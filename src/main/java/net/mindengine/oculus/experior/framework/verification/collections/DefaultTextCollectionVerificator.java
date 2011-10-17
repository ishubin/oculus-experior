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
package net.mindengine.oculus.experior.framework.verification.collections;

import java.util.List;

import net.mindengine.oculus.experior.reporter.Report;
import net.mindengine.oculus.experior.reporter.ReportDesign;
import net.mindengine.oculus.experior.reporter.ReportLogo;

public class DefaultTextCollectionVerificator extends SimpleTextCollectionVerificator {

    private String name;
    private Report report;
    
    public DefaultTextCollectionVerificator() {
        super();
    }

    public DefaultTextCollectionVerificator(List<String> realCollection) {
        super(realCollection);
    }

    public DefaultTextCollectionVerificator(String... strings) {
        super(strings);
    }

    public void setReport(Report report) {
        this.report = report;
    }
    public Report getReport() {
        return report;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    
    public String fetchName() {
        if(name!=null) {
            return name;
        }
        return "Undefind";
    }
    
    private void reportInfo(String message, Object[] expectedValues) {
        reportInfo(message, "Expected items", expectedValues);
    }
    
    private void reportError(String message, Object[] expectedValues) {
        reportError(message, "Expected items", expectedValues);
    }
    
    private void reportInfo(String message, String expectedCaption, Object[] expectedValues) {
        if(report!=null) {
            String details = ReportDesign.bold("Real values: ")+ReportDesign.breakline()+ReportDesign.listValues(getRealCollection())
                + ReportDesign.bold(expectedCaption + ": ")+ReportDesign.breakline()+ReportDesign.listValues(expectedValues);
            report.info(message, details, ReportLogo.VALIDATION_PASSED);
        }
    }
    
    private void reportError(String message, String expectedCaption, Object[] expectedValues) {
        if(report!=null) {
            String details = ReportDesign.bold("Real values: ")+ReportDesign.breakline()+ReportDesign.listValues(getRealCollection())
                + ReportDesign.bold(expectedCaption + ": ")+ReportDesign.breakline()+ReportDesign.listValues(expectedValues);
            report.error(message, details, ReportLogo.VALIDATION_FAILED);
        }
    }
    
    @Override
    public boolean hasAll(Object... args) {
        boolean check = super.hasAll(args);
        if(check) {
            reportInfo("The "+getName()+" contains all specified expected items", args);
        }
        else reportError("The "+getName()+" doesn't contain all specified expected items", args);
        return check;
    }
    @Override
    public boolean hasAny(Object... args) {
        boolean check = super.hasAny(args);
        if(check) {
            reportInfo("The "+getName()+" contains at least on item from expected list", args);
        }
        else reportError("The "+getName()+" doesn't contain any item from expected list", args);
        return check;
    }
    @Override
    public boolean hasExactly(Object... args) {
        boolean check = super.hasExactly(args);
        if(check) {
            reportInfo("The "+getName()+" contains is the same as expected list", args);
        }
        else reportError("The "+getName()+" is not same as expected list", args);
        return check;
    }
    @Override
    public boolean hasNone(Object... args) {
        boolean check = super.hasNone(args);
        if(check) {
            reportInfo("The "+getName()+" doesn't contain any unexpected item", "Unexpected items", args);
        }
        else reportError("The "+getName()+" contains unexpected items", "Unexpected items", args);
        return check;
    }
    @Override
    public boolean hasOnly(Object... args) {
        boolean check = super.hasOnly(args);
        if(check) {
            reportInfo("The "+getName()+" contains only items from expected list", args);
        }
        else reportError("The "+getName()+" doesn't contain only items from expected list", args);
        return check;
    }

}
