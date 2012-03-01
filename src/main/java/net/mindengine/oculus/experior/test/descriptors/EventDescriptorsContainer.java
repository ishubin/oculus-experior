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
package net.mindengine.oculus.experior.test.descriptors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class EventDescriptorsContainer implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -3472074946976587089L;
    private Map<String, EventDescriptor> descriptors = new HashMap<String, EventDescriptor>();

    public void setDescriptors(Map<String, EventDescriptor> descriptors) {
        this.descriptors = descriptors;
    }

    public Map<String, EventDescriptor> getDescriptors() {
        return descriptors;
    }
    
    
}
