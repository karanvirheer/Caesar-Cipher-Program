package cipher;

/**
 * 
 * Where the Bar Graph is coded and created.
 * 
 * @author Karanvir Heer
 * 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class barGraph extends JFrame{
	
	/**
	 * 
	 * Initializes the variables needed to create the Bar Graph.
	 * 
	 * @param appTitle The title of the application
	 * 
	 * @param chartTitle The title of the chart
	 * 
	 */
	public barGraph(String appTitle, String chartTitle) {

		CategoryDataset dataset = createDataset();
		JFreeChart chart = createChart(dataset, chartTitle);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 300));
		setContentPane(chartPanel);
	}

	/**
	 * Creates the data set needed to graph the values by reading through a text file.
	 * 
	 * @return result - The data needed to graph
	 */
	private CategoryDataset createDataset() {
		DefaultCategoryDataset result = new DefaultCategoryDataset();

		Map<String, Integer> words = new HashMap<String, Integer>();

		try {
			Scanner sc = new Scanner(new File(".\\Dictionary\\MessageWord.txt"));
			while (sc.hasNext()) {

				String line = sc.nextLine();

				// Get the count
				Integer count = words.get(line);

				if (count != null) {
					count++;
				} else {
					count = 1;
					words.put(line, count);
				}

				result.addValue(count, "frequency", line);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Creates the Bar Graph and initializes all the variables and settings.
	 * 
	 * @param dataset The data that is being graphed.
	 * @param title The title of the graph.
	 * @return chart The type of graph that is being made, and the preferences for said chart.
	 */
	public JFreeChart createChart(CategoryDataset dataset, String title) {

		JFreeChart chart = ChartFactory.createBarChart(title, "Words", "Frequency", dataset, PlotOrientation.VERTICAL, true, true,
				false);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
		setContentPane(chartPanel);

		return chart;
	}

}
