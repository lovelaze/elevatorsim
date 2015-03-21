import java.util.Iterator;
import java.util.List;

/**
 * Created by Bengan on 3/19/2015.
 */
public class DefaultGroupController extends GroupControl {

    public DefaultGroupController(Building building) {
        super(building);
    }

    @Override
    public Car assignElevator(List<Shaft> shafts) {
        //Returns first elevator it finds
        return building.getCars().get(0);
    }

    public Car getBestElevator() {
        return building.getCars().get(0);
    }

    @Override
    public void controlElevators() {
        Call call;
        Iterator<Call> it = newCalls.iterator();

        for(Car car : building.getCars())  {
            car.move(building);
        }

        while ( it.hasNext()) {
            call = it.next();
            if (!getBestElevator().isBusy()) {
                getBestElevator().assignTo(call);
                assignedCalls.add(call);
                it.remove();
            }
        }

        Car car = null;
        it = assignedCalls.iterator();
        while (it.hasNext()) {
            call = it.next();
            car = call.getAssignee();

            if (!car.isMoving()) {
                if (car.getLocation().getLevel() == call.getTo().getLevel()) {
                    // dropa snuber
                    System.out.println("Removed Passenger");
                    dropPrivetOutOfCar(car, call);

                    it.remove();

                    car.stop();
                } if (car.getLocation().getLevel() == call.getFrom().getLevel()) {

                    // pick up
                    putPeopleInCar(car, call);
                }
            }
        }

    }

    private void putPeopleInCar(Car car, Call call) {

        /*Iterator<Passenger> it = car.getLocation().getPassengers().iterator();
        Passenger p;
        while (it.hasNext()) {
            p = it.next();
            if (getDirection(car.getAssignedCall().getFrom(), car.getAssignedCall().getTo()) == p.getDirection()) {
                car.addPassenger(p);
                it.remove();
                car.setDestination(car.getAssignedCall().getTo());
                System.out.println("Added passenger: from = " + p.getStart().getLevel() + ", to = " + p.getDestination().getLevel());
            }
        }*/

        Passenger p = call.getCaller();
        if (getDirection(car.getAssignedCall().getFrom(), car.getAssignedCall().getTo()) == p.getDirection()) {
            car.addPassenger(p);
            car.getLocation().getPassengers().remove(p);
            car.setDestination(car.getAssignedCall().getTo());
            System.out.println("Added passenger: from = " + p.getStart().getLevel() + ", to = " + p.getDestination().getLevel());
        }
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
        car.removePassenger(call.getCaller());
    }

}
