import java.util.*;

public class NearestCar extends GroupControl {

	int N, FS, d;
	
	public NearestCar(Building building) {
        super(building);
        N = building.getNumberOfFloors();
    }

    @Override
    public Car assignElevator(List<Shaft> shafts) {
        //Returns first elevator it finds
        return building.getCars().get(0);
    }

    public Car getBestElevator(Call call) {
    	List<Car> cars = building.getCars();
    	Car selectedCar;
    	for (int i=0; i<cars.size(); i++) {
    		d = Math.abs(cars.get(i).getLocation().getLevel() - call.getTo().getLevel());
    		Car.Direction direction = cars.get(i).getDirection();
    		Car.Direction elevatorCallDirection = getDirection(cars.get(i).getLocation(), call.getTo());
    		Car.Direction callDirection = getDirection(call.getFrom(), call.getTo());
    		switch(direction) {
    			case Idle:
    				FS = N + 1 - d;
    				break;
    			case Down:
    				if (elevatorCallDirection == Car.Direction.Up) {
    					FS = 1;
    				} else if (elevatorCallDirection == Car.Direction.Down && callDirection == Car.Direction.Up) {
    					FS = N + 1 - d;
    				} else if (elevatorCallDirection == Car.Direction.Down && callDirection == Car.Direction.Down) {
    					FS = N + 1 - (d - 1);
    				}
    				break;
    			case Up:
    				if (elevatorCallDirection == Car.Direction.Down) {
    					FS = 1;
    				} else if (elevatorCallDirection == Car.Direction.Up && callDirection == Car.Direction.Down) {
    					FS = N + 1 - d;
    				} else if (elevatorCallDirection == Car.Direction.Up && callDirection == Car.Direction.Up) {
    					FS = N + 1 - (d - 1);
    				}
    				break;
    		}
    	}
        return building.getCars().get(0);
    }

    @Override
    public void controlElevators(int time) {
        //TODO: Control ze elevatorz

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