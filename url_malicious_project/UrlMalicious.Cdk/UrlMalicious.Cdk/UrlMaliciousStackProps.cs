using Amazon.CDK;
using Amazon.CDK.AWS.Logs;

namespace UrlMalicious.Cdk;

public class UrlMaliciousStackProps : StackProps
{
    public string VpcId { get; set; }
    public string WebAccessSg { get; set; }
    public string Stage { get; set; }
    public RetentionDays RetentionDays { get; set; }
    public Dictionary<string,string> EnvironmentVariables { get; set; }
}