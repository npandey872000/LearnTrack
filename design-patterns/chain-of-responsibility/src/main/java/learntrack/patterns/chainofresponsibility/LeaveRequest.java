package learntrack.patterns.chainofresponsibility;

/** A request to approve a number of days of leave. */
public final class LeaveRequest {
    private final String employeeName;
    private final int days;

    public LeaveRequest(String employeeName, int days) {
        if (employeeName == null || employeeName.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name must not be blank.");
        }
        if (days <= 0) {
            throw new IllegalArgumentException("Leave days must be greater than zero.");
        }
        this.employeeName = employeeName;
        this.days = days;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public int getDays() {
        return days;
    }
}
