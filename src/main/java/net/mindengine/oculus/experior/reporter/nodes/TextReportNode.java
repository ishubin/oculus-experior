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

import org.apache.commons.lang3.StringUtils;

import net.mindengine.oculus.experior.reporter.ReportReason;

public class TextReportNode extends ReportNode {
    private String details;

    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }

    public TextReportNode details(String details) {
        this.details = details;
        return this;
    }

    public TextReportNode title(String title) {
        setTitle(title);
        return this;
    }
    public TextReportNode id(String id) {
        setId(id);
        return this;
    }
    public TextReportNode icon(String icon) {
        setIcon(icon);
        return this;
    }
    public TextReportNode debug(boolean debug) {
        setDebug(debug);
        return this;
    }
    public TextReportNode date(Date date) {
        setDate(date);
        return this;
    }
    public TextReportNode level(String level) {
        setLevel(level);
        return this;
    }
    
    public TextReportNode hint(String hint) {
        setHint(hint);
        return this;
    }
    
    @Override
    public List<ReportReason> collectReasons(String... levels) {
        for ( String level : levels ) {
            if ( StringUtils.equals(this.getLevel(), level) ) {
                ReportReason reason = new ReportReason();
                reason.setLevel(level);
                reason.setReason(this.getTitle());
                
                List<ReportReason> reasons = new LinkedList<ReportReason>();
                reasons.add(reason);
                return reasons;
            }
        }
        return null;
    }
}
