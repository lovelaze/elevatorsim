/**
 * Created by Bengan on 3/19/2015.
 */
public class SimulationResult {

    public int totalCalls;
    public int totalPassengers;
    public long totalTravelTime;
    public long totalWaitingTime;
    public long averageRoundTripTime;

    public void printResult() {
        System.out.println("totalCalls = " + totalCalls);
        System.out.println("totalPassengers = " + totalPassengers);
        System.out.println("totalTravelTime = " + totalTravelTime);
        System.out.println("totalWaitingTime = " + totalWaitingTime);
        System.out.println("averageRoundTripTime = " + averageRoundTripTime);

    }

}
