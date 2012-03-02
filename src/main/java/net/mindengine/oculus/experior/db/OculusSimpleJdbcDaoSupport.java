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

        dataSource.setDriverClassName(config.get(ExperiorConfig.REPORT_DB_DRIVERCLASSNAME));
        dataSource.setUrl(config.get(ExperiorConfig.REPORT_DB_URL));
        dataSource.setUsername(config.get(ExperiorConfig.REPORT_DB_USERNAME));
        dataSource.setPassword(config.get(ExperiorConfig.REPORT_DB_PASSWORD));
        setDataSource(dataSource);
    }

    public synchronized static OculusSimpleJdbcDaoSupport getInstance() {
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
