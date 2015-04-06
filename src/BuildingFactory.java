/**
 * Created by Fredrik on 3/19/2015.
 */
public final class BuildingFactory {

    public static Building defaultBuilding() {
        Building building = new Building();

        for(int i=0; i<10; i++) {
        	Floor floor;
        	if(i == 0) {
        		floor = new Floor(0, true);
        	} else {
        		floor = new Floor(i);
        	}
        	building.addFloor(floor);
        }

        for(int i=0; i<2; i++) {
        	Shaft shaft = new Shaft();
        	shaft.addCar(new Car(building.getTerminalFloor(), i+1));
        	building.addShaft(shaft);
        }

        return building;
    }


}
