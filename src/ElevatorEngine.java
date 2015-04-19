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

    int step = 0, allSteps = 0;

    private int stepLength = 50, stepCounter = 0, time  = 0;
    private int totalPassengers = 10000;

    public ElevatorEngine(Building building, boolean chart, boolean traditional) {
        this(building, chart, new DefaultGroupController(building), traditional);
    } 

    public ElevatorEngine(Building building, boolean chart, GroupControl controller, boolean traditional) {
        this.building = building;
        groupController = controller;
        R = new SimulationResult(chart);
        pattern = new TrafficPattern( TrafficPattern.CallPattern.UpPeak, building, totalPassengers, traditional);
        allSteps = pattern.remainingSteps();
    }

    public boolean isRunning() {
        stepping();
        groupController.startControlling(time, pattern.getCurrentPattern());
        return pattern.remainingSteps() <= 0 && groupController.remainingCalls() <= 0;
    }

    public void stepping() {
        R.totalTravelTime++;
        if(stepCounter>=stepLength){
            step++;
            currentCalls = pattern.nextStep(time);
            groupController.addCalls(currentCalls);
            currentCalls.clear();

        	Log.log("Remaining steps: " + pattern.remainingSteps() + ", New calls: "  + groupController.getNewCalls().size()+ ", Assigned Calls: " + groupController.getAssignedCalls().size());
            Log.chartLog(step + "/" + allSteps);
            stepCounter = 0;
        }

        stepCounter++;
        time++;
    }
}
