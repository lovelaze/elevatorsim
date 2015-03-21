import java.util.ArrayList;

/**
 * Created by Bengan on 3/17/2015.
 */

public class Car {

	public enum Direction {
        Idle, Left, Right, Up, Down
	}

    private Direction direction;
    private Floor location, destination;
    private ArrayList<Passenger> passengers;
    private Call assignedCall;
    private int progress = 0;



    public Car(Floor location) {
        direction = Direction.Idle;
        this.location = location;
        destination = location;
        passengers = new ArrayList<Passenger>();
    }

    public Call getAssignedCall() {
        return assignedCall;
    }

    public boolean isBusy() {
        return direction != Direction.Idle || assignedCall != null;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public void move(Building building){

        if(destination.getLevel() < location.getLevel())
            direction = Direction.Down;
        else if(destination.getLevel() > location.getLevel())
            direction = Direction.Up;
        else if(destination.getLevel() == location.getLevel())
            direction = Direction.Idle;

        switch (direction) {
            case Idle:
                break;
            case Left:
                break;

            case Right:
                break;
            case Up:
                progress++;
                break;
            case Down:
                progress--;
               break;
        }

        if (progress >= Parameters.travelTicks) {
            location = building.getAboveFloor(location);
            System.out.println("level = "+ getLocation().getLevel());
            progress = 0;
        } else if (progress <= -Parameters.travelTicks) {
            location = building.getUnderFloor(location);
            System.out.println("level = "+ getLocation().getLevel());
            progress = 0;
        }

    }

    public void stop() {
        direction = Car.Direction.Idle;
        assignedCall = null;
        destination = location;
    }

    public int getProgress() {
        return progress;
    }

    public Floor getLocation() {
        return location;
    }

    public Direction getDirection() {
        return direction;
    }

    public void assignTo(Call call) {
        call.setAssignee(this);
        assignedCall = call;
        destination = call.getFrom();
    }

    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
    }

    public void setDestination(Floor destination) {
        this.destination = destination;
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