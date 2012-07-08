package net.mindengine.oculus.experior.reporter.remote.wrappers;

import java.util.Date;
import java.util.List;

import net.mindengine.oculus.experior.reporter.ReportReason;

public class TestRun {
    private Long id;
    private Long suiteRunId;
    private String name;
    private String description;
    private String project;
    private List<ReportReason> reasons;
    private String report;
    private TestRunStatus status = TestRunStatus.PASSED;
    private List<TestRunParameter> parameters;
    private long startTime = new Date().getTime();
    private long endTime = new Date().getTime();
    
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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public long getStartTime() {
        return startTime;
    }
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    public long getEndTime() {
        return endTime;
    }
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
    public String getProject() {
        return project;
    }
    public void setProject(String project) {
        this.project = project;
    }
    public List<TestRunParameter> getParameters() {
        return parameters;
    }
    public void setParameters(List<TestRunParameter> parameters) {
        this.parameters = parameters;
    }
}
