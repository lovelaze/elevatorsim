import java.util.*;

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

        for(int i=0; i<5; i++) {
        	Shaft shaft = new Shaft(i+1);
        	shaft.addCar(new Car(building.getTerminalFloor(), i+1, shaft));
        	building.addShaft(shaft);
        }

        return building;
    }

    public static Building multiBuilding() {
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

        for(int i=0; i<5; i++) {
            Shaft shaft = new Shaft(i+1);
            building.addShaft(shaft);
        }

        for(int i=0; i<25; i++) {
            Shaft shaft = building.getShaft(i%5);
            shaft.addCar(new Car(building.getTerminalFloor(), i+1, shaft));
        }

        return building;
    }


}
