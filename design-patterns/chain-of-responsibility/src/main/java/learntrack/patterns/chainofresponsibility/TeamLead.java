package learntrack.patterns.chainofresponsibility;

/** First handler: a team lead can approve up to 2 days. */
public final class TeamLead extends Approver {
    public TeamLead() {
        super("Team Lead");
    }

    @Override
    protected boolean canApprove(LeaveRequest request) {
        return request.getDays() <= 2;
    }
}
