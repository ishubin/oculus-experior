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
package net.mindengine.oculus.experior.reporter;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Template;

public class MessageBuilder {

	private Template template;
	private Map<String, Object> variables = new HashMap<String, Object>();
	
	public MessageBuilder(Template template) {
		this.template = template;
	}
	
	public MessageBuilder() {
	}
	
	public MessageBuilder put(String name, Object value) {
		variables.put(name, value);
		return this;
	}
	
	public MessageBuilder putAll(Map<String, Object> map) {
		variables.putAll(map);
		return this;
	}
	
	@Override
	public String toString() {
	    if ( template != null ) {
	        StringWriter sw = new StringWriter();
	        try {
                template.process(variables, sw);
            } catch (Exception e) {
                throw new RuntimeException("Couldn't process template", e);
            }
	        return sw.toString();
	    }
	    else {
	        return "";
	    }
	    
	}
	
	public MessageBuilder setTemplate(Template template) {
		this.template = template;
		return this;
	}
}
