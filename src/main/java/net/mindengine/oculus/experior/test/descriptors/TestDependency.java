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
package net.mindengine.oculus.experior.test.descriptors;

import java.io.Serializable;

/**
 * Used for test dependency definition in the range of one suite.
 * 
 * @author Ivan Shubin
 * 
 */
public class TestDependency implements Serializable {
    private static final long serialVersionUID = -5156936488495851830L;

    /**
     * The id of the prerequisite test. (Usually it is a customId of test in
     * oculus.testrunframework.test.TestDescriptor class)
     */
    private Long prerequisiteTestId;

    /**
     * The name if the output parameter of prerequisite test
     */
    private String prerequisiteParameterName;

    /**
     * The name of the input parameter of the dependent test
     */
    private String dependentParameterName;

    public Long getPrerequisiteTestId() {
        return prerequisiteTestId;
    }

    public void setPrerequisiteTestId(Long prerequisiteTestId) {
        this.prerequisiteTestId = prerequisiteTestId;
    }

    public String getPrerequisiteParameterName() {
        return prerequisiteParameterName;
    }

    public void setPrerequisiteParameterName(String prerequisiteParameterName) {
        this.prerequisiteParameterName = prerequisiteParameterName;
    }

    public String getDependentParameterName() {
        return dependentParameterName;
    }

    public void setDependentParameterName(String dependentParameterName) {
        this.dependentParameterName = dependentParameterName;
    }

}
