/*******************************************************************************
 * Copyright 2011 Ivan Shubin
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
package net.mindengine.oculus.experior.db.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.mindengine.oculus.experior.db.TestBean;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class TestMapper implements ParameterizedRowMapper<Object> {

    public Object mapRow(ResultSet rs, int row) throws SQLException {
        TestBean testBean = new TestBean();
        testBean.setId(rs.getLong("id"));
        testBean.setName(rs.getString("name"));
        testBean.setDescription(rs.getString("description"));
        testBean.setDate(rs.getDate("date"));
        testBean.setMapping(rs.getString("mapping"));
        testBean.setProjectId(rs.getLong("project_id"));
        testBean.setAuthorId(rs.getLong("author_id"));

        return testBean;
    }
}
