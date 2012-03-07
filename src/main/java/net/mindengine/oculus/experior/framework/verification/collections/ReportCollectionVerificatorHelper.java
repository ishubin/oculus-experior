package net.mindengine.oculus.experior.framework.verification.collections;

import java.util.List;

import net.mindengine.oculus.experior.reporter.MessageBuilder;
import net.mindengine.oculus.experior.reporter.Report;
import net.mindengine.oculus.experior.reporter.ReportDesign;
import net.mindengine.oculus.experior.reporter.ReportIcon;

public class ReportCollectionVerificatorHelper {

	private String verificatorName = "CollectionVerificator";
	private Report report;
	private String collectionName;
	private List<?> realCollection;
	
	public boolean report(boolean checkState, String methodName, String passDefaultTemplate, String failDefaultTemplate, Object... args) {
		if(checkState) {
            reportInfo(msg(methodName + ".pass").put("name", getCollectionName()).toString(), args);
        }
        else reportError(msg(methodName + ".fail").put("name", getCollectionName()).toString(), args);
		return checkState;
	}
    
    private void reportInfo(String message, Object[] expectedValues) {
        if(getReport()!=null) {
            getReport().info(message).details(generateDetails("Expected items", expectedValues)).icon(ReportIcon.VALIDATION_PASSED);
        }
    }
    
    private void reportError(String message, Object[] expectedValues) {
        if(getReport()!=null) {
            getReport().error(message).details(generateDetails("Expected items", expectedValues)).icon(ReportIcon.VALIDATION_FAILED);
        }
    }
    
	private String generateDetails(String expectedCaption,
			Object[] expectedValues) {
		String details = ReportDesign.bold("Real values: ")+ReportDesign.breakline()+ReportDesign.listValues(getRealCollection())
		    + ReportDesign.bold(expectedCaption + ": ")+ReportDesign.breakline()+ReportDesign.listValues(expectedValues);
		return details;
	}

	public Report getReport() {
		return report;
	}

	public ReportCollectionVerificatorHelper setReport(Report report) {
		this.report = report;
		return this;
	}
	
	protected MessageBuilder msg(String templateSubName) {
    	return report.message(getVerificatorName() +"." + templateSubName);
    }

	public String getCollectionName() {
		return collectionName;
	}

	public ReportCollectionVerificatorHelper setCollectionName(String collectionName) {
		this.collectionName = collectionName;
		return this;
	}

	public List<?> getRealCollection() {
		return realCollection;
	}

	public ReportCollectionVerificatorHelper setRealCollection(List<?> realCollection) {
		this.realCollection = realCollection;
		return this;
	}

	public String getVerificatorName() {
		return verificatorName;
	}

	public ReportCollectionVerificatorHelper setVerificatorName(String verificatorName) {
		this.verificatorName = verificatorName;
		return this;
	}
}
