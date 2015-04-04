/**
 * Created by Bengan on 3/17/2015.
 */
public class Call {

    private Floor from;
    private Floor to;
    private int timeRequest;
    private int timePickedUp;
    private int timeArrived;

    private Car assignee;

    private boolean isPickedUp;
    private boolean finished;
    private Passenger caller;

    public Passenger getCaller() {
        return caller;
    }

    public Call(Floor from, Floor to, int time, Passenger caller) {
        this.from = from;

        this.to = to;
        this.caller = caller;
        timeRequest = time;
        isPickedUp = false;
        System.out.println("New call at time: " + time);
    }

    public void setFinished() {
        finished = true;
        timeArrived = 0; //TODO: Measure properly
    }

    public Floor getFrom() {
        return from;
    }

    public Floor getTo() {
        return to;
    }

    public Floor passengerPickedUp(int time) {
        timePickedUp = time; //TODO: Measure properly
        isPickedUp = true;
        return to;
    }

    public boolean isPickedUp() {
        return isPickedUp;
    }

    public int getTimeRequest() {
        return timeRequest;
    }

    public int getTimeArrived() {
        return timeArrived;
    }

    public int waitingTime() {
        return timePickedUp-timeRequest;
    }

    public int journeyTime()  {
        return timePickedUp-timeRequest;
    }

    public void setAssignee(Car assignee) {
        this.assignee = assignee;
    }

    public Car getAssignee() {
        return assignee;
    }

    public boolean isFinished() {
        return finished;
    }
}
