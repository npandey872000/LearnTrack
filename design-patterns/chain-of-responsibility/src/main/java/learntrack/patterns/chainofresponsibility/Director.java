package learntrack.patterns.chainofresponsibility;

/** Final handler: a director can approve up to 10 days. */
public final class Director extends Approver {
    public Director() {
        super("Director");
    }

    @Override
    protected boolean canApprove(LeaveRequest request) {
        return request.getDays() <= 10;
    }
}
