package learntrack.patterns.chainofresponsibility;

/** Second handler: a manager can approve up to 5 days. */
public final class Manager extends Approver {
    public Manager() {
        super("Manager");
    }

    @Override
    protected boolean canApprove(LeaveRequest request) {
        return request.getDays() <= 5;
    }
}
