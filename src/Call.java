/**
 * Created by Fredrik on 3/17/2015.
 */
public class Call {

    private Floor from;
    private Floor to;
    private double timeRequest;
    private double timeArrived;

    private Car assignee;

    private boolean finished;

    public Call (Floor from, Floor to) {
        this.from = from;
        this.to = to;

    }
}
