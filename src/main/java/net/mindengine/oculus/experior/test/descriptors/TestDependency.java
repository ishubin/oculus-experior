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
     * {@link TestDefinition} class)
     */
    private String refTestId;

    /**
     * The name if the output parameter of prerequisite test
     */
    private String refParameterName;

    /**
     * The name of the input parameter of the dependent test
     */
    private String dependentParameterName;

    public String getRefTestId() {
        return refTestId;
    }

    public void setRefTestId(String refTestId) {
        this.refTestId = refTestId;
    }

    public String getRefParameterName() {
        return refParameterName;
    }

    public void setRefParameterName(String refParameterName) {
        this.refParameterName = refParameterName;
    }

    public String getDependentParameterName() {
        return dependentParameterName;
    }

    public void setDependentParameterName(String dependentParameterName) {
        this.dependentParameterName = dependentParameterName;
    }

}
