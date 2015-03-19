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

    public int getNumberOfFloors() {
        return floors.size();
    }

    public ArrayList<Shaft> getShafts() {
        return shafts;
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
}
