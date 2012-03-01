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
package net.mindengine.oculus.experior.cstat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import net.mindengine.oculus.experior.db.OculusSimpleJdbcDaoSupport;

/**
 * 
 * @author ishubin
 * 
 */
public class DefaultCustomStatistic implements CustomStatistic {

    private Collection<CustomStatisticData> dataSet;

    /**
     * Id of custom statistic in Database
     */
    private Long customStatisticId;

    public void addData(CustomStatisticData data) {
        if (dataSet == null)
            dataSet = new LinkedList<CustomStatisticData>();
        dataSet.add(data);
    }

    public void addData(Date date, long value, CSParameter... parameters) {
        addData(new CustomStatisticData(date, value, CSParameter.toCollection(parameters)));
    }

    public void addData(Date date, float value, CSParameter... parameters) {
        addData(new CustomStatisticData(date, value, CSParameter.toCollection(parameters)));
    }

    /**
     * Used to reduce the amount of connection to Database in order to fetch the
     * id of custom statistic parameter
     */
    private Map<String, Long> parametersCache = new HashMap<String, Long>();

    public void save() throws Exception {
        if (dataSet != null) {
            OculusSimpleJdbcDaoSupport daoSupport = OculusSimpleJdbcDaoSupport.getInstance();
            Connection connection = daoSupport.getSQLConnection();

            if (customStatisticId == null || customStatisticId < 1)
                throw new IllegalArgumentException("customStatisticId should be a positive value");

            for (CustomStatisticData data : dataSet) {
                /*
                 * Checking that the value is of supported type and not null
                 */
                if (data.getValue() != null && (data.getValue() instanceof Long || data.getValue() instanceof Float)) {
                    PreparedStatement ps = connection.prepareStatement("insert into custom_statistic_data (custom_statistic_id, value, time) values (?,?,?)");
                    ps.setLong(1, customStatisticId);
                    ps.setString(2, data.getValue().toString());
                    ps.setTimestamp(3, new Timestamp(data.getDate().getTime()));

                    ps.execute();

                    ResultSet rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        Long dataId = rs.getLong(1);
                        /*
                         * Saving all parameter values
                         */

                        if (data.getParameters() != null) {
                            for (CSParameter parameter : data.getParameters()) {
                                Long pId = parametersCache.get(parameter.getShortName());
                                if (pId == null) {
                                    // Fetching the parameter id form database
                                    try {
                                        pId = daoSupport.getSimpleJdbcTemplate().queryForLong("select id from custom_statistic_parameters where short_name = ? and custom_statistic_id = ?",
                                                parameter.getShortName(), customStatisticId);
                                        parametersCache.put(parameter.getShortName(), pId);
                                    } catch (Exception e) {
                                        // this means that the specified
                                        // parameter wasn't found in Database
                                        e.printStackTrace();
                                    }
                                }

                                if (pId != null) {
                                    ps = connection.prepareStatement("insert into custom_statistic_data_parameters (custom_statistic_data_id, custom_statistic_parameter_id, value) values (?,?,?)");
                                    ps.setLong(1, dataId);
                                    ps.setLong(2, pId);
                                    ps.setString(3, parameter.getValue());
                                    try {
                                        ps.execute();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void setCustomStatisticId(Long customStatisticId) {
        this.customStatisticId = customStatisticId;
    }

    public Long getCustomStatisticId() {
        return customStatisticId;
    }

}
