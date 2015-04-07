import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Fredrik on 4/7/2015.
 */
public class FSO extends GroupControl {

	private class Sector {
		Floor startFloor, endFloor;
		Car assignedCar;

		public Sector(Floor start, Floor end, Car assCar) {
			startFloor = start;
			endFloor = end;
			assignedCar = assCar;
			assCar.setDestination(startFloor);
		}

		public boolean isCallInSector(Call call) {
			int start = startFloor.getLevel(), end = endFloor.getLevel(), callFloor = call.getFrom().getLevel();
			return callFloor >= start && callFloor <= end;
		}

		public String toString() {
			return "Sector start floor: " + startFloor.getLevel() + " end floor: " + endFloor.getLevel() + " elevator: " + assignedCar.getNumber();
		}
	}

	private ArrayList<Sector> sectors;

    public FSO(Building building) {
        super(building);
        sectors = new ArrayList<Sector>();

        int cars = building.getCars().size();
        int floors = building.getNumberOfFloors();
        int range = (floors-1)/(cars-1);
        for(int i=0; i<cars; i++) {
        	if(i == 0) {
        		sectors.add(new Sector(building.getTerminalFloor(), building.getTerminalFloor(), building.getCars().get(i)));
        	} else if(i == cars-1) {
        		sectors.add(new Sector( building.getFloor((i-1) * range+1*i), building.getFloor(floors-1), building.getCars().get(i)));
        	} else {
        		sectors.add(new Sector( building.getFloor((i-1) * range+1*i), building.getFloor(i*(range+1)), building.getCars().get(i)));
        	}
        }
        for(Sector s : sectors) {
        	Log.log("" + s.toString());
        }

    }

    @Override
    public Car getBestElevator(Call call) {
    	Car selectedCar = building.getCars().get(0);
    	for(Sector sector : sectors) {
    		if(sector.isCallInSector(call)){
    			selectedCar = sector.assignedCar;
    			break;
    		}
    	}
        return selectedCar;
    }

    @Override
    public void controlElevators(int time, TrafficPattern.CallPattern currentPattern) {
    	Call call;
        Iterator<Call> it = newCalls.iterator();

        int waitingCalls = 0;
        for(Call c : assignedCalls) {
            if(!c.isPickedUp())
                waitingCalls++;
        }
        ElevatorEngine.R.addValue(waitingCalls, time);

        for(Car car : building.getCars()) {
        	car.move(building);
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
		Log.log("Added passenger: from = " + p.getStart().getLevel() + ", to = " + p.getDestination().getLevel() + ", time: " + time + " in elevator: " + car.getNumber());
    }

    private void dropPrivetOutOfCar(Car car, Call call) {
        //TODO: Drop privet
        car.removePassenger(call.getCaller());
        Log.log("" + call.waitingTime());
        ElevatorEngine.R.totalWaitingTime += call.waitingTime();
    }
}
