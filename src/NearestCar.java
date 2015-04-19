import java.util.*;

public class NearestCar extends GroupControl {

	int N;
    boolean optimized;

	public NearestCar(Building building, boolean optimized) {
        super(building);
        N = building.getNumberOfFloors();
        this.optimized = optimized;
    }

    /**
	* Calculates the most appropriate elevator to assign to a call.
    */
    @Override
    public Car getBestElevator(Call call) {
    	int FS = 1, d;
    	List<Car> cars = building.getCars();
    	Car selectedCar = cars.get(0);
    	for (int i=0; i<cars.size(); i++) {
    		d = Math.abs(cars.get(i).getLocation().getLevel() - call.getFrom().getLevel());
    		Car.Direction direction = cars.get(i).getDirection();
    		Car.Direction elevatorCallDirection = getDirection(cars.get(i).getLocation(), call.getFrom());
    		Car.Direction callDirection = getDirection(call.getFrom(), call.getTo());
    		int newFS = 1;
    		switch(direction) {
    			case Idle:
    				newFS = N + 1 - d;
    			case Down:
    				if (elevatorCallDirection == Car.Direction.Up) {
    					newFS = 1;
    				} else if (elevatorCallDirection == Car.Direction.Down && callDirection == Car.Direction.Up) {
    					newFS = N + 1 - d;
    				} else if (elevatorCallDirection == Car.Direction.Down && callDirection == Car.Direction.Down) {
    					newFS = N + 1 - (d - 1);
    				}
    			case Up:
    				if (elevatorCallDirection == Car.Direction.Down) {
    					newFS = 1;
    				} else if (elevatorCallDirection == Car.Direction.Up && callDirection == Car.Direction.Down) {
    					newFS = N + 1 - d;
    				} else if (elevatorCallDirection == Car.Direction.Up && callDirection == Car.Direction.Up) {
    					newFS = N + 1 - (d - 1);
    				}
    		}
    		if (newFS > FS) {
    			selectedCar = cars.get(i);
    			FS = newFS;
    		} if (newFS == FS) {
    			int r = new Random().nextInt(2);
    			if(r == 1) {
    				selectedCar = cars.get(i);
    			}
    		}
    	}
        return selectedCar;
    }

    int tTicker = 0;

    @Override
    public void controlElevators(int time, TrafficPattern.CallPattern currentPattern) {
        //TODO: Control ze elevatorz

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
            if(optimized){
                if(!car.isBusy()){
                    switch(currentPattern){
                        case UpPeak:
                            car.setDestination(building.getTerminalFloor());
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
		Log.log("Added passenger: from = " + p.getStart().getLevel() + ", to = " + p.getDestination().getLevel() + ", time: " + time + " in elevator: " + car.getNumber());
    }

    private void dropPrivetOutOfCar(Car car, Call call) {
        //TODO: Drop privet
        car.removePassenger(call.getCaller());
        Log.log("" + call.waitingTime());
        ElevatorEngine.R.totalWaitingTime += call.waitingTime();
    }

}