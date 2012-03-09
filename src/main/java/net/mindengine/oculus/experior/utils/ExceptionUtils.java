package net.mindengine.oculus.experior.utils;

public class ExceptionUtils {

	public static String convertExceptionToString(Throwable exception) {
		StringBuilder text = new StringBuilder();
		text.append(exception.getClass().getName());
		String message = exception.getMessage();
		if( message != null ) {
			text.append(": ").append(message);
		}
		text.append("\n").append(convertStackTraceToString(exception.getStackTrace()));
		
		if( exception.getCause() != null ) {
			text.append("\nCaused by:\n").append(convertExceptionToString(exception.getCause()));
		}
		
		return text.toString();
	}

	private static String convertStackTraceToString(StackTraceElement[] stackTrace) {
		StringBuilder text = new StringBuilder();
		if( stackTrace != null ) {
			for(StackTraceElement element : stackTrace) {
				String fileName = element.getFileName();
				if( fileName == null ) {
					fileName = "Unkown file";
				}
			    Integer lineNumber = element.getLineNumber();
			    text.append("  at ")
					.append(element.getClassName()).append(".").append(element.getMethodName())
					.append("(").append(fileName).append(":").append(lineNumber).append(")\n");
			}
		}
		return text.toString();
	}
}
