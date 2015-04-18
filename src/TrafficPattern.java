import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Bengan on 3/17/2015.
 */
public class TrafficPattern {

    public enum CallPattern{
        UpPeak, DownPeak, SpecialFloor, Ordinary
    }

    private CallPattern currentPattern;
    private int totalPassengers;
    private Building building;
    private Random random = new Random();

    private ArrayList<Step> steps;

    public TrafficPattern(CallPattern currentPattern, Building building, int totalPassengers, boolean traditional) {
        this.currentPattern = currentPattern;
        this.totalPassengers = totalPassengers;
        this.building = building;
        this.steps = new ArrayList<Step>();
        ElevatorEngine.R.totalPassengers = totalPassengers;


        switch (currentPattern) {
            case UpPeak:
                upPeak();
                break;
            case DownPeak:
                downPeak();
                break;
            case SpecialFloor:
                specialFloor();
                break;
            case Ordinary:
                ordinary(traditional);
                break;

        }
    }

    public CallPattern getCurrentPattern() {
        return currentPattern;
    }

    public ArrayList<Call> nextStep(int time) {
        ArrayList<Call> calls = new ArrayList<Call>();
        if (steps.size() > 0) {
            Step step = steps.get(0);
            for (Passenger p : step.getPassengers()) {
                calls.add(p.makeCall(time));
                p.getStart().addPassenger(p);
            }
            steps.remove(0);
        }

        return calls;
    }

    public int remainingSteps() {
        return steps.size();
    }

    private void upPeak() {
        Log.log("UPPEAK");
        while (totalPassengers > 0) {
            int spawnPeople = random.nextInt(100)+1;
            Step step = new Step();
            if(spawnPeople > 70) {
                int numberOfPeople = random.nextInt(5)+1;
                while (totalPassengers - numberOfPeople < 0) {numberOfPeople = random.nextInt(5)+1;}
                for(int i=0; i<numberOfPeople; i++) {
                    Floor start, stop;
                    int terminalFloor = random.nextInt(100)+1;
                    int startFloor = building.getTerminalFloor().getLevel();
                    if(terminalFloor > 20)
                        start = building.getTerminalFloor();
                    else{
                        startFloor = random.nextInt(building.getFloors().size());
                        start = building.getFloor(startFloor);
                    }
                    int stopFloor = random.nextInt(building.getFloors().size());
                    while (stopFloor == startFloor) {stopFloor = random.nextInt(building.getFloors().size());}
                    stop = building.getFloor(stopFloor);
                    step.addPassenger(new Passenger(start, stop));
                }
                totalPassengers -= numberOfPeople;
            }
            steps.add(step);
        }
        Log.log("" + steps.size());
    }

    private void downPeak() {
        Log.log("DOWNPEAK");
        while (totalPassengers > 0) {
            int spawnPeople = random.nextInt(100)+1;
            Step step = new Step();
            if(spawnPeople > 70) {
                int numberOfPeople = random.nextInt(5)+1;
                while (totalPassengers - numberOfPeople < 0) {numberOfPeople = random.nextInt(5)+1;}
                for(int i=0; i<numberOfPeople; i++) {
                    Floor start, stop;
                    int terminalFloor = random.nextInt(100)+1;
                    int startFloor = random.nextInt(building.getFloors().size());
                    
                    int stopFloor = building.getTerminalFloor().getLevel();
                    if(terminalFloor > 20)
                        stop = building.getTerminalFloor();
                    else{
                        stopFloor = random.nextInt(building.getFloors().size());
                        stop = building.getFloor(startFloor);
                    }
                    while (stopFloor == startFloor) {startFloor = random.nextInt(building.getFloors().size());}
                    start = building.getFloor(startFloor);
                    step.addPassenger(new Passenger(start, stop));
                }
                totalPassengers -= numberOfPeople;
            }
            steps.add(step);
        }
        Log.log("" + steps.size());
    }

    private void specialFloor() {
        Log.log("SPECIALFLOOR");
    }

    private void ordinary(boolean traditional) {
        Log.log("ORDINARY");
        while (totalPassengers > 0) {
            int spawnPeople = random.nextInt(100)+1;
            Step step = new Step();
            if(spawnPeople > 70) {
                int numberOfPeople = random.nextInt(5)+1;
                while (totalPassengers - numberOfPeople < 0) {numberOfPeople = random.nextInt(5)+1;}
                for(int i=0; i<numberOfPeople; i++) {
                    Floor start, stop;
                    int startFloor = random.nextInt(building.getFloors().size());
                    
                    int stopFloor = random.nextInt(building.getFloors().size());
                    stop = building.getFloor(stopFloor);
                    if(!traditional)
                        while (stopFloor == startFloor) {startFloor = random.nextInt(building.getFloors().size());}
                    start = building.getFloor(startFloor);
                    if(!traditional){
                        int startShaft = random.nextInt(building.getNumberOfShafts());
                        int stopShaft = random.nextInt(building.getNumberOfShafts());
                        if(startFloor == stopFloor)
                            while (startShaft == stopShaft ) {stopShaft = random.nextInt(building.getNumberOfShafts()); }
                        step.addPassenger(new Passenger(start, stop, building.getShaft(startShaft), building.getShaft(stopShaft)));
                    } else {
                        step.addPassenger(new Passenger(start, stop));
                    }
                }
                totalPassengers -= numberOfPeople;
            }
            steps.add(step);
        }
        Log.log("" + steps.size());
    }

}
