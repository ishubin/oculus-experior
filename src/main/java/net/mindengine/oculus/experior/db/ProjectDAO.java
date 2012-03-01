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
package net.mindengine.oculus.experior.db;

import java.util.List;

public class ProjectDAO extends SimpleDAO {
    protected ProjectDAO(OculusSimpleJdbcDaoSupport daoSupport) {
        super(daoSupport);
    }

    public ProjectBean getProjectByPath(String path) throws Exception {
        List<?> list = daoSupport.query("select * from projects where path = :path", ProjectBean.class, "path", path);
        if (list.size() > 0) {
            return (ProjectBean) list.get(0);
        }
        return null;
    }
}
