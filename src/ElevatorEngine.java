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

    private int stepLength = 100, stepCounter = 0, time  = 0;
    private int totalPassengers = 10000;

    public ElevatorEngine(Building building) {
        this(building, new DefaultGroupController(building));
    } 

    public ElevatorEngine(Building building, GroupControl controller) {
        this.building = building;
        groupController = controller;
        R = new SimulationResult();
        pattern = new TrafficPattern( TrafficPattern.CallPattern.UpPeak, building, totalPassengers);
    }

    public boolean isRunning() {
        stepping();
        groupController.startControlling(time);
        return pattern.remainingSteps() <= 0 && groupController.remainingCalls() <= 0;
    }

    public void stepping() {
        if(stepCounter>=stepLength){
            currentCalls = pattern.nextStep(time);
            groupController.addCalls(currentCalls);
            currentCalls.clear();

        	System.out.println("Remaining steps: " + pattern.remainingSteps() + ", New calls: "  + groupController.getNewCalls().size()+ ", Assigned Calls: " + groupController.getAssignedCalls().size());

            stepCounter = 0;
        }

        stepCounter++;
        time++;
    }
}
