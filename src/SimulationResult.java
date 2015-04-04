/**
 * Created by Bengan on 3/19/2015.
 */
public class SimulationResult {

    public int totalCalls;
    public int totalPassengers;
    public int totalTravelTime;
    public int totalWaitingTime;
    public int averageRoundTripTime;

    public void printResult() {
        System.out.println("totalCalls = " + totalCalls);
        System.out.println("totalPassengers = " + totalPassengers);
        System.out.println("totalTravelTime = " + totalTravelTime);
        System.out.println("totalWaitingTime = " + totalWaitingTime);
        System.out.println("averageRoundTripTime = " + averageRoundTripTime);
        System.out.println("averageWaitingTime = " + (totalWaitingTime/totalPassengers));
    }

}
