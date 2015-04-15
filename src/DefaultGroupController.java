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
    public Car getBestElevator(Call call) {
        return building.getCars().get(0);
    }

    @Override
    public void controlElevators(int time, TrafficPattern.CallPattern currentPattern) {
        Call call;
        Iterator<Call> it = newCalls.iterator();

        for(Car car : building.getCars())  {
            car.move(building, time);
        }

        while ( it.hasNext()) {
            call = it.next();
            if (!getBestElevator(null).isBusy()) {
                getBestElevator(null).assignTo(call);
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
                if (car.getLocation().getLevel() == call.getTo().getLevel() && call.isPickedUp()) {
                    // dropa snuber
                    Log.log("Removed Passenger");
                    dropPrivetOutOfCar(car, call);

                    it.remove();

                    car.stop();
                } if (car.getLocation().getLevel() == call.getFrom().getLevel() && !call.isPickedUp()) {

                    // pick up
                    putPeopleInCar(car, call, time);
                }
            }
        }

    }

    private void putPeopleInCar(Car car, Call call, int time) {

        /*Iterator<Passenger> it = car.getLocation().getPassengers().iterator();
        Passenger p;
        while (it.hasNext()) {
            p = it.next();
            if (getDirection(car.getAssignedCall().getFrom(), car.getAssignedCall().getTo()) == p.getDirection()) {
                car.addPassenger(p);
                it.remove();
                car.setDestination(car.getAssignedCall().getTo());
                Log.log("Added passenger: from = " + p.getStart().getLevel() + ", to = " + p.getDestination().getLevel());
            }
        }*/

        Passenger p = call.getCaller();
        if (getDirection(car.getAssignedCall().getFrom(), car.getAssignedCall().getTo()) == p.getDirection()) {
            car.addPassenger(p, time);
            car.getLocation().getPassengers().remove(p);
            car.setDestination(car.getAssignedCall().passengerPickedUp(time));
            Log.log("Added passenger: from = " + p.getStart().getLevel() + ", to = " + p.getDestination().getLevel() + ", time: " + time);
        }
    }

    private void dropPrivetOutOfCar(Car car, Call call) {
        car.removePassenger(call.getCaller());
        Log.log("" + call.waitingTime());
        ElevatorEngine.R.totalWaitingTime += call.waitingTime();
    }

}
