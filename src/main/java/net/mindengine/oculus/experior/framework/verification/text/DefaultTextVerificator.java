package net.mindengine.oculus.experior.framework.verification.text;

import net.mindengine.oculus.experior.reporter.Report;
import net.mindengine.oculus.experior.reporter.ReportDesign;
import net.mindengine.oculus.experior.reporter.ReportLogo;

public class DefaultTextVerificator extends SimpleTextVerificator {

    private Report report;
    private String name;
    
    public DefaultTextVerificator() {
        super();
    }

    public DefaultTextVerificator(String string) {
        super(string);
    }
    
    public DefaultTextVerificator(String string, String name, Report report) {
        super(string);
        setName(name);
        setReport(report);
    }
    
    public void reportInfo(String message) {
        reportInfo(message, null);
    }
    
    public void reportError(String message) {
        reportError(message, null);
    }
    
    public void reportInfo(String message, String details) {
        if(report!=null) {
            report.info(message, details, ReportLogo.VALIDATION_PASSED);
        }
    }
    
    private String fetchName() {
        if(name!=null) {
            return name;
        }
        else return "Undefined";
    }

    public void reportError(String message, String details) {
        if(report!=null) {
            report.info(message, details, ReportLogo.VALIDATION_FAILED);
        }
    }
    
    private String details(String expectedCaption, String expectedString) {
        return ReportDesign.bold("Real text: ")+ReportDesign.string(getRealValue())+ReportDesign.breakline()+ReportDesign.bold(expectedCaption+": ")+ReportDesign.string(expectedString);
    }
    

    @Override
    public boolean contains(String string) {
        boolean check = super.contains(string);
        if(check){
            reportInfo("The text of "+fetchName()+" contains the expected "+ReportDesign.shortString(string),
                    details("Should contain", string));
        }
        else reportError("The text of "+fetchName()+" doesn't contain the expected "+ReportDesign.shortString(string),
                details("Should contain", string));
        return check;
    }

    @Override
    public boolean doesNotContain(String string) {
        boolean check = super.doesNotContain(string);
        if(check){
            reportInfo("The text of "+fetchName()+" doesn't contain the "+ReportDesign.shortString(string)+" as expected",
                    details("Should not contain", string));
        }
        else reportError("The text of "+fetchName()+" contains the unexpected "+ReportDesign.shortString(string),
                details("Should not contain", string));
        return check;
    }

    @Override
    public boolean doesNotMatch(String regEx) {
        boolean check = super.doesNotMatch(regEx);
        if(check){
            reportInfo("The text of "+fetchName()+" doesn't match pattern "+ReportDesign.shortString(regEx)+" as expected",
                    details("Pattern", regEx));
        }
        else reportError("The text of "+fetchName()+" matches the unexpected pattern "+ReportDesign.shortString(regEx),
                details("Pattern", regEx));
        return check;
    }

    @Override
    public boolean doesNotStartWith(String string) {
        boolean check = super.doesNotStartWith(string);
        if(check){
            reportInfo("The text of "+fetchName()+" doesn't start with text "+ReportDesign.shortString(string)+" as expected",
                    details("Prefix", string));
        }
        else reportError("The text of "+fetchName()+" starts with unexpected text "+ReportDesign.shortString(string),
                details("Prefix", string));
        return check;
    }

    @Override
    public boolean is(String string) {
        boolean check = super.is(string);
        if(check){
            reportInfo("The text of "+fetchName()+" is same as expected text "+ReportDesign.shortString(string),
                    details("Expected text", string));
        }
        else reportError("The text of "+fetchName()+" not the same as expected text "+ReportDesign.shortString(string),
                details("Expected text", string));
        return check;
    }

    @Override
    public boolean isNot(String string) {
        boolean check = super.isNot(string);
        if(check){
            reportInfo("The text of "+fetchName()+" is not same as unexpected text "+ReportDesign.shortString(string),
                    details("Unexpected text", string));
        }
        else reportError("The text of "+fetchName()+" is the same as unexpected text "+ReportDesign.shortString(string),
                details("Unexpected text", string));
        return check;
    }

    @Override
    public boolean matches(String regEx) {
        boolean check = super.matches(regEx);
        if(check){
            reportInfo("The text of "+fetchName()+" matches the expected pattern "+ReportDesign.shortString(regEx),
                    details("Pattern", regEx));
        }
        else reportError("The text of "+fetchName()+" doesn't match the expected pattern "+ReportDesign.shortString(regEx),
                details("Pattern", regEx));
        return check;
    }

    @Override
    public boolean startsWith(String string) {
        boolean check = super.startsWith(string);
        if(check){
            reportInfo("The text of "+fetchName()+" starts with text "+ReportDesign.shortString(string)+" as expected",
                    details("Prefix", string));
        }
        else reportError("The text of "+fetchName()+" doesn't start with expected text "+ReportDesign.shortString(string),
                details("Prefix", string));
        return check;
    }
    
    @Override
    public boolean endsWith(String string) {
        boolean check = super.endsWith(string);
        if(check){
            reportInfo("The text of "+fetchName()+" ends with text "+ReportDesign.shortString(string)+" as expected",
                    details("Suffix", string));
        }
        else reportError("The text of "+fetchName()+" doesn't end with expected text "+ReportDesign.shortString(string),
                details("Suffix", string));
        return check;
    }
    
    @Override
    public boolean doesNotEndWith(String string) {
        boolean check = super.doesNotEndWith(string);
        if(check){
            reportInfo("The text of "+fetchName()+" doesn't end with unexpected text "+ReportDesign.shortString(string),
                    details("Suffix", string));
        }
        else reportError("The text of "+fetchName()+" ends with unexpected text "+ReportDesign.shortString(string),
                details("Suffix", string));
        return check;
    }

    @Override
    public boolean isOneOf(String... strings) {
        boolean check = super.isOneOf(strings);
        if(check){
            reportInfo("The text of "+fetchName()+" is in specified list of expected texts",
                    details("Expected texts", ReportDesign.breakline()+ReportDesign.listValues((Object[])strings)));
        }
        else reportError("The text of "+fetchName()+" is not in specified list of expected texts",
                details("Expected texts", ReportDesign.breakline()+ReportDesign.listValues((Object[])strings)));
        return check;
    }
    
    @Override
    public boolean isNotOneOf(String... strings) {
        boolean check = super.isNotOneOf(strings);
        if(check){
            reportInfo("The text of "+fetchName()+" is ont in specified list of unexpected texts",
                    details("Unexpected texts", ReportDesign.breakline()+ReportDesign.listValues((Object[])strings)));
        }
        else reportError("The text of "+fetchName()+" is in specified list of unexpected texts",
                details("Unexpected texts", ReportDesign.breakline()+ReportDesign.listValues((Object[])strings)));
        return check;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public Report getReport() {
        return report;
    }
    
}
