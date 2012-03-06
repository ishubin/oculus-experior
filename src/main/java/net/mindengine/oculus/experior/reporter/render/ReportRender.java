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
package net.mindengine.oculus.experior.reporter.render;

import java.util.List;

import net.mindengine.oculus.experior.reporter.ReportReason;
import net.mindengine.oculus.experior.reporter.nodes.ReportNode;

public interface ReportRender {
    public String render(ReportNode report);

    public ReportNode decode(String report) throws Exception;

    /**
     * Saves collected failure reasons
     * @param reasons
     * @return
     */
    public String renderReasons(List<ReportReason> reasons);
    
    public List<ReportReason> decodeReasons(String reasons) throws Exception;
}
