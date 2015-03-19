/**
 * Created by Bengan on 3/17/2015.
 */
public class Passenger {

    private Floor start;
    private Floor destination;
    private Call call;

    public Passenger(Floor start, Floor destination) {
        this.start = start;
        this.destination = destination;
        call = null;
    }

    public Call makeCall() {
    	call = new Call(start, destination);
    	return call;
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
