package xCharting;

import java.util.List;
import java.util.stream.Collectors;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler;

public class graphPassengers {
	public void graphPassengerAges(List<Passengers> passengerList) {
		List<Float> pAges= passengerList.stream().map (Passengers::getAge).limit (8).collect (Collectors.toList());
		List<String> pNames= passengerList.stream().map (Passengers::getName).limit (8).collect (Collectors.toList());
		
		CategoryChart chart = new CategoryChartBuilder().width (1024).height (768).title ("Age Histogram").xAxisTitle("Names").yAxisTitle("Age").build();
		chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
		chart.getStyler().setHasAnnotations(true);
		chart.getStyler().setStacked(true);
		chart.addSeries("Passenger's Ages", pNames, pAges);
		new SwingWrapper(chart).displayChart();
		
		}

}
