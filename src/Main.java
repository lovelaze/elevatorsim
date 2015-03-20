public class Main {

    public static void main(String[] args) {

        Building building = BuildingFactory.defaultBuilding();
        ElevatorEngine engine = new ElevatorEngine(building);

        engine.start();
        while (!engine.isRunning()) {}
        engine.stop();
        ElevatorEngine.R.printResult();
    }
}
