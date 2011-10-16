package net.mindengine.oculus.experior.framework.verification.collections;

import java.util.List;

import net.mindengine.oculus.experior.reporter.Report;
import net.mindengine.oculus.experior.reporter.ReportDesign;
import net.mindengine.oculus.experior.reporter.ReportLogo;

public class DefaultNumberCollectionVerificator extends SimpleNumberCollectionVerificator {

    private String name;
    private Report report;
    
    
    
    public DefaultNumberCollectionVerificator() {
        super();
    }
    public DefaultNumberCollectionVerificator(List<?> realCollection) {
        super(realCollection);
    }
    public DefaultNumberCollectionVerificator(Number... realCollection) {
        super(realCollection);
    }
    
    public DefaultNumberCollectionVerificator(String name, Report report, List<?> realCollection) {
        super(realCollection);
        setName(name);
        setReport(report);
    }
    public DefaultNumberCollectionVerificator(String name, Report report, Number... realCollection) {
        super(realCollection);
        setName(name);
        setReport(report);
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