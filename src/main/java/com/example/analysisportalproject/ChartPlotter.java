package com.example.analysisportalproject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;

public class ChartPlotter {

    private XYChart<Double, Double> graph;
    private double range;

    public ChartPlotter(final XYChart<Double, Double> graph, final double range){
        this.graph = graph;
        this.range = range;
    }
    public void plotLine(final HashMap<Double, Double> function){
        final XYChart.Series<Double, Double> series = new XYChart.Series<Double, Double>();
            function.forEach((k,v) -> {
                        plotPoint(k, v, series);
            });
            graph.getData().add(series);
    }

    public void plotPoint(final double x, final double y, final XYChart.Series<Double, Double> series){
        series.getData().add(new XYChart.Data<Double, Double>(x,y));
    }

    public void clearGraph(){
        graph.getData().clear();
    }
}
