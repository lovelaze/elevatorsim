import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Bengan on 3/17/2015.
 */

public class Shaft {

    private ArrayList<Car> cars;

    public Shaft() {
        cars = new ArrayList<Car>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    public int carsInShaft() {
        return cars.size();
    }

}
