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
package net.mindengine.oculus.experior.defaultframework.verification;

import net.mindengine.oculus.experior.reporter.Report;
import net.mindengine.oculus.experior.reporter.ReportDesign;
import net.mindengine.oculus.experior.reporter.ReportLogo;

public class OculusVerificator {
    private Report report;

    /**
     * Name of the entity which is being verified
     */
    private String entityName;

    public OculusVerificator(Report report) {
        super();
        this.report = report;
    }

    public OculusVerificator(Report report, String entityName) {
        super();
        this.report = report;
        this.entityName = entityName;
    }

    @Deprecated
    public boolean validateEquals(String parameterName, Object expectedValue, Object realValue) {
        boolean bPassed = false;

        if (expectedValue == null) {
            if (realValue == null)
                bPassed = true;
        } else {
            if (expectedValue.equals(realValue))
                bPassed = true;
        }

        String text = ReportDesign.bold(parameterName) + " validation: " + ReportDesign.breakline() + "Expected = " + ReportDesign.string(expectedValue.toString()) + ReportDesign.breakline()
                + "Real = " + ReportDesign.string(realValue.toString());

        if (bPassed) {
            report.info(text, null, ReportLogo.VALIDATION_PASSED);
        } else
            report.error(text, null, ReportLogo.VALIDATION_FAILED);

        return bPassed;
    }

    @Deprecated
    public boolean validateContains(String parameterName, String seekText, String realText) {
        boolean bPassed = false;
        if (realText != null) {
            bPassed = realText.contains(seekText);
            if (bPassed) {
                report.info(ReportDesign.bold(parameterName) + " contains text " + ReportDesign.shortString(seekText), seekText, ReportLogo.VALIDATION_PASSED);
            } else
                report.error(ReportDesign.bold(parameterName) + " doesn't contain text " + ReportDesign.shortString(seekText), seekText, ReportLogo.VALIDATION_FAILED);
        } else
            report.error(ReportDesign.bold(parameterName) + " is null");
        return bPassed;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public Report getReport() {
        return report;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityName() {
        return entityName;
    }
}
