package com.engcpp.utils;

import static java.awt.Color.BLACK;
import static java.awt.Color.GRAY;
import static java.awt.Color.WHITE;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.swing.JDialog;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author engcpp
 */
public class Chart {
    private List<Serie>series;
    
    public Chart(){
        this(null);
    }
    
    public Chart(Serie serie){
        series = new ArrayList<>();

        if (serie != null)
            series.add(serie);
    }    
    
    public void addSerie(Serie serie) {
        this.series.add(serie);
    }
    
    public void showChart(boolean legend, String title, String xLabel, String yLable) {        
        String chartTitle = title;
        String xAxisLabel = xLabel;
        String yAxisLabel = yLable;
        
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        XYSeriesCollection dataset = new XYSeriesCollection();
        
        for (int s=0; s < series.size(); s++) {
            Serie serie = series.get(s);
            
            XYSeries xySerie = new XYSeries(serie.getLabel());
            Matrix matrix = series.get(s).getData();
            
            for (int row=0; row<matrix.rowsCount(); row++)
                 xySerie.add(matrix.get(row, 0), matrix.get(row, 1));
            
            dataset.addSeries(xySerie);
             renderer.setSeriesPaint(s, serie.getColor());
             renderer.setSeriesLinesVisible(s, serie.isLineVisible());
             renderer.setSeriesShapesFilled(s, serie.isShapeFilled());
        }                       
        
        JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, 
           xAxisLabel, yAxisLabel, dataset, PlotOrientation.VERTICAL, 
           legend, false, false);

        
        chart.getXYPlot().setBackgroundPaint(WHITE);
        chart.getXYPlot().setDomainGridlinePaint(GRAY);
        chart.getXYPlot().setRangeGridlinePaint(GRAY);
        chart.getXYPlot().setRenderer(renderer);
        ((NumberAxis)chart.getXYPlot().getRangeAxis()).setAutoRangeIncludesZero(false);
                
        // Create and show dlg
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        
        JDialog dlg = new JDialog();        
        
        dlg.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dlg.setModal(true);
        dlg.setSize(800, 600);                
        dlg.setLocation((screen.width - dlg.getWidth())/2,
                (screen.height- dlg.getHeight())/2);
        dlg.getContentPane().add(new ChartPanel(chart));
        dlg.show();   
    }
    
 
}
