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

import java.sql.Connection;
import java.util.List;

import net.mindengine.oculus.experior.ExperiorConfig;
import net.mindengine.oculus.experior.db.mappers.MapperFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class OculusSimpleJdbcDaoSupport extends SimpleJdbcDaoSupport {
    protected Log logger = LogFactory.getLog(getClass());

    private static OculusSimpleJdbcDaoSupport _instance = null;

    private OculusSimpleJdbcDaoSupport() {
        super();
        ExperiorConfig config = ExperiorConfig.getInstance();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(config.get(ExperiorConfig.REPORTING_DB_DRIVERCLASSNAME));
        dataSource.setUrl(config.get(ExperiorConfig.REPORTING_DB_URL));
        dataSource.setUsername(config.get(ExperiorConfig.REPORTING_DB_USERNAME));
        dataSource.setPassword(config.get(ExperiorConfig.REPORTING_DB_PASSWORD));
        setDataSource(dataSource);
    }

    public static OculusSimpleJdbcDaoSupport getInstance() {
        if (_instance == null) {
            _instance = new OculusSimpleJdbcDaoSupport();
        }
        return _instance;
    }

    @SuppressWarnings({ "unchecked"})
    public <T> List<T> query(String sql, Class<T> mapperClass, Object... args) throws Exception {
        logQuery(sql, args);
        if (args == null)
            return (List<T>) getSimpleJdbcTemplate().query(sql, MapperFactory.getRowMapper(mapperClass));

        MapSqlParameterSource map = new MapSqlParameterSource();
        for (int i = 0; i < args.length; i += 2) {
            map.addValue((String) args[i], args[i + 1]);
        }
        return (List<T>) getSimpleJdbcTemplate().query(sql, MapperFactory.getRowMapper(mapperClass), map);
    }

    /**
     * Returns the long value of counts of entities returned by sql query
     */
    public Long count(String sql, Object... args) throws Exception {
        MapSqlParameterSource map = new MapSqlParameterSource();
        for (int i = 0; i < args.length; i += 2) {
            map.addValue((String) args[i], args[i + 1]);
        }
        return getSimpleJdbcTemplate().queryForLong(sql, map);
    }

    public int update(String sql, Object... args) throws Exception {
        logQuery(sql, args);
        if (args == null)
            return getSimpleJdbcTemplate().update(sql);

        MapSqlParameterSource map = new MapSqlParameterSource();
        for (int i = 0; i < args.length; i += 2) {
            map.addValue((String) args[i], args[i + 1]);
        }
        return getSimpleJdbcTemplate().update(sql, map);
    }

    private void logQuery(String sql, Object[] args) {
        if (args != null) {

        }
        for (int i = 0; i < args.length - 1; i += 2) {
            sql = sql.replace(":" + args[i], "" + args[i + 1]);
        }

        logger.info(sql);
    }

    public ProjectDAO getProjectDAO() {
        return new ProjectDAO(this);
    }

    public SuiteRunDAO getSuiteRunDAO() {
        return new SuiteRunDAO(this);
    }

    public TestDAO getTestDAO() {
        return new TestDAO(this);
    }

    public TestRunDAO getTestRunDAO() {
        return new TestRunDAO(this);
    }

    public Connection getSQLConnection() {
        return super.getConnection();
    }
}
