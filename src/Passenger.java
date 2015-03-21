/**
 * Created by Bengan on 3/17/2015.
 */
public class Passenger {

    private Floor start;
    private Floor destination;
    private Call call;
    private Car.Direction direction;

    public Passenger(Floor start, Floor destination) {
        this.start = start;
        this.destination = destination;
        call = null;
        if(start.getLevel() - destination.getLevel() > 0)
            direction = Car.Direction.Up;
        else if(start.getLevel() - destination.getLevel() < 0)
            direction = Car.Direction.Down;
    }

    public Call makeCall() {
    	return new Call(start, destination, this);

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
