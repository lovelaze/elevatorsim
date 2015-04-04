import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bengan on 3/17/2015.
 */
public abstract class GroupControl {

	protected Building building;
    protected ArrayList<Call> newCalls = new ArrayList<Call>();
    protected ArrayList<Call> assignedCalls = new ArrayList<Call>();

    public GroupControl() {
        building = new Building();
    }

	public GroupControl(Building building) {
		this.building = building;
	}

	public abstract Car assignElevator(List<Shaft> shafts);

    public abstract void controlElevators(int time);

    public ArrayList<Call> getAssignedCalls() {
        return assignedCalls;
    }

    public ArrayList<Call> getNewCalls() {
        return newCalls;
    }

    public void addCalls (ArrayList<Call> calls) {
        this.newCalls.addAll(calls);
        ElevatorEngine.R.totalCalls += calls.size();
    }

    public int remainingCalls() {
        return newCalls.size() + assignedCalls.size();
    }

	public void startControlling(int time) {
	    controlElevators(time);
    }

}
