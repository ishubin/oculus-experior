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

import java.util.Date;

/**
 * Used for storing and saving data for custom statistic
 * 
 * @author ishubin
 * 
 */
public interface CustomStatistic {

    /**
     * Saves all custom statistic data
     * 
     * @throws Exception
     */
    public void save() throws Exception;

    /**
     * Saves data in long format
     * 
     * @param date
     * @param value
     * @param parameters
     */
    public void addData(Date date, long value, CSParameter... parameters);

    /**
     * Saves data in float format
     * 
     * @param date
     * @param value
     * @param parameters
     */
    public void addData(Date date, float value, CSParameter... parameters);

}
