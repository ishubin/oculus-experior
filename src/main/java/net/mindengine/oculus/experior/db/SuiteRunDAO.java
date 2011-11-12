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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SuiteRunDAO extends SimpleDAO {
    private Log logger = LogFactory.getLog(getClass());

    protected SuiteRunDAO(OculusSimpleJdbcDaoSupport daoSupport) {
        super(daoSupport);
    }

    public void updateSuiteEndTime(Long id, Date date) throws Exception {
        daoSupport.update("update suite_runs set end_time = :date where id = :id", "id", id, "date", date);
    }

    public Long create(SuiteRunBean suite) throws Exception {
        PreparedStatement ps = daoSupport.getSQLConnection().prepareStatement("insert into suite_runs (start_time, end_time, name, runner_id, parameters, agent_name) " + "values (?,?,?,?,?,?)");

        ps.setTimestamp(1, new Timestamp(suite.getStartTime().getTime()));
        ps.setTimestamp(2, new Timestamp(suite.getEndTime().getTime()));
        ps.setString(3, suite.getName());
        if (suite.getRunnerId() == null)
            suite.setRunnerId(0L);
        ps.setLong(4, suite.getRunnerId());
        ps.setString(5, suite.getSerializedParameters());
        String agentName = suite.getAgentName();
        if (agentName == null)
            agentName = "";
        ps.setString(6, agentName);

        logger.info(ps);
        ps.execute();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getLong(1);
        }
        return null;
    }
}
