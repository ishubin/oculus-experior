package net.mindengine.oculus.experior.reporter;

import java.util.HashMap;
import java.util.Map;

public class MessageBuilder {

	private String template;
	private Map<String, String> variables = new HashMap<String, String>();
	
	public MessageBuilder(String template) {
		this.template = template;
	}
	
	public MessageBuilder put(String name, String value) {
		variables.put(name, value);
		return this;
	}
	
	@Override
	public String toString() {
		String text = template;
		if( text == null ) {
			text = "";
		}
		
		for(Map.Entry<String, String> var : variables.entrySet()) {
			text = text.replace("${" + var.getKey() + "}", var.getValue());
		}
		
		return text;
	}

}
