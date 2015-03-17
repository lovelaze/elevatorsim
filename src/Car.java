import java.util.ArrayList;

/**
 * Created by Fredrik on 3/17/2015.
 */
public class Car {

    private int direction;
    private Floor location;
    private ArrayList<Passenger> passengers;

    public Car (Floor location) {
        direction = 0;
        this.location = location;
    }

}
