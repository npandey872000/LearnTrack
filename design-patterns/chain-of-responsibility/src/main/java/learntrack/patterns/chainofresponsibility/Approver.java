package learntrack.patterns.chainofresponsibility;

/**
 * Base handler in the chain. Concrete approvers decide whether to approve
 * a request or pass it to the next handler.
 */
public abstract class Approver {
    private final String title;
    private Approver nextApprover;

    protected Approver(String title) {
        this.title = title;
    }

    /** Connects this handler to the next handler and returns it for easy setup. */
    public Approver setNext(Approver nextApprover) {
        this.nextApprover = nextApprover;
        return nextApprover;
    }

    /** Handles the request here, or forwards it along the chain. */
    public final void handle(LeaveRequest request) {
        if (canApprove(request)) {
            System.out.println(title + " approved " + request.getDays()
                    + " day(s) for " + request.getEmployeeName() + ".");
        } else if (nextApprover != null) {
            System.out.println(title + " cannot approve " + request.getDays()
                    + " day(s); forwarding the request.");
            nextApprover.handle(request);
        } else {
            System.out.println("No approver can approve " + request.getDays()
                    + " day(s) for " + request.getEmployeeName() + ". Request declined.");
        }
    }

    protected abstract boolean canApprove(LeaveRequest request);
}
