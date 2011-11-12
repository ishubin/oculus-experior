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
package net.mindengine.oculus.experior.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestRunDAO extends SimpleDAO {
    private Log logger = LogFactory.getLog(getClass());

    protected TestRunDAO(OculusSimpleJdbcDaoSupport daoSupport) {
        super(daoSupport);
    }

    public Long create(TestRunBean testRunBean) throws Exception {
        Connection connection = daoSupport.getSQLConnection();
        PreparedStatement ps = connection.prepareStatement("insert into test_runs " + "(suite_run_id, " + "test_id, " + "start_time, " + "end_time, " + "reasons, " + "report, " + "name, "
                + "status, " + "project_id, description) " + "values (?,?,?,?,?,?,?,?,?,?)");

        if (testRunBean.getReasons() == null) {
            testRunBean.setReasons("");
        }
        if (testRunBean.getName() == null) {
            testRunBean.setName("");
        }
        ps.setLong(1, testRunBean.getSuiteRunId());
        ps.setLong(2, testRunBean.getTestId());
        ps.setTimestamp(3, new Timestamp(testRunBean.getStartTime().getTime()));
        ps.setTimestamp(4, new Timestamp(testRunBean.getEndTime().getTime()));
        ps.setString(5, testRunBean.getReasons());
        ps.setString(6, testRunBean.getReport());
        ps.setString(7, testRunBean.getName());
        ps.setString(8, testRunBean.getStatus());
        ps.setLong(9, testRunBean.getProjectId());
        ps.setString(10, testRunBean.getDescription());
        logger.info(ps);
        ps.execute();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getLong(1);
        }

        return null;
    }

    public Long createTestRunParameters(Long testRunId, String name, String value, boolean input) throws SQLException {
        Connection connection = daoSupport.getSQLConnection();
        PreparedStatement ps = connection.prepareStatement("insert into test_run_parameters (test_run_id, name, value, type) values (?,?,?,?)");

        ps.setLong(1, testRunId);
        ps.setString(2, name);
        ps.setString(3, value);
        if (input) {
            ps.setString(4, "input");
        } else
            ps.setString(4, "output");

        logger.info(ps);
        ps.execute();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getLong(1);
        }

        return null;
    }
}
