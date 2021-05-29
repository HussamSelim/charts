package xCharting;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class xChartExamples {

	public static void main(String[] args) {
		//graphPassengerAges(getPassengersFromJsonFile());
	xChartExamples obj=new xChartExamples();
	//obj.graphPassengerClass(getPassengersFromJsonFile());
	//obj.graphPassengerSurvived(getPassengersFromJsonFile());
	obj.graphPassengerSurvivedGender(getPassengersFromJsonFile());
	}
	public static void graphPassengerAges(List<Passengers> passengerList) {
		List<Float> pAges= passengerList.stream().map (Passengers::getAge).limit (8).collect (Collectors.toList());
		List<String> pNames= passengerList.stream().map (Passengers::getName).limit (8).collect (Collectors.toList());
		
		CategoryChart chart = new CategoryChartBuilder().width (1024).height (768).title ("Age Histogram").xAxisTitle("Names").yAxisTitle("Age").build();
		chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideE);
		chart.getStyler().setHasAnnotations(true);
		chart.getStyler().setStacked(true);
		chart.addSeries("Passenger's Ages", pNames, pAges);
		new SwingWrapper(chart).displayChart();
		
		}
	public static List<Passengers> getPassengersFromJsonFile() {
        List<Passengers> allPassengers = new ArrayList<Passengers> ();
        ObjectMapper objectMapper = new ObjectMapper ();
        objectMapper.configure (DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try (InputStream input = new FileInputStream ("E:\\iti\\Java & UML Programming\\DAY 6\\titanic_csv.json")) {
            //Read JSON file
            allPassengers = objectMapper.readValue (input, new TypeReference<List<Passengers>> () {
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
return allPassengers;

}
	public void graphPassengerClass(List<Passengers> passengerList) {
		//filter to get a map of passenger class and total number of passengers in each class
		Map<String, Long> result =passengerList.stream().collect (
				Collectors.groupingBy(Passengers::getPclass, Collectors.counting() ) );
	
	PieChart chart = new PieChartBuilder().width (800).height (600).title (getClass().getSimpleName()).build ();
	// Customize Chart
	Color[] sliceColors= new Color[]{new Color (180, 68, 50), new Color (130, 105, 120), new Color (80, 143, 160)};
	chart.getStyler().setSeriesColors(sliceColors);
	// Series
	chart.addSeries("First Class", result.get("1"));
	chart.addSeries("Second Class", result.get("2"));
	chart.addSeries("Third Class", result.get("3"));
	// Show it 
	new SwingWrapper(chart).displayChart();
	}
	
	public void graphPassengerSurvived(List<Passengers> passengerList) {
		
		Map<String, Long> result =passengerList.stream().collect (
				Collectors.groupingBy(Passengers::getSurvived, Collectors.counting() ) );
	
	PieChart chart = new PieChartBuilder().width (800).height (600).title (getClass().getSimpleName()).build ();
	// Customize Chart
	Color[] sliceColors= new Color[]{new Color (180, 68, 50), new Color (130, 105, 120), new Color (80, 143, 160)};
	chart.getStyler().setSeriesColors(sliceColors);
	// Series
	chart.addSeries("Survived", result.get("0"));
	chart.addSeries("Not Survived", result.get("1"));
	// Show it 
	new SwingWrapper(chart).displayChart();
	}
public void graphPassengerSurvivedGender(List<Passengers> passengerList) {
		
		Map<String, Long> result =passengerList.stream().filter(x->x.getSurvived().equals("0")).collect (
				Collectors.groupingBy(Passengers::getSex, Collectors.counting() ) );
	
	PieChart chart = new PieChartBuilder().width (800).height (600).title (getClass().getSimpleName()).build ();
	// Customize Chart
	Color[] sliceColors= new Color[]{new Color (180, 68, 50), new Color (130, 105, 120), new Color (80, 143, 160)};
	chart.getStyler().setSeriesColors(sliceColors);
	// Series
	chart.addSeries("Dead Males", result.get("male"));
	chart.addSeries("Dead Females", result.get("female"));
	// Show it 
	new SwingWrapper(chart).displayChart();
}
}