package sdk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Base64;

import com.google.video.widevine.sdk.wvpl.WvPLEnvironment;
import com.google.video.widevine.sdk.wvpl.WvPLStatusException;

public class Main {
  public static void main(String[] args) throws WvPLStatusException, IOException {
    final String certificatePath = System.getProperty("certPath");
    if (certificatePath == null) {
      throw new RuntimeException("Please provide certificate path using: [-DcertPath={absolute_Path}]");
    }

    final WvPLEnvironment wvPLEnvironment = WvPlProvider.createProvider(certificatePath);
    final byte[] request = readData("request.base64");

    int count = 0;
    while (true) {
      try {
        final String license = LicenseGenerator.generateLicense(request, wvPLEnvironment);
        count++;
        if (count % 1000 == 0) {
          System.out.println("Number of requests are [" + count + "]");
        }
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    }
  }

  private static byte[] readData(String fileName) throws IOException {
    final InputStream resourceAsStream = Main.class.getClassLoader().getResourceAsStream(fileName);
    final BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream));
    return Base64.getDecoder().decode(reader.readLine());
  }
}
