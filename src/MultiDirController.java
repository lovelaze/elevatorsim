import java.util.*;

public class MultiDirController extends GroupControl {

	private int N;
	private int M;
	private boolean optimized;

	public MultiDirController(Building building, boolean optimized) {
		super(building);
		N = building.getNumberOfFloors();
		M = building.getNumberOfShafts();
		this.optimized = optimized;
	}

	public Car getBestElevator(Call call) {

		int FS = 1;
		List<Car> cars = building.getCars();
		Car selectedCar = cars.get(0);
		for(int i=0; i<cars.size(); i++) {
			Car car = cars.get(i);
			int d = Math.abs(car.getLocation().getLevel() - call.getFrom().getLevel());
			int h = Math.abs(car.getShaftLocation().getIndex() - call.getFromShaft().getIndex());
			Car.Direction direction = car.getDirection();
    		Car.Direction elevatorCallDirection = getDirection(car.getLocation(), call.getFrom(), car.getShaftLocation(), call.getFromShaft());
    		Car.Direction callDirection = getDirection(call.getFrom(), call.getTo(), call.getFromShaft(), call.getToShaft());
    		int newFS = 1;

			switch(direction) {
    			case Idle:
    				newFS = N + M + 2 - d - h;
    			case Down:
    				if (elevatorCallDirection == Car.Direction.Up) {
    					newFS = 1;
    				} else if (elevatorCallDirection == Car.Direction.Down && callDirection == Car.Direction.Up) {
    					newFS = N + M + 4 - d - h;
    				} else if (elevatorCallDirection == Car.Direction.Down && callDirection == Car.Direction.Down) {
    					newFS = N + M + 2 - d - h;
    				}
    			case Up:
    				if (elevatorCallDirection == Car.Direction.Down) {
    					newFS = 1;
    				} else if (elevatorCallDirection == Car.Direction.Up && callDirection == Car.Direction.Down) {
    					newFS = N + M + 4 - d - h;
    				} else if (elevatorCallDirection == Car.Direction.Up && callDirection == Car.Direction.Up) {
    					newFS = N + M + 2 - d - h;
    				}
    			case Left:
    				if (elevatorCallDirection == Car.Direction.Left) {
    					newFS = 1;
    				} else if (elevatorCallDirection == Car.Direction.Right && callDirection == Car.Direction.Left) {
    					newFS = N + M + 4 - d - h;
    				} else if (elevatorCallDirection == Car.Direction.Right && callDirection == Car.Direction.Right) {
    					newFS = N + M + 2 - d - h;
    				}
    			case Right:
  					if (elevatorCallDirection == Car.Direction.Right) {
    					newFS = 1;
    				} else if (elevatorCallDirection == Car.Direction.Left && callDirection == Car.Direction.Right) {
    					newFS = N + M + 4 - d - h;
    				} else if (elevatorCallDirection == Car.Direction.Left && callDirection == Car.Direction.Left) {
    					newFS = N + M + 2 - d - h;
    		    }
				
    		}if (newFS > FS) {
    			selectedCar = cars.get(i);
    			FS = newFS;
    		}
		}

		return selectedCar;
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
        	if(optimized) {
        		if(!car.isBusy()) {
        			switch (currentPattern) {
        				case UpPeak:
        					car.setDestination(building.getTerminalFloor());
        				case DownPeak:
        					break;
        			}
        		}
        	}
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