import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bengan on 3/17/2015.
 */
public abstract class GroupControl {

	protected Building building;
    protected ArrayList<Call> newCalls = new ArrayList<Call>();
    protected ArrayList<Call> assignedCalls = new ArrayList<Call>();


    protected Thread t;
    private boolean running = true;

    public GroupControl() {
        building = new Building();
    }

	public GroupControl(Building building) {
		this.building = building;
	}

	public abstract Car assignElevator(List<Shaft> shafts);

    public abstract void controlElevators();

    public synchronized void addCalls (ArrayList<Call> calls) {
        this.newCalls.addAll(calls);
        ElevatorEngine.R.totalCalls += calls.size();
    }

    public synchronized int remainingCalls() {
        return newCalls.size() + assignedCalls.size();
    }

    public void stopControlling() {
    	running = false;
    }

	public void startControlling() {
        t = new Thread() {
            public void run() {
                while (running) {
                    controlElevators();
                }
            }
        };
        t.start();
    }

}
