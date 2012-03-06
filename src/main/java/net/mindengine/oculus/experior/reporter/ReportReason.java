package net.mindengine.oculus.experior.reporter;

public class ReportReason {

    private String reason;
    private String level;
    
    public ReportReason() {
    }
    
    public String getReason() {
        return reason;
    }
    public ReportReason setReason(String reason) {
        this.reason = reason;
        return this;
    }
    public String getLevel() {
        return level;
    }
    public ReportReason setLevel(String level) {
        this.level = level;
        return this;
    }
}
