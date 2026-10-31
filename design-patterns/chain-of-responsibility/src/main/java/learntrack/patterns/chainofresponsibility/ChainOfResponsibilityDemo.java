package learntrack.patterns.chainofresponsibility;

/** Runs a few leave requests through the approval chain. */
public final class ChainOfResponsibilityDemo {
    private ChainOfResponsibilityDemo() {
    }

    public static void main(String[] args) {
        Approver teamLead = new TeamLead();
        Approver manager = new Manager();
        Approver director = new Director();

        teamLead.setNext(manager).setNext(director);

        teamLead.handle(new LeaveRequest("Alex", 1));
        teamLead.handle(new LeaveRequest("Sam", 4));
        teamLead.handle(new LeaveRequest("Taylor", 8));
        teamLead.handle(new LeaveRequest("Jordan", 12));
    }
}
