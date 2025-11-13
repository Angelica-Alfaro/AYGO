package com.myorg;

import software.constructs.Construct;
import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.iam.IRole;
import software.amazon.awscdk.services.iam.Role;
// Import Lambda function
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.FunctionUrl;
import software.amazon.awscdk.services.lambda.FunctionUrlAuthType;
import software.amazon.awscdk.services.lambda.FunctionUrlOptions;
import software.amazon.awscdk.services.lambda.Runtime;

public class HelloCdkStack extends Stack {
    public HelloCdkStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public HelloCdkStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);
        
        IRole myRole = Role.fromRoleArn(this, "MyRole", "arn:aws:iam::595366412114:role/LabRole");
        // Define the Lambda function resource
        Function myFunction = Function.Builder.create(this, "HelloWorldFunction")
          .runtime(Runtime.NODEJS_20_X) // Provide any supported Node.js runtime
          .handler("index.handler")
          .role(myRole)
          .code(Code.fromInline(
                      "exports.handler = async function(event) {" +
                      " return {" +
                      " statusCode: 200," +
                      " body: JSON.stringify('Hello World!')" +
                      " };" +
                      "};"))
                      .build();
    
 // Define the Lambda function URL resource
    FunctionUrl myFunctionUrl = myFunction.addFunctionUrl(FunctionUrlOptions.builder()
      .authType(FunctionUrlAuthType.NONE)
      .build());

    // Define a CloudFormation output for your URL
    CfnOutput.Builder.create(this, "myFunctionUrlOutput")
      .value(myFunctionUrl.getUrl())
      .build();
    }
}


