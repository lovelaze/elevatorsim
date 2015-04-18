import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Bengan on 3/17/2015.
 */

public class Shaft {

    private ArrayList<Car> cars;
    private int index;

    public Shaft(int index) {
        cars = new ArrayList<Car>();
        this.index = index;
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

    public String toString() {
        return "Shaft: " + index + ", contains cars: " + cars.toString();
    }

    public int getIndex() {
        return index;
    }

    public Shaft move(Car car, Shaft shaft) {
        cars.remove(car);
        shaft.addCar(car);
        return shaft;
    }

}
