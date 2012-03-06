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
package net.mindengine.oculus.experior.framework.verification.collections;

import java.util.List;

import net.mindengine.oculus.experior.reporter.Report;
import net.mindengine.oculus.experior.reporter.ReportDesign;
import net.mindengine.oculus.experior.reporter.ReportIcon;

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
            report.info(message).details(details).icon(ReportIcon.VALIDATION_PASSED);
        }
    }
    
    private void reportError(String message, String expectedCaption, Object[] expectedValues) {
        if(report!=null) {
            String details = ReportDesign.bold("Real values: ")+ReportDesign.breakline()+ReportDesign.listValues(getRealCollection())
                + ReportDesign.bold(expectedCaption + ": ")+ReportDesign.breakline()+ReportDesign.listValues(expectedValues);
            report.error(message).details(details).icon(ReportIcon.VALIDATION_FAILED);
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
