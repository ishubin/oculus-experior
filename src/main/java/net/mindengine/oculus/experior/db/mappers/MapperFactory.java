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

import net.mindengine.oculus.experior.db.ProjectBean;
import net.mindengine.oculus.experior.db.SuiteRunBean;
import net.mindengine.oculus.experior.db.TestBean;
import net.mindengine.oculus.experior.db.TestRunBean;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class MapperFactory {
    public static ParameterizedRowMapper<Object> getRowMapper(Class<?> clazz) {
        if (clazz.equals(SuiteRunBean.class)) {
            return new SuiteRunMapper();
        } else if (clazz.equals(TestRunBean.class)) {
            return new TestRunMapper();
        } else if (clazz.equals(ProjectBean.class)) {
            return new ProjectMapper();
        } else if (clazz.equals(TestBean.class)) {
            return new TestMapper();
        } else
            return null;
    }
}
