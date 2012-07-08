package net.mindengine.oculus.experior.reporter.remote.wrappers;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class SuiteRun implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4200173324630300841L;
    private Long id;
    private Long startTime = new Date().getTime();
    private Long endTime = new Date().getTime();
    private String name;
    private Long runnerId;
    private Map<String, String> parameters;
    private String agentName;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getRunnerId() {
        return runnerId;
    }
    public void setRunnerId(Long runnerId) {
        this.runnerId = runnerId;
    }
    public String getAgentName() {
        return agentName;
    }
    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }
    public Map<String, String> getParameters() {
        return parameters;
    }
    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }
    public Long getEndTime() {
        return endTime;
    }
    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
    public Long getStartTime() {
        return startTime;
    }
    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }
    
}
