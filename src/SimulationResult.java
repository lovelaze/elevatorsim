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
    public int totalTripTime;
    public int trips;
    public int servedCalls = 0;
    public int measures = 0;
    public float peopleFlow = 0;

    private DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
    private boolean chart;

    public SimulationResult(boolean chart) {
        this.chart = chart;
    }

    public void addValue(float waitingPassengers, int tick) {
        if(chart)
            line_chart_dataset.addValue( waitingPassengers , "passengers" , "" + tick );
    }

    public void printResult() throws IOException{
        printResult("fisk");
    }

    public int getAverageWaitingTime() {
        return totalWaitingTime/totalPassengers;
    }

    public void printResult(String algorithm) throws IOException{
        System.out.println("totalCalls = " + totalCalls);
        System.out.println("totalPassengers = " + totalPassengers);
        System.out.println("totalTravelTime = " + totalTravelTime);
        System.out.println("totalWaitingTime = " + totalWaitingTime);
        System.out.println("averageRoundTripTime = " + (totalTripTime/trips));
        System.out.println("averageWaitingTime = " + (totalWaitingTime/totalPassengers));
        System.out.println("averagePeopleFlow = " + (peopleFlow/measures));

        if(chart) {

            JFreeChart lineChartObject = ChartFactory.createLineChart(
             "Passenger flow","Ticks",
             "Passengers in elevators",
             line_chart_dataset,PlotOrientation.VERTICAL,
             true,true,false);

            int width = 640; /* Width of the image */
            int height = 480; /* Height of the image */ 
            File lineChart = new File( algorithm + ".jpeg" ); 
            ChartUtilities.saveChartAsJPEG(lineChart ,lineChartObject, width ,height);

        }

    }

}
