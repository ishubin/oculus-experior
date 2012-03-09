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
package net.mindengine.oculus.experior.reporter.nodes;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import net.mindengine.oculus.experior.reporter.ReportReason;

import org.apache.commons.lang3.StringUtils;

public class ExceptionReportNode extends ReportNode{

    private ExceptionInfo exception;

    public ExceptionInfo getException() {
        return exception;
    }
    
    public void setException(ExceptionInfo exception) {
        this.exception = exception;
    }

    public ExceptionReportNode exception(ExceptionInfo exception) {
        this.exception = exception;
        return this;
    }
    
    public ExceptionReportNode exception(Throwable exception) {
        this.exception = ExceptionInfo.convert(exception);
        return this;
    }
    
    public ExceptionReportNode title(String title) {
        setTitle(title);
        return this;
    }
    public ExceptionReportNode id(String id) {
        setId(id);
        return this;
    }
    public ExceptionReportNode icon(String icon) {
        setIcon(icon);
        return this;
    }
    public ExceptionReportNode debug(boolean debug) {
        setDebug(debug);
        return this;
    }
    public ExceptionReportNode date(Date date) {
        setDate(date);
        return this;
    }
    public ExceptionReportNode level(String level) {
        setLevel(level);
        return this;
    }
    
    public ExceptionReportNode hint(String hint) {
        setHint(hint);
        return this;
    }
    
    @Override
    public List<ReportReason> collectReasons(String... levels) {
        for ( String level : levels ) {
            if ( StringUtils.equals(this.getLevel(), level) ) {
                ReportReason reason = new ReportReason();
                reason.setLevel(level);
                
                if ( this.getException() != null ) {
                    String text = this.getException().getClassName();
                    if( this.getException().getMessageName() != null ) {
                        text = text + ":" + this.getException().getMessageName();
                    }
                    reason.setReason(text);
                }
                else {
                    reason.setReason(this.getTitle());
                }
                
                List<ReportReason> reasons = new LinkedList<ReportReason>();
                reasons.add(reason);
                return reasons;
            }
        }
        return null;
    }
    
}
