import java.io.*;
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.ChartUtilities; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Main {

    public static void main(String[] args) throws IOException{

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
    		engine = new ElevatorEngine(building, chart);
    	} else if (args[0].toUpperCase().equals("NC") ) {
    		System.out.println("Running Nearest Car algorithm");
    		engine = new ElevatorEngine (building, chart, new NearestCar(building));
    	} else if (args[0].toUpperCase().equals("FSO") ) {
            System.out.println("Running Fixed Sectoring Common Sector System algorithm");
            engine = new ElevatorEngine (building, chart, new FSO(building));
        } else {
    		System.out.println("Didn't recognise algorithm. Running default controller");
    		engine = new ElevatorEngine(building, chart);
    	}
        

        while (!engine.isRunning()) {}
        ElevatorEngine.R.printResult(building);
    }
}
