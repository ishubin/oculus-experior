package net.mindengine.oculus.experior.framework.verification.text;

public class AssertionTextVerificator implements TextVerificator {

	private TextVerificator textVerificator;
	
	public AssertionTextVerificator(TextVerificator textVerificator) {
		this.textVerificator = textVerificator;
	}
	
	private String errorMsg(String method, String expected) {
		String got = textVerificator.realValue();
		return "Expected: " +method + " \"" + expected + "\" \nGot: \"" + got + "\"";
	}
	
	public String realValue() {
		return textVerificator.realValue();
	}
	
	@Override
	public boolean is(String string) {
		if (!textVerificator.is(string)) {
			throw new AssertionError(errorMsg("is", string));
		}
		return true;
	}

	@Override
	public boolean isNot(String string) {
		if (!textVerificator.isNot(string)) {
			throw new AssertionError(errorMsg("isNot", string));
		}
		return true;
	}

	@Override
	public boolean contains(String string) {
		if (!textVerificator.contains(string)) {
			throw new AssertionError(errorMsg("contains", string));
		}
		return true;
	}

	@Override
	public boolean doesNotContain(String string) {
		if (!textVerificator.doesNotContain(string)) {
			throw new AssertionError(errorMsg("doesNotContain", string));
		}
		return true;
	}

	@Override
	public boolean startsWith(String string) {
		if (!textVerificator.startsWith(string)) {
			throw new AssertionError(errorMsg("startsWith", string));
		}
		return true;
	}

	@Override
	public boolean doesNotStartWith(String string) {
		if (!textVerificator.doesNotStartWith(string)) {
			throw new AssertionError(errorMsg("doesNotStartWith", string));
		}
		return true;
	}

	@Override
	public boolean endsWith(String string) {
		if (!textVerificator.endsWith(string)) {
			throw new AssertionError(errorMsg("endsWith", string));
		}
		return true;
	}

	@Override
	public boolean doesNotEndWith(String string) {
		if (!textVerificator.doesNotEndWith(string)) {
			throw new AssertionError(errorMsg("doesNotEndWith", string));
		}
		return true;
	}

	@Override
	public boolean matches(String string) {
		if (!textVerificator.matches(string)) {
			throw new AssertionError(errorMsg("matches", string));
		}
		return true;
	}

	@Override
	public boolean doesNotMatch(String string) {
		if (!textVerificator.doesNotMatch(string)) {
			throw new AssertionError(errorMsg("doesNotMatch", string));
		}
		return true;
	}

	@Override
	public boolean isOneOf(String... strings) {
		if (!textVerificator.isOneOf(strings)) {
			throw new AssertionError(errorMsgStrings("isOneOf", strings));
		}
		return true;
	}


	@Override
	public boolean isNotOneOf(String... strings) {
		if (!textVerificator.isNotOneOf(strings)) {
			throw new AssertionError(errorMsgStrings("isNotOneOf", strings));
		}
		return true;
	}

	private String errorMsgStrings(String method, String[] strings) {
		StringBuilder text = new StringBuilder("[");
		if ( strings != null ) {
			for ( int i=0; i<strings.length; i++ ) {
				if ( i>0 ) {
					text.append(",");
				}
				text.append(strings[i]);
			}
		}
		text.append("]");
		
		return "Expected: " + method + " " + text.toString() + ", Got: " + textVerificator.realValue() ;
	}

	@Override
	public TextVerificator toLowerCase() {
		return new AssertionTextVerificator(textVerificator.toLowerCase());
	}

	@Override
	public TextVerificator toUpperCase() {
		return new AssertionTextVerificator(textVerificator.toUpperCase());
	}

	@Override
	public TextVerificator replace(String seek, String replace) {
		return new AssertionTextVerificator(textVerificator.replace(seek, replace));
	}

	@Override
	public TextVerificator substring(int id1, int id2) {
		return new AssertionTextVerificator(textVerificator.substring(id1, id2));
	}

	@Override
	public TextVerificator substring(int id) {
		return new AssertionTextVerificator(textVerificator.substring(id));
	}

	@Override
	public TextVerificator replaceAll(String target, String replacement) {
		return new AssertionTextVerificator(textVerificator.replaceAll(target, replacement));
	}

}
