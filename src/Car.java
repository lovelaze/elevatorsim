import java.util.ArrayList;

/**
 * Created by Bengan on 3/17/2015.
 */

public class Car {

	public enum Direction {
        Idle, Left, Right, Up, Down
	}

    private Direction direction;
    private Floor location;
    private ArrayList<Passenger> passengers;
    private ArrayList<Call> assigendCalls;

    public Car(Floor location) {
        direction = Direction.Idle;
        this.location = location;
    }

    public Floor getLocation() {
        return location;
    }

    public Direction getDirection() {
        return direction;
    }

    public void assignTo(Call call) {
    	assigendCalls.add(call);
    }

    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
    }

    public void removePassenger(Passenger passenger) {
        passengers.remove(passenger);
    }

    public void emptyCar() {
        passengers.clear();
    }

    public boolean isEmpty() {
        return passengers.isEmpty();
    }

}