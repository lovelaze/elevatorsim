import java.util.*;

public class NearestCar extends GroupControl {

	int N, FS, d;
	
	public NearestCar(Building building) {
        super(building);
        N = building.getNumberOfFloors();
        FS = 1;
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
    				break;
    			case Down:
    				if (elevatorCallDirection == Car.Direction.Up) {
    					newFS = 1;
    				} else if (elevatorCallDirection == Car.Direction.Down && callDirection == Car.Direction.Up) {
    					newFS = N + 1 - d;
    				} else if (elevatorCallDirection == Car.Direction.Down && callDirection == Car.Direction.Down) {
    					newFS = N + 1 - (d - 1);
    				}
    				break;
    			case Up:
    				if (elevatorCallDirection == Car.Direction.Down) {
    					newFS = 1;
    				} else if (elevatorCallDirection == Car.Direction.Up && callDirection == Car.Direction.Down) {
    					newFS = N + 1 - d;
    				} else if (elevatorCallDirection == Car.Direction.Up && callDirection == Car.Direction.Up) {
    					newFS = N + 1 - (d - 1);
    				}
    				break;
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

    }

    private void putPeopleInCar(Car car, Call call, int time) {
    	//TODO: Put people in car
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
    }

}