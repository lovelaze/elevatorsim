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

        public boolean returningToSector() {
            return assignedCar.getDestination().getLevel() == startFloor.getLevel();
        }

		public boolean isCallInSector(Call call) {
			int start = startFloor.getLevel(), end = endFloor.getLevel(), callFloor = call.getFrom().getLevel();
			return callFloor >= start && callFloor <= end;
		}

        public boolean isVacant() {
            return assignedCar == null;
        }

        public void deAssign() {
            assignedCar = null;
        }

        public boolean assign(Car car) {
            if(assignedCar == null) {
                assignedCar = car;
                assignedCar.setDestination(startFloor);
            }
            return assignedCar == null;
        }

        public boolean isCarInSector(Car car) {
            return car.getLocation().getLevel() >= startFloor.getLevel() && car.getLocation().getLevel() <= endFloor.getLevel();
        }

		public String toString() {
            if(assignedCar != null)
			     return "Sector start floor: " + startFloor.getLevel() + " end floor: " + endFloor.getLevel() + " elevator: " + assignedCar.getNumber();
            else
                 return "Sector start floor: " + startFloor.getLevel() + " end floor: " + endFloor.getLevel() + " elevator: " + "no assigned elevator.";
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
    	Car selectedCar = null;
        for(Car c : building.getCars()){
            if(c.isMoving() && c.getDirection() == Car.Direction.Up) {

            }
        }
    	for(Sector sector : sectors) {
    		if(sector.isCallInSector(call)){
    			selectedCar = sector.assignedCar;
    			break;
    		}
    	}
        if(selectedCar == null) {
            boolean lowestSector = false;
            for(int i=0; i<sectors.size(); i++) {
                Sector sector = sectors.get(i);
                if(!sector.isVacant() && i != sectors.size()-1) {
                    Sector aboveSector = sectors.get(i+1);
                    if(aboveSector.isVacant() && aboveSector.isCallInSector(call)) {
                        selectedCar = sector.assignedCar;
                    }
                }
                if(!lowestSector && !sector.isVacant()) {
                    if(i != 0){
                        Sector belowSector = sectors.get(i-1);
                        if(belowSector.isVacant() && belowSector.isCallInSector(call)){
                            selectedCar = sector.assignedCar;
                        }
                    }
                    lowestSector = true;
                }
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
            if(bestCar != null) {
                bestCar.assignTo(call);
                assignedCalls.add(call);
                it.remove();
            }
        }

        for(Sector s : sectors) {
            if(s.assignedCar != null && !s.returningToSector() && !s.isCarInSector(s.assignedCar)){
                Log.log("Deassigned " + s.toString());
                s.deAssign();
            }
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

                    sendToSector(car);

                    it.remove();
                } if (car.getLocation().getLevel() == call.getFrom().getLevel() && !call.isPickedUp()) {
                    // pick up
                    putPeopleInCar(car, call, time);
                }
            }
        }
    }

    private void sendToSector(Car car) {
        Sector currentsector = null;
        if(!car.isBusy()) {
            for (Sector s : sectors) {
                if (s.isCarInSector(car)) {
                    currentsector = s;
                    break;
                }
            }

            if (currentsector.isVacant()) {
                currentsector.assign(car);
                Log.log("Assigned " + currentsector.toString());
            }

            int index = sectors.indexOf(currentsector);
            int i = 1;
            while (!car.isBusy()) {
                if(index - i < 0 && index + i > sectors.size()+1)
                    break;
                int tempindex = index - i;
                Sector tempsector = null;
                if (tempindex >= 0) {
                    tempsector = sectors.get(tempindex);
                    if (tempsector.isVacant()) {
                        tempsector.assign(car);
                        Log.log("Assigned " + tempsector.toString());
                        break;
                    }
                }

                tempindex = index +i;
                if (tempindex <= sectors.size()-1) {
                    tempsector = sectors.get(tempindex);
                    if (tempsector.isVacant()) {
                        tempsector.assign(car);
                        Log.log("Assigned " + tempsector.toString());
                        break;
                    }
                }
                i++;
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
