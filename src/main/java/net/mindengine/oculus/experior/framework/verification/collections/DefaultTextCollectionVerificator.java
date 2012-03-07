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

public class DefaultTextCollectionVerificator extends SimpleTextCollectionVerificator {

    private String name;
    private Report report;
	private ReportCollectionVerificatorHelper reportHelper;
    
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
        return "Undefined list";
    }
    
    @Override
    public boolean hasAll(Object... args) {
        return getReportHelper().report(super.hasAll(args), "hasAll", HASALL_PASS_DEFAULT_TEMPLATE, HASALL_FAIL_DEFAULT_TEMPLATE, args);
    }
	
    @Override
    public boolean hasAny(Object... args) {
    	return getReportHelper().report(super.hasAny(args), "hasAny", HASANY_PASS_DEFAULT_TEMPLATE, HASANY_FAIL_DEFAULT_TEMPLATE, args);
    }
    @Override
    public boolean hasExactly(Object... args) {
    	return getReportHelper().report(super.hasExactly(args), "hasExactly", HASEXACTLY_PASS_DEFAULT_TEMPLATE, HASEXACTLY_FAIL_DEFAULT_TEMPLATE, args);
    }
    @Override
    public boolean hasNone(Object... args) {
        return getReportHelper().report(super.hasNone(args), "hasNone", HASNONE_PASS_DEFAULT_TEMPLATE, HASNONE_FAIL_DEFAULT_TEMPLATE, args);
    }
    
    @Override
    public boolean hasOnly(Object... args) {
        return getReportHelper().report(super.hasOnly(args), "hasOnly", HASONLY_PASS_DEFAULT_TEMPLATE, HASONLY_FAIL_DEFAULT_TEMPLATE, args);
    }
    

    private ReportCollectionVerificatorHelper getReportHelper() {
		if ( this.reportHelper == null ) {
			this.reportHelper = new ReportCollectionVerificatorHelper();
		}
		return this.reportHelper.setCollectionName(getName()).setReport(getReport()).setRealCollection(getRealCollection());
	}
}
