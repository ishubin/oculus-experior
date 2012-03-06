package net.mindengine.oculus.experior.reporter.nodes;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import net.mindengine.oculus.experior.reporter.Report;
import net.mindengine.oculus.experior.reporter.ReportReason;

public abstract class ReportNode {
    
    public static final String ERROR = "error".intern();
    public static final String WARN = "warn".intern();
    public static final String INFO = "info".intern();
    
    private transient Report report;
    
    private transient BranchReportNode parentBranch;
    
    private String title;
    private String id;
    private String icon;
    private boolean debug = false;
    private Date date = new Date();
    private String hint;
    private String level = INFO;
    public Report getReport() {
        return report;
    }
    public void setReport(Report report) {
        this.report = report;
    }
    public BranchReportNode getParentBranch() {
        return parentBranch;
    }
    public void setParentBranch(BranchReportNode parentBranch) {
        this.parentBranch = parentBranch;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public boolean getDebug() {
        return debug;
    }
    public void setDebug(boolean debug) {
        this.debug = debug;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getHint() {
        return hint;
    }
    public void setHint(String hint) {
        this.hint = hint;
    }
    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * Checkes whether the report is of specified level, in case of branch nodes it also checkes of it has specified level nodes in child nodes
     * @param level
     * @return
     */
    public boolean hasLevel(String level) {
        return StringUtils.equals(this.level, level);
    }
    
    /**
     * Collects reasons for specified level 
     * @param levels
     * @return
     */
    public abstract List<ReportReason> collectReasons(String...levels);
}
