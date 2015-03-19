public class Main {

    public static void main(String[] args) {

        Building building = BuildingFactory.defaultBuilding();
        ElevatorEngine engine = new ElevatorEngine(building);

        engine.start();
        while (engine.getSimulationResult() == null) { }
        SimulationResult res = engine.getSimulationResult();
        res.printResult();
    }
}
