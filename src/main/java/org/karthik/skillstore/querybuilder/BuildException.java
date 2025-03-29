package org.karthik.skillstore.querybuilder;

public class BuildException extends Exception {
    public BuildException(String message) {
        super(message);
    }

    public BuildException(String message, Throwable clause) {
        super(message,clause);
    }
}
