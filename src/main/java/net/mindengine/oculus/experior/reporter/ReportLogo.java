/*******************************************************************************
 * 2011 Ivan Shubin http://mindengine.net
 * 
 * This file is part of Oculus Experior.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Oculus Experior.  If not, see <http://www.gnu.org/licenses/>.
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
    public final static ReportLogo XPATH = new ReportLogo("xpath");
    public final static ReportLogo XPATH_TYPE = new ReportLogo("xpath-type");
    public final static ReportLogo XPATH_CLICK = new ReportLogo("xpath-click");
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
