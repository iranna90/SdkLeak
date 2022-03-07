package sdk;

import java.util.Map;

public class WvConfig {
  private  byte[] wvServiceCert;
  private  byte[] wvServicePrivateKey;
  private  byte[] wvServicePassphrase;
  private  Map<String, String> configValues;

  public byte[] getWvServiceCert() {
    return wvServiceCert;
  }

  public void setWvServiceCert(final byte[] wvServiceCert) {
    this.wvServiceCert = wvServiceCert;
  }

  public byte[] getWvServicePrivateKey() {
    return wvServicePrivateKey;
  }

  public void setWvServicePrivateKey(final byte[] wvServicePrivateKey) {
    this.wvServicePrivateKey = wvServicePrivateKey;
  }

  public byte[] getWvServicePassphrase() {
    return wvServicePassphrase;
  }

  public void setWvServicePassphrase(final byte[] wvServicePassphrase) {
    this.wvServicePassphrase = wvServicePassphrase;
  }

  public Map<String, String> getConfigValues() {
    return configValues;
  }

  public void setConfigValues(final Map<String, String> configValues) {
    this.configValues = configValues;
  }
}
