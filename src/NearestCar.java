import java.util.*;

public class NearestCar extends GroupControl {

	int N;
	
	public NearestCar(Building building) {
        super(building);
        N = building.getNumberOfFloors();
    }

    @Override
    public Car assignElevator(List<Shaft> shafts) {
        //Returns first elevator it finds
        return building.getCars().get(0);
    }

    /**
	* Calculates the most appropriate elevator to assign to a call.
    */
    public Car getBestElevator(Call call) {
    	int FS = 1, d;
    	List<Car> cars = building.getCars();
    	Car selectedCar = cars.get(0);
    	for (int i=0; i<cars.size(); i++) {
    		d = Math.abs(cars.get(i).getLocation().getLevel() - call.getTo().getLevel());
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
    		}
    	}
        return selectedCar;
    }

    @Override
    public void controlElevators(int time) {
        //TODO: Control ze elevatorz

    	Call call;
        Iterator<Call> it = newCalls.iterator();

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
            //System.out.println("from: " + call.getFrom().getLevel() + " to: " + call.getTo().getLevel() + " pickedUp: " + call.isPickedUp());

            if (!car.isMoving()) {
                if (car.getLocation().getLevel() == call.getTo().getLevel() && call.isPickedUp()) {
                    // dropa snuber
                    System.out.println("Removed Passenger from: " + call.getFrom().getLevel() + " to: " + call.getTo().getLevel() + " from elevator: " + car.getNumber());
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
		System.out.println("Added passenger: from = " + p.getStart().getLevel() + ", to = " + p.getDestination().getLevel() + ", time: " + time + " in elevator: " + car.getNumber());
    }

    private Car.Direction getDirection(Floor from, Floor to) {
        Car.Direction direction = Car.Direction.Idle;
        if(from.getLevel() < to.getLevel())
            direction = Car.Direction.Up;
        else if(from.getLevel() > to.getLevel())
            direction = Car.Direction.Down;
        else if(from.getLevel() == to.getLevel())
            direction = Car.Direction.Idle;

        return direction;
    }

    private void dropPrivetOutOfCar(Car car, Call call) {
        //TODO: Drop privet
        car.removePassenger(call.getCaller());
        System.out.println(call.waitingTime());
        ElevatorEngine.R.totalWaitingTime += call.waitingTime();
    }

}