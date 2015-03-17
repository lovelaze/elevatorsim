import java.util.ArrayList;

/**
 * Created by Fredrik on 3/17/2015.
 */
public class Building {

    private ArrayList<Floor> floors;
    private ArrayList<Shaft> shafts;

    public int getNumberOfFloors() {
        return floors.size();
    }

}
