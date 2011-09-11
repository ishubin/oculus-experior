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
