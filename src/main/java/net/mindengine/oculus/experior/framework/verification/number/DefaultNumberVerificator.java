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
package net.mindengine.oculus.experior.framework.verification.number;

import net.mindengine.oculus.experior.reporter.Report;
import net.mindengine.oculus.experior.reporter.ReportDesign;
import net.mindengine.oculus.experior.reporter.ReportLogo;

public class DefaultNumberVerificator extends SimpleNumberVerificator{

    private String name;
    private Report report;
    
    public DefaultNumberVerificator() {
        super();
    }

    public DefaultNumberVerificator(Number realValue) {
        super(realValue);
    }
    
    public DefaultNumberVerificator(Number realValue, String name, Report report) {
        super(realValue);
        setName(name);
        setReport(report);
    }
    
    public String fetchName() {
        if(name!=null) {
            return name;
        }
        else return ReportDesign.bold("Unknown");
    }
    
    public void reportInfo(String message) {
        reportInfo(message, null);
    }
    
    public void reportError(String message) {
        reportError(message, null);
    }
    
    public void reportInfo(String message, String details) {
        if(report!=null) {
            report.info("The value of "+fetchName() + " is "+ReportDesign.variableValue(getRealValue()) +" "+  message, details, ReportLogo.VALIDATION_PASSED);
        }
    }
    
    public void reportError(String message, String details) {
        if(report!=null) {
            report.info("The value of "+fetchName() + " is "+ReportDesign.variableValue(getRealValue()) +" "+  message, details, ReportLogo.VALIDATION_FAILED);
        }
    }
    
    @Override
    public boolean is(Number expected) {
        boolean check = super.is(expected);
        if(check) {
            reportInfo("as expected");
        }
        else reportError("but it is not as expected "+ReportDesign.variableValue(expected));
        return check;
    }

    @Override
    public boolean isGreaterThan(Number expected) {
        boolean check = super.isGreaterThan(expected);
        if(check) {
            reportInfo("and it is greater than expected "+ReportDesign.variableValue(expected));
        }
        else reportError("but it is not greater than expected "+ReportDesign.variableValue(expected));
        return check;
    }

    @Override
    public boolean isGreaterThanOrEquals(Number expected) {
        boolean check = super.isGreaterThanOrEquals(expected);
        if(check) {
            reportInfo("and it is greater than expected or equals "+ReportDesign.variableValue(expected));
        }
        else reportError("but it is less than expected "+ReportDesign.variableValue(expected));
        return check;
    }

    @Override
    public boolean isInRange(Number start, Number end) {
        boolean check = super.isInRange(start, end);
        if(check) {
            reportInfo("and it is in expected range ["+ReportDesign.variableValue(start)+", "+ReportDesign.variableValue(end)+"]");
        }
        else reportError("but it is not in expected range ["+ReportDesign.variableValue(start)+", "+ReportDesign.variableValue(end)+"]");
        return check;
    }

    @Override
    public boolean isLessThan(Number expected) {
        boolean check = super.isLessThan(expected);
        if(check) {
            reportInfo("and it is less than expected "+ReportDesign.variableValue(expected));
        }
        else reportError("but it is not less than expected "+ReportDesign.variableValue(expected));
        return check;
    }

    @Override
    public boolean isLessThanOrEquals(Number expected) {
        boolean check = super.isLessThanOrEquals(expected);
        if(check) {
            reportInfo("and it is less than expected or equals "+ReportDesign.variableValue(expected));
        }
        else reportError("but it is greater than expected "+ReportDesign.variableValue(expected));
        return check;
    }

    @Override
    public boolean isNot(Number expected) {
        boolean check = super.isNot(expected);
        if(check) {
            reportInfo("as expected");
        }
        else reportError("but it is not as expected");
        return check;
    }

    @Override
    public boolean isNotInRange(Number start, Number end) {
        boolean check = super.isNotInRange(start, end);
        if(check) {
            reportInfo("and it is not inrange ["+ReportDesign.variableValue(start)+", "+ReportDesign.variableValue(end)+"] as expected");
        }
        else reportError("but it is in range ["+ReportDesign.variableValue(start)+", "+ReportDesign.variableValue(end)+"] which is not as expected");
        return check;
    }

    @Override
    public boolean isNotNull() {
        boolean check = super.isNotNull();
        if(check) {
            reportInfo("and it is not "+ReportDesign.nullValue()+" as expected");
        }
        else reportError("but it is not as expected");
        return check;
    }

    @Override
    public boolean isNotOneOf(Number... args) {
        boolean check = super.isNotOneOf(args);
        if(check) {
            reportInfo("and it is not in specified list as expected", ReportDesign.listValues((Object[])args));
        }
        else reportError("and it is in specified list which is not expected", ReportDesign.listValues((Object[])args));
        return check;
    }

    @Override
    public boolean isNull() {
        boolean check = super.isNull();
        if(check) {
            reportInfo("as expected");
        }
        else reportError("but "+ReportDesign.nullValue()+" is expected");
        return check;
    }

    @Override
    public boolean isOneOf(Number... args) {
        boolean check = super.isOneOf(args);
        if(check) {
            reportInfo("and it is in expected list", ReportDesign.listValues((Object[])args));
        }
        else reportError("and it is not in expected list", ReportDesign.listValues((Object[])args));
        return check;
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
}
