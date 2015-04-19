import java.io.*;
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.ChartUtilities; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Main {

    public static void main(String[] args) throws IOException{

        int waitingTime = 0;

        for(int i=0; i<30; i++) {
            boolean chart = false;

            Building building = BuildingFactory.defaultBuilding();
            ElevatorEngine engine = null;

            if(args.length >= 2){
                if(args[1].toUpperCase().equals("TRUE")) {
                    Log.startLogging();
                } else if(args[1].toUpperCase().equals("CHART")) {
                    Log.makingChart();
                    chart = true;
                }
            }

            if (args.length >= 1 && (args[0] == null || args[0].equals("default"))) {
                System.out.println("Running default controller");
                engine = new ElevatorEngine(building, chart, true);
            } else if (args[0].toUpperCase().equals("NC") ) {
                System.out.println("Running Nearest Car algorithm");
                if(args.length >= 3 && args[2].equalsIgnoreCase("true")) {
                    engine = new ElevatorEngine (building, chart, new NearestCar(building, true), true);
                } else {
                    engine = new ElevatorEngine (building, chart, new NearestCar(building, false), true);
                }

            } else if (args[0].toUpperCase().equals("FSO") ) {
                System.out.println("Running Fixed Sectoring Common Sector System algorithm");
                engine = new ElevatorEngine (building, chart, new FSO(building), true);
            } else if (args[0].toUpperCase().equals("MULTI") ) {
                System.out.println("Running Multi directional elevator algorithm");
                building = BuildingFactory.multiBuilding();
                if(args.length >= 3 && args[2].equalsIgnoreCase("true")) {
                    engine = new ElevatorEngine (building, chart, new MultiDirController(building, true), false);
                } else {
                    engine = new ElevatorEngine (building, chart, new MultiDirController(building, false), false);
                }
            } else {
                System.out.println("Didn't recognise algorithm. Running default controller");
                engine = new ElevatorEngine(building, chart, true);
            }


            while (!engine.isRunning()) {}

            waitingTime += ElevatorEngine.R.getAverageWaitingTime();

        }

        /*if (args.length >= 1)
            ElevatorEngine.R.printResult(args[0].toUpperCase());
        else
            ElevatorEngine.R.printResult();*/
        System.out.println("AvgWait: " + waitingTime/30);
    }
}
