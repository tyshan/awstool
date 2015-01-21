package processor

import java.io.File
import com.amazonaws.AmazonClientException
import com.amazonaws.auth.profile.internal.ProfilesConfigFileLoader
import java.util.Map.Entry
import com.amazonaws.auth.profile.internal.ProfilesConfigFileLoader.ProfilesConfigFileLoaderHelper
import java.util.Scanner
import java.io.FileInputStream
import java.io.IOException
import com.amazonaws.regions.Regions
import com.amazonaws.regions.Region

object ConfigProcessor {
  def region: Region = {
    val userHome: String = System.getProperty("user.home");
    if (userHome == null) {
      throw new AmazonClientException("Unable to load AWS profiles: "
        + "'user.home' System property is not set.");
    }

    var awsDirectory: File = new File(userHome, ".aws");
    var legacyConfigProfiles: File = new File(awsDirectory, "config");
    var foundlegacyConfigProfiles: Boolean = legacyConfigProfiles.exists() && legacyConfigProfiles.isFile();
    if (!foundlegacyConfigProfiles) {
      throw new RuntimeException("no config file found in path $user.home/.aws");
    }
    var fis: FileInputStream = null;
    try {
      fis = new FileInputStream(legacyConfigProfiles)
      var scanner = new Scanner(fis)
      var currentProfileName: String = null;

      try {
        while (scanner.hasNextLine()) {
          var line: String = scanner.nextLine().trim()

          // Empty or comment lines
          if (!line.isEmpty() && !line.startsWith("#") && !line.startsWith("[")) {
            line = line.trim()
            var pair: Array[String] = line.split("=", 2)
            if (pair.length == 2) {
              var key: String = pair(0).trim()
              if (key == "region") {
                return Region.getRegion(Regions.fromName(pair(1).trim()))
              }
            }
          }
        }

      } finally {
        scanner.close();
      }

    } catch {
      case e: IOException =>
        throw new AmazonClientException("Unable to load AWS config file at: " + legacyConfigProfiles.getAbsolutePath, e)
    } finally {
      if (fis != null) try { fis.close() } finally { fis == null }
    }
    throw new RuntimeException("no region is found in $user.home/.aws/config");

  }
}