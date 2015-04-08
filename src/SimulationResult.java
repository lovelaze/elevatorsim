import java.io.*;
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.ChartUtilities; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Created by Bengan on 3/19/2015.
 */
public class SimulationResult {

    public int totalCalls;
    public int totalPassengers;
    public int totalTravelTime;
    public int totalWaitingTime;
    public int averageRoundTripTime;

    private DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
    private boolean chart;

    public SimulationResult(boolean chart) {
        this.chart = chart;
    }

    public void addValue(int waitingPassengers, int tick) {
        if(chart)
            line_chart_dataset.addValue( waitingPassengers , "passengers" , "" + tick );
    }

    public void printResult(Building building) throws IOException{
        printResult(building, "fisk");
    }

    public void printResult(Building building, String algorithm) throws IOException{
        System.out.println("totalCalls = " + totalCalls);
        System.out.println("totalPassengers = " + totalPassengers);
        System.out.println("totalTravelTime = " + totalTravelTime);
        System.out.println("totalWaitingTime = " + totalWaitingTime);
        System.out.println("averageRoundTripTime = " + averageRoundTripTime);
        System.out.println("averageWaitingTime = " + (totalWaitingTime/totalPassengers));
        for(Car car : building.getCars()) {
            System.out.println(car.getResult());
        }

        if(chart) {

            JFreeChart lineChartObject = ChartFactory.createLineChart(
             "Waiting calls every tick","Tick",
             "Waiting calls",
             line_chart_dataset,PlotOrientation.VERTICAL,
             true,true,false);

            int width = 640; /* Width of the image */
            int height = 480; /* Height of the image */ 
            File lineChart = new File( algorithm + ".jpeg" ); 
            ChartUtilities.saveChartAsJPEG(lineChart ,lineChartObject, width ,height);

        }

    }

}
