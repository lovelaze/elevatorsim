import java.util.ArrayList;

/**
 * Created by Bengan on 3/17/2015.
 */
public class Building {

	private Floor terminalFloor;
    private ArrayList<Floor> floors;
    private ArrayList<Floor> specialFloors;
    private ArrayList<Shaft> shafts;

    public Building() {
		floors = new ArrayList<Floor>();
		specialFloors = new ArrayList<Floor>();
		shafts = new ArrayList<Shaft>();
    }

    public Floor getAboveFloor(Floor floor) {
        int index = floors.indexOf(floor);
        if (index == floors.size()-1 ) {
            return null;
        }
        return floors.get(index+1);
    }

    public Floor getUnderFloor(Floor floor) {
        int index = floors.indexOf(floor);
        if (index == 0) {
            return null;
        }
        return floors.get(index-1);
    }

    public Shaft getRightShaft(Shaft shaft) {
        int index = shafts.indexOf(shaft);
        if (index == shafts.size()-1 ) {
            return null;
        }
        return shafts.get(index+1);
    }

    public Shaft getLeftShaft(Shaft shaft) {
        int index = shafts.indexOf(shaft);
        if (index == 0) {
            return null;
        }
        return shafts.get(index-1);
    }

    public int getNumberOfFloors() {
        return floors.size();
    }

    public int getNumberOfShafts() {
        return shafts.size();
    }

    public ArrayList<Shaft> getShafts() {
        return shafts;
    }

    public ArrayList<Car> getCars() {
        ArrayList<Car> cars = new ArrayList<Car>();
        for(Shaft shaft : shafts) {
            cars.addAll(shaft.getCars());
        }
        return cars;
    }

    public ArrayList<Floor> getFloors() {
        return floors;
    }

    public void addFloor(Floor floor) {
    	floors.add(floor);
    	if(floor.isTerminalFloor())
    		terminalFloor = floor;
    	else if(floor.isSpecialFloor())
    		specialFloors.add(floor);
    }

    public void addShaft(Shaft shaft) {
    	shafts.add(shaft);
    }

    public ArrayList<Floor> getSpecialFloors() {
    	return specialFloors;
    }

    public void setTerminalFloor(Floor terminalFloor) {
    	this.terminalFloor = terminalFloor;
    }

    public Floor getTerminalFloor() {
    	return terminalFloor;
    }

    public Shaft getShaft(int index) {
        return shafts.get(index);
    }

    public Floor getFloor(int index) {
        Floor returnFloor = null;
        for(Floor f : floors) {
            if(f.getLevel() == index)
                returnFloor = f;
        }
        return returnFloor;
    }
}
