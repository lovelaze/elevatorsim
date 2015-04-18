import java.util.*;

public class MultiDirController extends GroupControl {

	public MultiDirController(Building building) {
		super(building);
	}

	public Car getBestElevator(Call call) {
		return building.getCars().get(0);
	}

	int tTicker = 0;

    public void controlElevators(int time, TrafficPattern.CallPattern currentPattern) {
    	Call call;
        Iterator<Call> it = newCalls.iterator();

        int pickedUpCalls = 0;
        for(Call c : assignedCalls) {
            if(c.isPickedUp())
                pickedUpCalls++;
        }
        tTicker++;
        if(tTicker > 10000){
            ElevatorEngine.R.peopleFlow += ElevatorEngine.R.servedCalls;
            ElevatorEngine.R.servedCalls = 0;
            ElevatorEngine.R.measures++;
            tTicker = 0;
        }


        for(Car car : building.getCars()) {

        	car.move(building, time);
        }

        while (it.hasNext()) {
        	call = it.next();
        	Car bestCar = getBestElevator(call);
        	bestCar.assignTo(call);
        	assignedCalls.add(call);
        	it.remove();
        }

        Car car = null;
        it = assignedCalls.iterator();
        while (it.hasNext()) {
            call = it.next();
            car = call.getAssignee();
            //Log.log("from: " + call.getFrom().getLevel() + " to: " + call.getTo().getLevel() + " pickedUp: " + call.isPickedUp());

            if (!car.isMoving()) {
                if (car.getLocation().getLevel() == call.getTo().getLevel() && call.isPickedUp()) {
                    // dropa snuber
                    Log.log("Removed Passenger from: " + call.getFrom().getLevel() + " to: " + call.getTo().getLevel() + " from elevator: " + car.getNumber());
                    dropPrivetOutOfCar(car, call);

                    it.remove();
                } if (car.getLocation().getLevel() == call.getFrom().getLevel() && !call.isPickedUp()) {
                    // pick up
                    putPeopleInCar(car, call, time);
                }
            }
        }
    }

    private void putPeopleInCar(Car car, Call call, int time) {
		//TODO: Put people in car
		Passenger p = call.getCaller();
    	car.addPassenger(p, time);
    	car.getLocation().getPassengers().remove(p);
		Log.log("Added passenger: from = " + p.getStart().getLevel() + ", to = " + p.getDestination().getLevel() + ", from shaft = " + p.getStartShaft().getIndex() + ", to shaft = " + p.getDestinationShaft().getIndex() + ", time: " + time + " in elevator: " + car.getNumber());
    }

    private void dropPrivetOutOfCar(Car car, Call call) {
        //TODO: Drop privet
        car.removePassenger(call.getCaller());
        Log.log("" + call.waitingTime());
        ElevatorEngine.R.totalWaitingTime += call.waitingTime();
    }

}