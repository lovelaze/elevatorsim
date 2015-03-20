import java.util.ArrayList;

/**
 * Created by Bengan on 3/19/2015.
 */
public class Step {

    private ArrayList<Passenger> passengers;

    public Step() {
        passengers = new ArrayList<Passenger>();
    }

    public void addPassenger(Passenger passenger) {
    	passengers.add(passenger);
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }


}
