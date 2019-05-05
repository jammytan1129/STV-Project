package useCase;

public class ScriptInformation {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";


    private String taskName;

    private boolean executionResult;

    private double executionTime;

    public ScriptInformation(String taskName) {
        this.taskName = taskName;
    }

    public void setExecutionTime(double executionTime) {
        this.executionTime = executionTime;
    }

    private String resultStatus() {
        if (this.executionResult)
            return ANSI_GREEN + "PASS" + ANSI_RESET;
        else
            return ANSI_RED + "FAILED" + ANSI_RESET;
    }

    public String summary() {
        String leftAlignFormat = "| %-15s | %-15s | %-15s          |%n";
        return String.format(leftAlignFormat, taskName, executionTime + "ms", resultStatus());
    }

    public void executionComplete() {
        this.executionResult = true;
    }

    public void executionFailed() {
        this.executionResult = false;
    }
}
