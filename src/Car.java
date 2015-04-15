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
    private int number;
    private ArrayList<Call> waitingCalls;

    private int roundTripTime = 0;

    private int servedCalls = 0;

    public Car(Floor location, int number) {
        direction = Direction.Idle;
        this.location = location;
        destination = location;
        passengers = new ArrayList<Passenger>();
        waitingCalls = new ArrayList<Call>();
        this.number = number;
    }

    public Call getAssignedCall() {
        return assignedCall;
    }

    public boolean isMoving() {
        return direction != Direction.Idle;
    }

    public String getResult() {
        return "Elevator " + number + " served " + servedCalls + " calls.";
    }

    public boolean isBusy() {
        return direction != Direction.Idle || assignedCall != null;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public void move(Building building, int time){
        if(destination.getLevel() < location.getLevel()){
            direction = Direction.Down;
            if (location.getLevel() == building.getTerminalFloor().getLevel() )
                roundTripTime = time;
        } else if(destination.getLevel() > location.getLevel()){
            direction = Direction.Up;
            if (location.getLevel() == building.getTerminalFloor().getLevel() )
                roundTripTime = time;
        } else if(destination.getLevel() == location.getLevel()){
            direction = Direction.Idle;
            if (location.getLevel() == building.getTerminalFloor().getLevel() ){
                ElevatorEngine.R.totalTripTime += time - roundTripTime;
                ElevatorEngine.R.trips++;
            }
        }

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
            Log.log("Elevator " + number + " now at level = "+ getLocation().getLevel());
            progress = 0;
        } else if (progress <= -Parameters.travelTicks) {
            location = building.getUnderFloor(location);
            Log.log("Elevator " + number + " now at level = "+ getLocation().getLevel());
            progress = 0;
        }

    }

    public int getNumber() {
        return number;  
    }

    public void stop() {
        direction = Car.Direction.Idle;
        assignedCall = null;
        destination = location;
        passengers.clear();
        waitingCalls.clear();
    }

    public int getProgress() {
        return progress;
    }

    public Floor getLocation() {
        return location;
    }

    public Floor getDestination() {
        return destination;
    }

    public Direction getDirection() {
        return direction;
    }

    public void assignTo(Call call) {
        servedCalls++;
        call.setAssignee(this);
        if(assignedCall == null){
            assignedCall = call;
        } else {
            waitingCalls.add(call);
        }
        calculateDestination();
        /*if(assignedCall == null){
            assignedCall = call;
            destination = call.getFrom();   
        } else {
            int newDistance = call.isPickedUp() ? Math.abs(location.getLevel() - call.getTo().getLevel()) : Math.abs(location.getLevel() - call.getFrom().getLevel());
            int oldDistance = assignedCall.isPickedUp() ? Math.abs(location.getLevel() - assignedCall.getTo().getLevel()) : Math.abs(location.getLevel() - assignedCall.getFrom().getLevel());
            if(newDistance < oldDistance) {
                waitingCalls.add(assignedCall);
                assignedCall = call;
                destination = call.getFrom();
            } else {
                waitingCalls.add(call);
            }
        }*/
        Log.log("Elevator " + number + " has " + waitingCalls.size() + " waiting calls");
    }

    public void addPassenger(Passenger passenger, int time) {
        passengers.add(passenger);
        passenger.getCurrentCall().passengerPickedUp(time);
        calculateDestination();
    }

    public void setDestination(Floor destination) {
        this.destination = destination;
        if(destination.getLevel() < location.getLevel())
            direction = Direction.Down;
        else if(destination.getLevel() > location.getLevel())
            direction = Direction.Up;
        else if(destination.getLevel() == location.getLevel())
            direction = Direction.Idle;
    }

    public void removePassenger(Passenger passenger) {
        passengers.remove(passenger);
        waitingCalls.add(assignedCall);
        assignedCall = null;
        waitingCalls.remove(passenger.getCurrentCall());
        calculateDestination();
        ElevatorEngine.R.servedCalls++;
    }

    public void calculateDestination() {
        if (assignedCall != null) {
            waitingCalls.add(assignedCall);
            assignedCall = null;
        }
        if (!waitingCalls.isEmpty()) {
            for(Call call : waitingCalls) {
                if(assignedCall == null) {
                    assignedCall = call;
                } else {
                    int newDistance = call.isPickedUp() ? Math.abs(location.getLevel() - call.getTo().getLevel()) : Math.abs(location.getLevel() - call.getFrom().getLevel());
                    int oldDistance = assignedCall.isPickedUp() ? Math.abs(location.getLevel() - assignedCall.getTo().getLevel()) : Math.abs(location.getLevel() - assignedCall.getFrom().getLevel());
                    if (newDistance < oldDistance) {
                        assignedCall = call;
                    }
                }
            }
            waitingCalls.remove(assignedCall);
            destination = assignedCall.isPickedUp() ? assignedCall.getTo() : assignedCall.getFrom();
        } else if(waitingCalls.isEmpty()){
            //System.out.println("STOP");
            stop();
        }
    }

    public void emptyCar() {
        passengers.clear();
    }

    public boolean isEmpty() {
        return passengers.isEmpty();
    }

}