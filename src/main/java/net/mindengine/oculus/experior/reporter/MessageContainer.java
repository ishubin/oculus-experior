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

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.Properties;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * Used for storing message templates which are configured in experior.properties
 * @author ishubin
 *
 */
public class MessageContainer {

    private Configuration templateConfiguration;
    
	
	public MessageBuilder message(String name) {
		return message(name, String.format("\"%s\" is not configured in message properties", name));
	}

	public MessageBuilder message(String name, String defaultValue) {
		if( templateConfiguration != null ) {
		    Template template = getTemplate(name);
			if ( template!=null) {
				return new MessageBuilder(template);
			}
		}
		Template template;
        try {
            template = new Template(name, new StringReader(defaultValue), templateConfiguration);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't process default template", e);
        }
		return new MessageBuilder(template);
	}

    private Template getTemplate(String name) {
        Template template = null;
        try {
            template = templateConfiguration.getTemplate(name);
        }
        catch (Exception e) {
        }
        return template;
    }

	public void loadMessages(Properties messageProperties) {
		if( templateConfiguration == null ) {
			templateConfiguration = new Configuration();
			templateConfiguration.setObjectWrapper(new DefaultObjectWrapper());
			templateConfiguration.setNumberFormat("0.######");
		}
		
		StringTemplateLoader templateLoader = new StringTemplateLoader();
		
		if( messageProperties != null ) {
			Iterator<String> propertyIterator = messageProperties.stringPropertyNames().iterator();
			while( propertyIterator.hasNext() ) {
				String name = propertyIterator.next();
				String value = messageProperties.getProperty(name);
				templateLoader.putTemplate(name, value);
			}
		}
		
		templateConfiguration.setTemplateLoader(templateLoader);
	}

    public Configuration getTemplateConfiguration() {
        return templateConfiguration;
    }

    public void setTemplateConfiguration(Configuration templateConfiguration) {
        this.templateConfiguration = templateConfiguration;
    }
}
