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
package net.mindengine.oculus.experior.db.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import net.mindengine.oculus.experior.db.SuiteRunBean;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class SuiteRunMapper implements ParameterizedRowMapper<Object> {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        SuiteRunBean bean = new SuiteRunBean();
        bean.setId(rs.getLong("id"));
        bean.setEndTime(rs.getDate("end_time"));
        bean.setStartTime(rs.getDate("start_time"));
        bean.setName(rs.getString("name"));
        bean.setRunnerId(rs.getLong("runner_id"));
        return bean;
    }
}
