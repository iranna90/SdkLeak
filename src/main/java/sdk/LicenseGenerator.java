package sdk;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

import com.google.video.widevine.sdk.wvpl.WvPLEnvironment;
import com.google.video.widevine.sdk.wvpl.WvPLKey;
import com.google.video.widevine.sdk.wvpl.WvPLOutputProtection;
import com.google.video.widevine.sdk.wvpl.WvPLPlaybackPolicy;
import com.google.video.widevine.sdk.wvpl.WvPLSession;
import com.google.video.widevine.sdk.wvpl.WvPLSessionInit;
import com.google.video.widevine.sdk.wvpl.WvPLTrackType;

public class LicenseGenerator {
  private static final byte[] defaultKey;

  static {
    final UUID uuid = UUID.fromString("00000000-0000-0000-0000-000000000000");
    byte[] data = new byte[16];
    var bb = ByteBuffer.wrap(data);
    bb.putLong(uuid.getMostSignificantBits());
    bb.putLong(uuid.getLeastSignificantBits());
    defaultKey = bb.array();
  }

  public static String generateLicense(final byte[] request, final WvPLEnvironment wvPLEnvironment) {
    WvPLSession session = null;
    try {
      session = wvPLEnvironment.createSession(request);

      // example session
      var sessionInit = new WvPLSessionInit();
      sessionInit.setMasterSigningKey(defaultKey);
      sessionInit.setProviderClientToken(Base64.getEncoder().encodeToString(defaultKey));
      sessionInit.setOverrideProviderClientToken(true);
      session.setSessionInit(sessionInit);

      // example key
      final WvPLKey wvPLKey = new WvPLKey();
      wvPLKey.setKeyBytes(defaultKey);
      wvPLKey.setKeyId(defaultKey);
      wvPLKey.setTrackType(WvPLTrackType.TrackType.VIDEO_HD);
      wvPLKey.setOutputProtection(new WvPLOutputProtection());
      session.addKey(wvPLKey);


      // example policy
      final WvPLPlaybackPolicy policy = new WvPLPlaybackPolicy();
      policy.setCanPlay(true);
      policy.setCanPersist(true);
      policy.setLicenseDurationSeconds(200);
      policy.setPlaybackDurationSeconds(200);
      policy.setRentalDurationSeconds(200);
      policy.setAlwaysIncludeClientId(true);
      session.setPolicy(policy);

      final byte[] bytes = session.generateLicense();

      // closing session details.
      sessionInit.finalize();
      session.getSessionInit().finalize();
      wvPLEnvironment.destroySession(session);

      return Base64.getEncoder().encodeToString(bytes);
    } catch (Exception exception) {
      if (session != null) {
        try {
          wvPLEnvironment.destroySession(session);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
      throw new RuntimeException(exception);
    }
  }
}
