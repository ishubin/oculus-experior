/*******************************************************************************
 * 2011 Ivan Shubin http://mindengine.net
 * 
 * This file is part of Oculus Experior.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Oculus Experior.  If not, see <http://www.gnu.org/licenses/>.
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
