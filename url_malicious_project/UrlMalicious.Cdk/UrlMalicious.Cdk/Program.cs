using Amazon.CDK;
using Amazon.CDK.AWS.Logs;
using Newtonsoft.Json;
using Environment = Amazon.CDK.Environment;

namespace UrlMalicious.Cdk;


public static class Program
{
    public static void Main(string[] args)
    {
        var app = new App();

        CreateLabsStack(app);
        CreateProdStack(app);

        app.Synth();
    }

    private static void CreateProdStack(App app)
    {
        var prodConfig = JsonConvert.DeserializeObject<UrlMaliciousStackProps>(JsonConvert.SerializeObject(app.Node.TryGetContext("ProdConfig")));

        prodConfig.RetentionDays = RetentionDays.THREE_DAYS;
        prodConfig.Stage = "Prod";
        prodConfig.Env = new Environment { Account = "908902414259", Region = "us-east-1" };

        _ = new UrlMaliciousStack(app, "UrlMaliciousProd", prodConfig);
    }

    private static void CreateLabsStack(App app)
    {
        var labsConfig = JsonConvert.DeserializeObject<UrlMaliciousStackProps>(JsonConvert.SerializeObject(app.Node.TryGetContext("LabsConfig")));

        labsConfig.RetentionDays = RetentionDays.THREE_DAYS;
        labsConfig.Env = new Environment { Account = "919471278046", Region = "us-east-1" };
        labsConfig.Stage = "Labs";
        _ = new UrlMaliciousStack(app, "UrlMaliciousLabs", labsConfig);
    }
}
