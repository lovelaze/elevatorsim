import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bengan on 3/19/2015.
 */
public class DefaultGroupController extends GroupControl {



    public DefaultGroupController(Building building) {
        super(building);
    }

    @Override
    public Car assignElevator(List<Shaft> shafts) {
    	//Returns first elevator it finds
    	if(shafts.size() > 0) {
    		if(shafts.get(0).getCars().size() > 0) {
    			return shafts.get(0).getCars().get(0);
    		}
    	}
        return null;
    }

    @Override
    public void controlElevators() {

    }

}
