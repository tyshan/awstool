package processor

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.AmazonClientException
import com.amazonaws.regions.Region
import com.amazonaws.services.ec2.AmazonEC2
import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.ec2.AmazonEC2Client
import com.amazonaws.regions.Regions

class BaseProcessor {
  var ec2: AmazonEC2 = null
  var region: Region = ConfigProcessor.region;
  def init() {
    var credentials: AWSCredentials = null
    try {
      credentials = new ProfileCredentialsProvider().getCredentials()
    } catch {
      case e: Exception =>
        throw new AmazonClientException(
          "Cannot load the credentials from the credential profiles file. " +
            "Please make sure that your credentials file is at the correct " +
            "location (~/.aws/credentials), and is in valid format.",
          e)
    }
    ec2 = new AmazonEC2Client(credentials);
    ec2.setRegion(region);

  }

  init();
}