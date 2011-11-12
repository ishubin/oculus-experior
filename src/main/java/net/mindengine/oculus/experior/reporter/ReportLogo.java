/*******************************************************************************
 * Copyright 2011 Ivan Shubin
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
package net.mindengine.oculus.experior.reporter;

/**
 * Immutable class which is used for fetching the reports logo in
 * {@link DefaultReport}
 * 
 * @author Ivan Shubin
 * 
 */
public class ReportLogo {
    public final static ReportLogo UI = new ReportLogo("ui");
    public final static ReportLogo UI_TYPE = new ReportLogo("ui-type");
    public final static ReportLogo UI_CLICK = new ReportLogo("ui-click");
    public final static ReportLogo WAIT = new ReportLogo("wait");
    public final static ReportLogo SQL = new ReportLogo("sql");
    public final static ReportLogo MAIL = new ReportLogo("mail");
    public final static ReportLogo HTTP = new ReportLogo("http");
    public final static ReportLogo INFO = new ReportLogo("info");
    public final static ReportLogo VALIDATION_PASSED = new ReportLogo("validation-passed");
    public final static ReportLogo VALIDATION_FAILED = new ReportLogo("validation-failed");
    public final static ReportLogo OPEN = new ReportLogo("open");
    public final static ReportLogo EXCEPTION = new ReportLogo("exception");
    public final static ReportLogo ACTION = new ReportLogo("action");
    public final static ReportLogo COMPONENT = new ReportLogo("component");
    public final static ReportLogo ROLLBACK = new ReportLogo("rollback");

    private String logo;

    public ReportLogo(String logo) {
        this.logo = logo;
    }

    public String getLogo() {
        return logo;
    }
}
