package net.mindengine.oculus.experior.reporter.remote.wrappers;

import java.util.List;
import java.util.Map;

import net.mindengine.oculus.experior.reporter.ReportReason;

public class TestRun {
    
        
    private Long id;
    private Long suiteRunId;
    private String name;
    private Long projectId;
    private List<ReportReason> reasons;
    private String report;
    private TestRunStatus status;
    private Map<String, String> parameters;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getSuiteRunId() {
        return suiteRunId;
    }
    public void setSuiteRunId(Long suiteRunId) {
        this.suiteRunId = suiteRunId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getProjectId() {
        return projectId;
    }
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
    public List<ReportReason> getReasons() {
        return reasons;
    }
    public void setReasons(List<ReportReason> reasons) {
        this.reasons = reasons;
    }
    public String getReport() {
        return report;
    }
    public void setReport(String report) {
        this.report = report;
    }
    public TestRunStatus getStatus() {
        return status;
    }
    public void setStatus(TestRunStatus status) {
        this.status = status;
    }
    public Map<String, String> getParameters() {
        return parameters;
    }
    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }
}
