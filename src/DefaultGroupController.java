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

            if (car.getLocation().getLevel() == call.getTo().getLevel()) {
                // dropa snuber
                System.out.println("epnis 3");
                dropPrivetOutOfCar(car, call);

                it.remove();

                car.stop();
            } else if (car.getLocation().getLevel() == call.getFrom().getLevel()) {
                System.out.println("epnis 2");
                // pick up
                putPeopleInCar(car);
            }
        }

    }

    private void putPeopleInCar(Car car) {

        Iterator<Passenger> it = car.getLocation().getPassengers().iterator();
        Passenger p;
        while (it.hasNext()) {
            p = it.next();
            if (car.getDirection() == p.getDirection()) {
                car.addPassenger(p);
                it.remove();
            }
        }

        car.setDestination(car.getAssignedCall().getTo());
    }

    private void dropPrivetOutOfCar(Car car, Call call) {
        car.removePassenger(call.getCaller());
    }

}
