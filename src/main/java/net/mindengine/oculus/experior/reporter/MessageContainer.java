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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Used for storing message templates which are configured in experior.properties
 * @author ishubin
 *
 */
public class MessageContainer {

	private Map<String, String> messages = new HashMap<String, String>();
	
	public MessageBuilder message(String name) {
		return message(name, String.format("\"%s\" is not configured in message properties", name));
	}

	public MessageBuilder message(String name, String defaultValue) {
		if( messages != null ) {
			if ( messages.containsKey(name)) {
				return new MessageBuilder(messages.get(name));
			}
		}
		return new MessageBuilder(defaultValue);
	}

	public Map<String, String> getMessages() {
		return messages;
	}

	public void setMessages(Map<String, String> messages) {
		this.messages = messages;
	}

	public void loadMessages(Properties messageProperties) {
		if( messages == null ) {
			messages = new HashMap<String, String>();
		}
		
		if( messageProperties != null ) {
			Iterator<String> propertyIterator = messageProperties.stringPropertyNames().iterator();
			while( propertyIterator.hasNext() ) {
				String name = propertyIterator.next();
				String value = messageProperties.getProperty(name);
				messages.put(name, value);
			}
		}
	}
}
