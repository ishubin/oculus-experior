package net.mindengine.oculus.experior.reporter;

import java.util.HashMap;
import java.util.Map;

public class MessageBuilder {

	private String template;
	private Map<String, Object> variables = new HashMap<String, Object>();
	
	public MessageBuilder(String template) {
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
		String text = template;
		if( text == null ) {
			text = "";
		}
		
		text = text.trim();
		
		for(Map.Entry<String, Object> var : variables.entrySet()) {
			Object varValue = var.getValue();
			if (varValue == null) {
				varValue = "";
			}
			text = text.replace("${" + var.getKey() + "}", varValue.toString());
		}
		return text;
	}
	
	public MessageBuilder setTemplate(String template) {
		this.template = template;
		return this;
	}
}
