/*******************************************************************************
* Copyright 2012 Ivan Shubin http://mindengine.net
* 
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* 
*   http://www.apache.org/licenses/LICENSE-2.0
* 
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
******************************************************************************/
package net.mindengine.oculus.experior.reporter.remote;

import net.mindengine.oculus.experior.reporter.remote.wrappers.SuiteRun;
import net.mindengine.oculus.experior.reporter.remote.wrappers.TestRun;

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

    public Long createTestRun(Long suiteId, TestRun testRun) {
        return restClient.post(oculusServerUrl + "/api/report/suite/" + suiteId + "/test", authToken, testRun).asLong();
    }

}
