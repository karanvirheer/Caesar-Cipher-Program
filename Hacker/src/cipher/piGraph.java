package cipher;

/**
 * 
 * Where the Pie Graph is coded and created.
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
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

public class piGraph extends JFrame {
	
	/**
	 * Initializes the variables needed to create the Pie Graph.
	 * 
	 * @param piAppTitle The title of the application
	 * 
	 * @param piChartTitle The title of the chart
	 * 
	 */
	public piGraph(String piAppTitle, String piChartTitle) {

		PieDataset piDataset = createPiDataset();
		JFreeChart piChart = createChart(piDataset, piChartTitle);
		ChartPanel piChartPanel = new ChartPanel(piChart);
		piChartPanel.setPreferredSize(new java.awt.Dimension(500, 300));
		setContentPane(piChartPanel);
	}

	/**
	 * Creates the data set needed to graph the values by reading through a text file.
	 * 
	 * @return piResult The data needed to graph
	 */
	private PieDataset createPiDataset() {
		DefaultPieDataset piResult = new DefaultPieDataset();

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

				piResult.setValue(line, count);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return piResult;
	}

	
	/**
	 * Creates the Pie Graph and initializes all the variables and settings.
	 * 
	 * @param dataset The data that is being graphed.
	 * @param title The title of the graph.
	 * @return chart The type of graph that is being made, and the preferences for said chart.
	 */
	public JFreeChart createChart(PieDataset dataset, String title) {

		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false);

		PiePlot3D piChartPanel = (PiePlot3D) chart.getPlot();
		piChartPanel.setStartAngle(0);
		piChartPanel.setDirection(Rotation.CLOCKWISE);

		// transparency of the graph itself
		piChartPanel.setForegroundAlpha(1f);
		return chart;
	}
}
