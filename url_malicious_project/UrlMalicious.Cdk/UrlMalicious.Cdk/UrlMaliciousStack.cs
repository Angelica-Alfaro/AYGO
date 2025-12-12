using Amazon.CDK;
using Amazon.CDK.AWS.APIGateway;
using Amazon.CDK.AWS.EC2;
using Amazon.CDK.AWS.ECR;
using Amazon.CDK.AWS.IAM;
using Amazon.CDK.AWS.Lambda;
using Amazon.CDK.AWS.Logs;
using Amazon.CDK.AWS.S3;
using Constructs;
using TagsManager = Amazon.CDK.Tags;

namespace UrlMalicious.Cdk;

public class UrlMaliciousStack : Stack
{
    private const int TimeOutMinutes = 2;
    private const int MemorySizeMb = 512;
    
    internal UrlMaliciousStack(Construct scope, string id, UrlMaliciousStackProps props = null
    )
        : base(scope, id, props)
    {
        RegisterTags();
        CreateInfrastructure(props);
    }
    
    private void RegisterTags()
    {
        TagsManager.Of(this).Add("CostCenter", "SMS");
        TagsManager.Of(this).Add("AzureRepository", "MasivSms/sms-url-malicious");
        TagsManager.Of(this).Add("Component", "SmsUrlMaliciousApi");
    }
    
    private void CreateInfrastructure(UrlMaliciousStackProps props)
    {
        var s3 = new Bucket(this,"models-url-malicious");
        var repo = new Repository(this,"repository");
        var function = CreateLambda(s3,repo, props);
        CreateApiGateway(function,props);
    }

    private Function CreateLambda(Bucket bucket, Repository repo, UrlMaliciousStackProps props)
    {
        var vpc = Vpc.FromLookup(this, "VpcLambda", new VpcLookupOptions { VpcId = props.VpcId});
        var privateAccessSgId = SecurityGroup.FromSecurityGroupId(this, "PrivateAccessSg", props.WebAccessSg,
            new SecurityGroupImportOptions
            {
                Mutable = false
            });
        const string functionName = "UrlMalicious";
        var logGroup = new LogGroup(this, "LogGroup", new LogGroupProps
        {
            Retention = props.RetentionDays,
            LogGroupName = $"/aws/lambda/{functionName}"
        });
        props.EnvironmentVariables.Add("BUCKET_NAME",bucket.BucketName);
        var urlMaliciousApiFunction = new Function(this, "Function", new FunctionProps
        {
            FunctionName = functionName,
            Timeout = Duration.Minutes(TimeOutMinutes),
            Code = Code.FromEcrImage(repo),
            Handler = Handler.FROM_IMAGE,
            Runtime = Runtime.FROM_IMAGE,
            MemorySize = MemorySizeMb,
            Vpc = vpc,
            Environment = props.EnvironmentVariables,
            SecurityGroups = new ISecurityGroup[]{privateAccessSgId},
        });
        urlMaliciousApiFunction = SetLambdaConfigurations(urlMaliciousApiFunction,bucket,props);

        return urlMaliciousApiFunction;
        
    }
    
    private Function SetLambdaConfigurations(Function function,Bucket bucket,UrlMaliciousStackProps props)
    {
        function.Role!.AttachInlinePolicy(new Policy(this,"Policy",new PolicyProps
        {
            Statements = new []{new PolicyStatement(new PolicyStatementProps
            {
                Actions = new []{"ssm:GetParametersByPath","s3:GetObject","s3:ListBucket"},
                Effect = Effect.ALLOW,
                Resources = new []{$"arn:aws:ssm:{Region}:{Account}:parameter/*",$"arn:aws:s3:::{bucket.BucketName}/*"}
            })}
        }));

        return function;
    }
    
    private void CreateApiGateway(IFunction function,UrlMaliciousStackProps props)
        {
            
            var vpc = Vpc.FromLookup(this, "VpcApiGateway", new VpcLookupOptions { VpcId = props.VpcId});
            var apiGatewayDbPolicyStatement = new PolicyStatement {Effect = Effect.ALLOW};
            apiGatewayDbPolicyStatement.AddActions("execute-api:Invoke");
            apiGatewayDbPolicyStatement.AddResources("*");
            apiGatewayDbPolicyStatement.AddPrincipals(new AnyPrincipal());
            var api = new LambdaRestApi(this, "UrlMaliciousApi",new LambdaRestApiProps
            {
                
                Handler = function,
                Proxy = false,
                ApiKeySourceType = ApiKeySourceType.HEADER,
                DeployOptions = new StageOptions
                {
                   StageName = props.Stage
                },
                Deploy = true,
                Policy = new PolicyDocument(new PolicyDocumentProps{Statements = new[]{apiGatewayDbPolicyStatement}}),
                EndpointConfiguration = new EndpointConfiguration
                {
                    Types = new [] {EndpointType.PRIVATE}
                },
            });
            
            //Endpoints
            var modelResource= api.Root.AddResource("model");
            var urlMaliciousResource = modelResource.AddResource("url-malicious");

            api.AddUsagePlan("urlMalicious-UsagePlan",new UsagePlanProps
            {
                Name = "urlMalicious",
                ApiStages = new IUsagePlanPerApiStage[]
                {
                    new UsagePlanPerApiStage
                    {
                        Api = api,
                        Stage = api.DeploymentStage
                    }
                }
            }).AddApiKey(new ApiKey(this,"url-malicious-key",new ApiKeyProps
            {
                ApiKeyName = "url-malicious-key"
            }));

            var model = new Dictionary<string, IModel>();
            model.Add("application/json",Model.EMPTY_MODEL);
            urlMaliciousResource.AddMethod("POST",new LambdaIntegration(function),new MethodOptions
            {
                ApiKeyRequired = true,
                MethodResponses = new IMethodResponse[]
                {
                    new MethodResponse
                    {
                        StatusCode = "200",
                        ResponseModels = model
                    }
                },
                
            });
            
        }
}