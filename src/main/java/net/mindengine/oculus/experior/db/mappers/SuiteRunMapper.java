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
