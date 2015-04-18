/**
 * Created by Bengan on 3/17/2015.
 */
public class Passenger {

    private Floor start;
    private Floor destination;
    private Shaft startShaft, destinationShaft;
    private Call call;
    private Car.Direction direction;

    public Passenger(Floor start, Floor destination) {
        this(start, destination, null, null);
    }

    public Passenger(Floor start, Floor destination, Shaft startShaft, Shaft destinationShaft) {
        this.start = start;
        this.destination = destination;
        this.startShaft = startShaft;
        this.destinationShaft = destinationShaft;
        call = null;
        if(start.getLevel() - destination.getLevel() > 0)
            direction = Car.Direction.Down;
        else if(start.getLevel() - destination.getLevel() < 0)
            direction = Car.Direction.Up;
        else if(startShaft.getIndex() < destinationShaft.getIndex())
            direction = Car.Direction.Right;
        else if(startShaft.getIndex() > destinationShaft.getIndex())
            direction = Car.Direction.Left;
    }

    public Call makeCall(int step) {
    	call = new Call(start, destination, startShaft, destinationShaft, step, this);
        return call;
    }

    public Car.Direction getDirection() {
        return direction;
    }

    public Call getCurrentCall() {
    	return call;
    }

    public Floor getDestination() {
        return destination;
    }

    public void setDestination(Floor destination) {
        this.destination = destination;
    }

    public Floor getStart() {
        return start;
    }

    public void setStart(Floor start) {
        this.start = start;
    }
}
