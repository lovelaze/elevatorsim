import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * Created by Bengan on 3/19/2015.
 */

public class ElevatorEngine {

    private Building building;
    private GroupControl groupController;
    public static SimulationResult R;
    private TrafficPattern pattern;

    private ArrayList<Call> currentCalls;

    private int stepLength = 100, stepCounter = 0;

    public ElevatorEngine(Building building) {
        this.building = building;
        groupController = new DefaultGroupController(building);
        R = new SimulationResult();
        pattern = new TrafficPattern( TrafficPattern.CallPattern.UpPeak, building, 1000);
    }

    public boolean isRunning() {
        stepping();
        groupController.startControlling();
        return pattern.remainingSteps() <= 0 && groupController.remainingCalls() <= 0;
    }

    public void stepping() {
        if(stepCounter>=stepLength){
            currentCalls = pattern.nextStep();
            groupController.addCalls(currentCalls);
            currentCalls.clear();

        	System.out.println(pattern.remainingSteps() + ", "  + groupController.getNewCalls().size()+ ", " + groupController.getAssignedCalls().size());

            stepCounter = 0;
        }

        stepCounter++;
    }
}
