/**
 * Created by Bengan on 3/17/2015.
 */
public class Call {

    private Floor from;
    private Floor to;
    private long timeRequest;
    private long timePickedUp;
    private long timeArrived;

    private Car assignee;

    private boolean finished;

    public Call(Floor from, Floor to) {
        this.from = from;
        this.to = to;
        timeRequest = System.currentTimeMillis();
    }

    public void setFinished() {
        finished = true;
        timeArrived = System.currentTimeMillis();
    }

    public Floor getFrom() {
        return from;
    }

    public Floor getTo() {
        return to;
    }

    public Floor passengerPickedUp() {
        timePickedUp = System.currentTimeMillis();
        return to;
    }

    public long getTimeRequest() {
        return timeRequest;
    }

    public long getTimeArrived() {
        return timeArrived;
    }

    public long waitingTime() {
        return timePickedUp-timeRequest;
    }

    public long journeyTime()  {
        return timePickedUp-timeRequest;
    }

    public Car getAssignee() {
        return assignee;
    }

    public boolean isFinished() {
        return finished;
    }
}
