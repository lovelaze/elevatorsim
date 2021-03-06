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
    private Shaft shaftLocation, shaftDestination;
    private ArrayList<Passenger> passengers;
    private Call assignedCall;
    private int progress = 0, sideProgress = 0;
    private int number;
    private ArrayList<Call> waitingCalls;

    private int roundTripTime = 0;

    private int servedCalls = 0;

    public Car(Floor location, int number, Shaft shaftLocation) {
        direction = Direction.Idle;
        this.location = location;
        this.shaftLocation = shaftLocation;
        destination = location;
        shaftDestination = shaftLocation;
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

    public String toString() {
        return "Elevator: " + number;
    }

    public boolean isBusy() {
        return direction != Direction.Idle || assignedCall != null;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }
    boolean asd = false;
    public void move(Building building, int time){
        if(destination.getLevel() < location.getLevel()){
            direction = Direction.Down;
            if (location.getLevel() == building.getTerminalFloor().getLevel() )
                roundTripTime = time;
        } else if(destination.getLevel() > location.getLevel()){
            direction = Direction.Up;
            if (location.getLevel() == building.getTerminalFloor().getLevel() )
                roundTripTime = time;
        } else if(shaftDestination!= null && shaftLocation != null && shaftDestination.getIndex() < shaftLocation.getIndex()){
            direction = Direction.Left;
        } else if(shaftDestination!= null && shaftLocation != null && shaftDestination.getIndex() > shaftLocation.getIndex()){
            direction = Direction.Right;
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
                sideProgress--;
                break;
            case Right:
                sideProgress++;
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
            Log.log("Elevator " + number + " now at level = "+ getLocation().getLevel() + " shaft = " + shaftLocation.getIndex());
            progress = 0;
        } else if (progress <= -Parameters.travelTicks) {
            location = building.getUnderFloor(location);
            Log.log("Elevator " + number + " now at level = "+ getLocation().getLevel() + " shaft = " + shaftLocation.getIndex());
            progress = 0;
        } else if (sideProgress >= Parameters.travelTicks) {
            shaftLocation = shaftLocation.move(this, building.getRightShaft(shaftLocation));
            Log.log("Elevator " + number + " now at level = "+ getLocation().getLevel() + " shaft = " + shaftLocation.getIndex());
            sideProgress = 0;
        } else if (sideProgress <= -Parameters.travelTicks) {
            shaftLocation = shaftLocation.move(this, building.getLeftShaft(shaftLocation));
            Log.log("Elevator " + number + " now at level = "+ getLocation().getLevel() + " shaft = " + shaftLocation.getIndex());
            sideProgress = 0;
        }

    }

    public int getNumber() {
        return number;  
    }

    public void stop() {
        direction = Car.Direction.Idle;
        assignedCall = null;
        destination = location;
        shaftDestination = shaftLocation;
        passengers.clear();
        waitingCalls.clear();
    }

    public int getProgress() {
        return progress;
    }

    public Floor getLocation() {
        return location;
    }

    public Shaft getShaftLocation() {
        return shaftLocation;
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
        setDestination(destination, shaftLocation);
    }

    public void setDestination(Floor destination, Shaft shaftDestination) {
        this.destination = destination;
        this.shaftDestination = shaftDestination;
        if(destination.getLevel() < location.getLevel())
            direction = Direction.Down;
        else if(destination.getLevel() > location.getLevel())
            direction = Direction.Up;
        else if(shaftDestination.getIndex() < shaftLocation.getIndex())
            direction = Direction.Left;
        else if(shaftDestination.getIndex() > shaftLocation.getIndex())
            direction = Direction.Right;
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
                    int oldDistance;
                    int newDistance;
                    if(shaftLocation != null && shaftDestination != null) {
                        oldDistance = assignedCall.isPickedUp() ? Math.abs(location.getLevel() - assignedCall.getTo().getLevel()) + Math.abs(shaftLocation.getIndex() - assignedCall.getToShaft().getIndex()) : Math.abs(location.getLevel() - assignedCall.getFrom().getLevel()) + Math.abs(shaftLocation.getIndex() - assignedCall.getFromShaft().getIndex());
                        newDistance = call.isPickedUp() ? Math.abs(location.getLevel() - call.getTo().getLevel()) + Math.abs(shaftLocation.getIndex() - call.getToShaft().getIndex()) : Math.abs(location.getLevel() - call.getFrom().getLevel()) + Math.abs(shaftLocation.getIndex() - call.getFromShaft().getIndex());
                    } else {
                        oldDistance = assignedCall.isPickedUp() ? Math.abs(location.getLevel() - assignedCall.getTo().getLevel()) : Math.abs(location.getLevel() - assignedCall.getFrom().getLevel());
                        newDistance = call.isPickedUp() ? Math.abs(location.getLevel() - call.getTo().getLevel()) : Math.abs(location.getLevel() - call.getFrom().getLevel());
                    }
                    if (newDistance < oldDistance) {
                        assignedCall = call;
                    }
                }
            }
            waitingCalls.remove(assignedCall);
            destination = assignedCall.isPickedUp() ? assignedCall.getTo() : assignedCall.getFrom();
            shaftDestination = assignedCall.isPickedUp() ? assignedCall.getToShaft() : assignedCall.getFromShaft();
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