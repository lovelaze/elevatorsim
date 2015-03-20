import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * Created by Bengan on 3/19/2015.
 */

public class ElevatorEngine implements ActionListener {

    private Building building;
    private GroupControl groupController;
    public static SimulationResult R;
    private Timer timer;
    private boolean isFinished;
    private TrafficPattern pattern;

    private ArrayList<Call> currentCalls;

    public ElevatorEngine(Building building) {
        this.building = building;
        groupController = new DefaultGroupController();
        timer = new Timer(500, this);
        R = new SimulationResult();
        isFinished = false;
        pattern = new TrafficPattern( TrafficPattern.CallPattern.UpPeak, building, 1000);
    }

    public void start() {
    	timer.start();
    }
    public void stop() {
        timer.stop();
        isFinished = true;
    }

    public SimulationResult getSimulationResult() {
    	if (isFinished)
        	return R;
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        currentCalls = pattern.nextStep();
        groupController.sendCalls(currentCalls);

    	System.out.println("STEP");
    }
}
