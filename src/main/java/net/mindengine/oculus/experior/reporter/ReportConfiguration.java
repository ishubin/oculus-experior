package net.mindengine.oculus.experior.reporter;

import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class ReportConfiguration {
    
    private final static String REPORT_BRANCH = "report.branch.";
    private final static String PARENTS = "parents";
	private static final String OUTPUT_INDENTATION = "report.output.indentation";
	private static final String MESSAGES_PATH = "report.messages.path";

    private Map<String, ReportBranchConfiguration> branches = new HashMap<String, ReportBranchConfiguration>();

    private MessageContainer messageContainer = new MessageContainer();
    
    private PrintStream outputStreamOut = System.out;
    private PrintStream outputStreamErr = System.err;
    private Integer outputIndentation = 0;

    private ReportConfiguration(){
    }
    
    public static ReportConfiguration loadFromProperties(Properties properties) {
        ReportConfiguration reportConfiguration = new ReportConfiguration();
        
        loadBranchConfiguration(properties, reportConfiguration);
        loadStreamConfiguration(properties, reportConfiguration);
        loadMessageContainer(properties, reportConfiguration);
        return reportConfiguration;
    }

	private static void loadMessageContainer(Properties properties, ReportConfiguration reportConfiguration) {
		try {
			String messagesPropertiesPath = properties.getProperty(MESSAGES_PATH);
			if ( messagesPropertiesPath != null ) {
				Properties messageProperties = new Properties();
				messageProperties.load(new FileReader(new File(messagesPropertiesPath.trim())));
				reportConfiguration.getMessageContainer().loadMessages(messageProperties);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void loadStreamConfiguration(Properties properties, ReportConfiguration reportConfiguration) {
		String indent = properties.getProperty(OUTPUT_INDENTATION);
		if(indent != null) {
			reportConfiguration.setOutputIndentation(Integer.parseInt(indent.trim()));
		}
	}

	private static void loadBranchConfiguration(Properties properties, ReportConfiguration reportConfiguration) {
		for(String propertyName : properties.stringPropertyNames()) {
            if(propertyName.startsWith(REPORT_BRANCH)) {
                
                ReportBranchConfiguration branch = fetchBranchForProperty(reportConfiguration, propertyName);
                processBranchFields(branch, propertyName, properties.getProperty(propertyName));
                reportConfiguration.getBranches().put(branch.getName(), branch);
            }
        }
	}

    private static void processBranchFields(ReportBranchConfiguration branch, String propertyName, String property) {
        int length = branch.getName().length() + REPORT_BRANCH.length()+1;
        if(propertyName.length() > length) {
            String branchField = propertyName.substring(length).trim();
            if(branchField.equals(PARENTS)) {
                branch.setParentBranches(readBranchParents(property));
            }
        }
    }

    private static Set<String> readBranchParents(String property) {
        String[] parents = property.split(",");
        Set<String> branchParents = new HashSet<String>();
        for(String parent : parents) {
            branchParents.add(parent.trim());
        }
        return branchParents;
    }

    private static ReportBranchConfiguration fetchBranchForProperty(ReportConfiguration reportConfiguration, String propertyName) {
        String branchName = fetchBranchNameFromPropertyName(propertyName);
        ReportBranchConfiguration branch = reportConfiguration.getBranches().get(branchName);
        if(branch == null) {
            branch = new ReportBranchConfiguration();
            branch.setName(branchName);
        }
        return branch;
    }

    private static String fetchBranchNameFromPropertyName(String propertyName) {
        String name = null;
        
        int k = REPORT_BRANCH.length();
        if(propertyName.length()>k) {
            int id = propertyName.indexOf(".", k);
            
            if(id>0) {
                name = propertyName.substring(k, id);
            }
            else if (id<0) {
                name = propertyName.substring(k);
            }
        }
        if(name==null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Can't read property " + propertyName + " from properties file");
        }
        return name;
    }

    public Map<String, ReportBranchConfiguration> getBranches() {
        return branches;
    }

    public void setBranches(Map<String, ReportBranchConfiguration> branches) {
        this.branches = branches;
    }

	public Integer getOutputIndentation() {
		return outputIndentation;
	}

	public void setOutputIndentation(Integer outputIndentation) {
		this.outputIndentation = outputIndentation;
	}

	public PrintStream getOutputStreamErr() {
		return outputStreamErr;
	}

	public void setOutputStreamErr(PrintStream outputStreamErr) {
		this.outputStreamErr = outputStreamErr;
	}

	public PrintStream getOutputStreamOut() {
		return outputStreamOut;
	}

	public void setOutputStreamOut(PrintStream outputStreamOut) {
		this.outputStreamOut = outputStreamOut;
	}

	public MessageContainer getMessageContainer() {
		return messageContainer;
	}

	public void setMessageContainer(MessageContainer messageContainer) {
		this.messageContainer = messageContainer;
	}


}
