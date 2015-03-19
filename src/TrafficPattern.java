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

    public TrafficPattern(CallPattern currentPattern, Building building, int totalPassengers) {
        this.currentPattern = currentPattern;
        this.totalPassengers = totalPassengers;
        this.building = building;
    }

    public void nextStep() {

        if (steps.size() > 0) {
            Step step = steps.get(0);
            steps.remove(0);
            switch (currentPattern) {
                case UpPeak:
                    upPeak(step);
                    break;
                case DownPeak:
                    downPeak(step);
                    break;
                case SpecialFloor:
                    specialFloor(step);
                    break;
                case Ordinary:
                    ordinary(step);
                    break;

            }
        }


    }

    private void upPeak(Step step) {
    	System.out.println("UPPEAK");
    }

    private void downPeak(Step step) {

    }

    private void specialFloor(Step step) {

    }

    private void ordinary(Step step) {

    }

}
