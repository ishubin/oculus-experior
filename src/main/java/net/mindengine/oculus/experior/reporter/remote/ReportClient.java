package net.mindengine.oculus.experior.reporter.remote;

import net.mindengine.oculus.experior.reporter.remote.wrappers.SuiteRun;

public class ReportClient {
    private String oculusServerUrl;
    private String authToken;
    private RestClient restClient = new RestClient();
    
    public ReportClient(String oculusServerUrl, String authToken) {
        this.oculusServerUrl = oculusServerUrl;
        this.authToken = authToken;
    }

    public Long createSuite(SuiteRun suiteRun) {
        if ( oculusServerUrl == null ) {
            throw new IllegalArgumentException("Url to oculus server is not specified");
        }
        return restClient.post(oculusServerUrl + "/api/report/suite", authToken, suiteRun).asLong();
    }
    
    public void finishSuite(long id) {
        restClient.put(oculusServerUrl + "/api/report/suite/" + id + "/finished", authToken);
    }
    
    
    public String getOculusServerUrl() {
        return oculusServerUrl;
    }
    public void setOculusServerUrl(String oculusServerUrl) {
        this.oculusServerUrl = oculusServerUrl;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

}
