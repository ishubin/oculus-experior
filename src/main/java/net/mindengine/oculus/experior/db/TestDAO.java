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

public class TestDAO extends SimpleDAO {
    protected TestDAO(OculusSimpleJdbcDaoSupport daoSupport) {
        super(daoSupport);
    }

    public TestBean getTestByNameProject(String testName, String projectPath) throws Exception {
        List<?> list = daoSupport.query("select tests.* from tests " + "left join projects on tests.project_id = projects.id " + "where tests.name = :name and projects.path = :projectPath",
                TestBean.class, "name", testName, "projectPath", projectPath);
        if (list.size() > 0) {
            return (TestBean) list.get(0);
        }
        return null;
    }
}
