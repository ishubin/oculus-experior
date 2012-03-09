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
package net.mindengine.oculus.experior.reporter;

import java.util.Set;

public class ReportBranchConfiguration {

    private String name;
    private Set<String> parentBranches;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Set<String> getParentBranches() {
        return parentBranches;
    }
    public void setParentBranches(Set<String> parentBranches) {
        this.parentBranches = parentBranches;
    }
    
    /**
     * Checks whether this branch is allowed to be added to specific parent branch
     * @param branch Parent branch
     * @return
     */
    public Boolean allowsParent(String branch) {
        if(parentBranches!=null) {
            return parentBranches.contains(branch);
        }
        return false;
    }
}
