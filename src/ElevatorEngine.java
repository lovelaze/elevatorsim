import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created by Fredrik on 3/19/2015.
 */

public class ElevatorEngine implements ActionListener {

    private Building building;
    private GroupControl groupController;
    public static SimulationResult R;
    private Timer timer;
    private boolean isFinished;
    private TrafficPattern pattern;

    public ElevatorEngine(Building building) {
        this.building = building;
        groupController = new DefaultGroupController();
        timer = new Timer(1000, this);
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
    	pattern.nextStep();
    	System.out.println("STEP");
    }
}
