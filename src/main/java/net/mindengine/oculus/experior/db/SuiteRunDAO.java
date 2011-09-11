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
