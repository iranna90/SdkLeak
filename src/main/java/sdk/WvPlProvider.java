package sdk;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.video.widevine.sdk.wvpl.WvPLEnvironment;
import com.google.video.widevine.sdk.wvpl.WvPLStatusException;

public class WvPlProvider {
  public static WvPLEnvironment createProvider(final String certificatePath) throws IOException, WvPLStatusException {

    var deviceList = WvPlProvider.class.getClassLoader().getResourceAsStream("dcsl.txt").readAllBytes();

    String lines = Files.lines(Path.of(certificatePath)).collect(
        Collectors.joining()
    );

    var data = Base64.getMimeDecoder().decode(lines);
    var serverCertificate = new ObjectMapper().readValue(data, WvConfig.class);

    var wvPLEnvironment = new WvPLEnvironment(serverCertificate.getConfigValues());
    wvPLEnvironment.setDeviceCertificateStatusList(deviceList);

    wvPLEnvironment.setServiceCertificate(
        serverCertificate.getWvServiceCert(),
        serverCertificate.getWvServicePrivateKey(),
        serverCertificate.getWvServicePassphrase()
    );
    return wvPLEnvironment;
  }

}
