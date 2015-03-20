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
    private TrafficPattern pattern;

    private ArrayList<Call> currentCalls;

    public ElevatorEngine(Building building) {
        this.building = building;
        groupController = new DefaultGroupController(building);
        timer = new Timer(1, this);
        R = new SimulationResult();
        pattern = new TrafficPattern( TrafficPattern.CallPattern.UpPeak, building, 1000);
    }

    public void start() {
    	timer.start();
        groupController.startControlling();
    }

    public void stop() {
        timer.stop();
        groupController.stopControlling();
    }

    public boolean isRunning() {
        return pattern.remainingSteps() <= 0 && groupController.remainingCalls() <= 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        currentCalls = pattern.nextStep();
        groupController.addCalls(currentCalls);
        currentCalls.clear();

    	System.out.println("STEP");
    }
}
