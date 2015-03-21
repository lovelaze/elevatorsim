public class Main {

    public static void main(String[] args) {

        Building building = BuildingFactory.defaultBuilding();
        ElevatorEngine engine = new ElevatorEngine(building);

        while (!engine.isRunning()) {}
        ElevatorEngine.R.printResult();
    }
}
