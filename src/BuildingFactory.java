/**
 * Created by Fredrik on 3/19/2015.
 */
public final class BuildingFactory {

    public static Building defaultBuilding() {
        Building building = new Building();

        for(int i=0; i<30; i++) {
        	Floor floor;
        	if(i == 0) {
        		floor = new Floor(0, true);
        	} else {
        		floor = new Floor(i);
        	}
        	building.addFloor(floor);
        }

        for(int i=0; i<7; i++) {
        	Shaft shaft = new Shaft(i+1);
        	shaft.addCar(new Car(building.getTerminalFloor(), i+1, shaft));
        	building.addShaft(shaft);
        }

        return building;
    }


}
