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

import net.mindengine.oculus.experior.reporter.MessageBuilder;
import net.mindengine.oculus.experior.reporter.Report;
import net.mindengine.oculus.experior.reporter.ReportDesign;
import net.mindengine.oculus.experior.reporter.ReportIcon;

public class ReportCollectionVerificatorHelper<T> {

	private String verificatorName = "CollectionVerificator";
	private Report report;
	private String collectionName;
	private List<T> realCollection;
	
	public boolean report(boolean checkState, String methodName, String passDefaultTemplate, String failDefaultTemplate, List<T> expectedList) {
		if(report != null) {
			if(checkState) {
	            reportInfo(msg(methodName + ".pass").put("name", getCollectionName()).toString(), expectedList);
	        }
	        else reportError(msg(methodName + ".fail").put("name", getCollectionName()).toString(), expectedList);
		}
		return checkState;
	}
    
    private void reportInfo(String message, List<T> expectedList) {
        if(getReport()!=null) {
            getReport().info(message).details(generateDetails("Expected items", expectedList)).icon(ReportIcon.VALIDATION_PASSED);
        }
    }
    
    private void reportError(String message, List<T> expectedList) {
        if(getReport()!=null) {
            getReport().error(message).details(generateDetails("Expected items", expectedList)).icon(ReportIcon.VALIDATION_FAILED);
        }
    }
    
	private String generateDetails(String expectedCaption, List<T> expectedList) {
		String details = ReportDesign.bold("Real values: ") + ReportDesign.breakline()+ReportDesign.listValues(getRealCollection())
		    + ReportDesign.bold(expectedCaption + ": ") + ReportDesign.breakline() + ReportDesign.listValues(expectedList);
		return details;
	}

	public Report getReport() {
		return report;
	}

	public ReportCollectionVerificatorHelper<T> setReport(Report report) {
		this.report = report;
		return this;
	}
	
	protected MessageBuilder msg(String templateSubName) {
    	return report.message(getVerificatorName() +"." + templateSubName);
    }

	public String getCollectionName() {
		return collectionName;
	}

	public ReportCollectionVerificatorHelper<T> setCollectionName(String collectionName) {
		this.collectionName = collectionName;
		return this;
	}

	public List<?> getRealCollection() {
		return realCollection;
	}

	public ReportCollectionVerificatorHelper<T> setRealCollection(List<T> realCollection) {
		this.realCollection = realCollection;
		return this;
	}

	public String getVerificatorName() {
		return verificatorName;
	}

	public ReportCollectionVerificatorHelper<T> setVerificatorName(String verificatorName) {
		this.verificatorName = verificatorName;
		return this;
	}
}
