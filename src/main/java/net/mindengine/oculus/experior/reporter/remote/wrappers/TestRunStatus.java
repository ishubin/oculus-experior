package net.mindengine.oculus.experior.reporter.remote.wrappers;

public enum TestRunStatus {
    
    PASSED,WARNING,FAILED;
    
    public String toString() {
        if ( this.equals(PASSED) ) {
            return "PASSED";
        }
        else if ( this.equals(FAILED) ) {
            return "FAILED";
        }
        else if ( this.equals(WARNING) ) {
            return "WARNING";
        }
        else return "";
    }

}
