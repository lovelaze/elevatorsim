import java.util.ArrayList;
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
        for (Iterator<Call> it = newCalls.iterator(); it.hasNext();) {
            call = it.next();
            if (!getBestElevator().isBusy()) {
                getBestElevator().assignTo(call);
                assignedCalls.add(call);
                it.remove();
            }
        }

        Car car;
        for (Iterator<Call> it = assignedCalls.iterator(); it.hasNext();) {
            call = it.next();
            car = call.getAssignee();

            if (car.getLocation().getLevel() == call.getTo().getLevel()) {
                it.remove();
            } else {
                car.move(call.getTo(), building);
            }
        }
    }

}
