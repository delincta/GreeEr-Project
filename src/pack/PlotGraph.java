/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pack;

import java.text.DecimalFormat;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JTextField;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

/**
 *
 * @author poteaum
 */
public class PlotGraph {
    ArrayList<String> listVariables;
    DataContainer recupDonnees;
    JTextField dateMin;
    JTextField dateMax;
    Date minD;
    Date maxD;
    String nameDisplayType;
    
    
    
    /**
     * Affiche le graph sur la plage demandé par l'utlisateur
     * @param listVariables liste des variables qui ont été sélectionnées par l'utilisateur
     * @param recupDonnees ensemble des données contenues dans "GreenEr_data.csv"
     * @param dateMin date minimale entree par utlisateur
     * @param dateMax date maximale entree par utlisateur
     * @param minDate  date minimale plage de donnée
     * @param maxDate   date maximale plage de donnée
     * @param nameDisplayType  variable qui dit si l'affichage se fera par heure, par jour ou par mois
     * @throws ParseException 
     */
    public PlotGraph(ArrayList<String> listVariables, DataContainer recupDonnees, JTextField dateMin, JTextField dateMax, Date minDate, Date maxDate, String nameDisplayType) throws ParseException{
        this.listVariables = listVariables;
        this.recupDonnees = recupDonnees;
        this.dateMin = dateMin;
        this.dateMax = dateMax;
        this.minD = minD;
        this.maxD = maxD;
        this.nameDisplayType = nameDisplayType;
        
        
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateTextMin = format.parse(this.dateMin.getText());
      
        Date dateTextMax = format.parse(this.dateMax.getText());
        Date[] toutesDates = recupDonnees.getDates();
        /*for (Date date : toutesDates){
            System.out.println(date);
        }*/
        TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();
        Double[] values = new Double[recupDonnees.numberOfSamples];
        double totalCons = 0.0;
        double totalProd = 0.0;
        Double[] valCons = this.recupDonnees.getData("Green_Er_Consumption_kW");
        Double[] valProd = this.recupDonnees.getData("Green_Er_production_kW");
        switch(nameDisplayType){
            case "daily":
                TreeMap<Day, ArrayList<Double>> dailyValues = new TreeMap<>();
                for(String var : listVariables) {
                    if ("Green_Er_Consumption_kW - Green_Er_production_kW".equals(var)) {

                        for(int i=0; i<recupDonnees.numberOfSamples; i++) {
                            values[i] = valCons[i] - valProd[i];
                        }
                    } else {
                        values = this.recupDonnees.getData(var);
                    }
                    
                    
                    
                    for(int i=0; i<recupDonnees.numberOfSamples; i++){
                        if (toutesDates[i].after(dateTextMin) && toutesDates[i].before(dateTextMax)){
                            totalCons += valCons[i];
                            totalProd += valProd[i];
                            Day day = new Day(toutesDates[i]);
                            if (!dailyValues.containsKey(day)) {
                                dailyValues.put(day, new ArrayList<>());
                            }
                            dailyValues.get(day).add(values[i]);
                        }
                    }
                    TimeSeries timeSeries = new TimeSeries(var);
                    for (Day day : dailyValues.keySet())  {
                        ArrayList<Double> dailyData = dailyValues.get(day);
                        double average = dailyData.stream().mapToDouble(Double::doubleValue).average().orElse(-1);
                        timeSeries.add(day,average);
                    }
                    timeSeriesCollection.addSeries(timeSeries);
                }
                

                break;
            case "monthly" :
                TreeMap<Month, ArrayList<Double>> monthlyValues = new TreeMap<>();
                for(String var : listVariables) {
                    if ("Green_Er_Consumption_kW - Green_Er_production_kW".equals(var)) {

                        for(int i=0; i<recupDonnees.numberOfSamples; i++) {
                            values[i] = valCons[i] - valProd[i];
                        }
                    } else {
                        values = this.recupDonnees.getData(var);
                    }
                    
                    
                    for(int i=0; i<recupDonnees.numberOfSamples; i++){
                        if (toutesDates[i].after(dateTextMin) && toutesDates[i].before(dateTextMax)){
                            totalCons += valCons[i];
                            totalProd += valProd[i];
                            Month month = new Month(toutesDates[i]);
                            if (!monthlyValues.containsKey(month)) {
                                monthlyValues.put(month, new ArrayList<>());
                            }
                            monthlyValues.get(month).add(values[i]);
                        }
                    }
                    TimeSeries timeSeries = new TimeSeries(var);
                    for (Month month : monthlyValues.keySet())  {
                        ArrayList<Double> monthlyData = monthlyValues.get(month);
                        double average = monthlyData.stream().mapToDouble(Double::doubleValue).average().orElse(-1);
                        timeSeries.add(month,average);
                    }
                    timeSeriesCollection.addSeries(timeSeries);
                }
                break;
            default :
                for(String var : listVariables) {
                    if ("Green_Er_Consumption_kW - Green_Er_production_kW".equals(var)) {

                        for(int i=0; i<recupDonnees.numberOfSamples; i++) {
                            values[i] = valCons[i] - valProd[i];
                        }
                    } else {
                        values = this.recupDonnees.getData(var);
                    }
                
                TimeSeries timeSeries = new TimeSeries(var);
                for(int i = 0; i < recupDonnees.numberOfSamples; i++) {
                    if (toutesDates[i].after(dateTextMin) && toutesDates[i].before(dateTextMax)) {
                        totalCons += valCons[i];
                        totalProd += valProd[i];
                        timeSeries.add(new Hour(toutesDates[i]), values[i]);
                    }
                }
                    timeSeriesCollection.addSeries(timeSeries);
                }
                break;
        }
            
            double perAutoEnergy = (totalProd / totalCons) * 100;
            DecimalFormat df = new DecimalFormat("0.00");
            JPanel chartPanel = new ChartPanel(ChartFactory.createTimeSeriesChart("figure","date","USI",timeSeriesCollection,true, true, false));
            JFrame frame = new JFrame("Test");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            JLabel autonomyLabel = new JLabel("Percentage of building operating energy produced autonomously: " + df.format(perAutoEnergy) + "%");
            autonomyLabel.setHorizontalAlignment(JLabel.CENTER);
            JPanel contentPane = new JPanel(new BorderLayout());
            
            contentPane.add(chartPanel, BorderLayout.CENTER);
            contentPane.add(autonomyLabel, BorderLayout.SOUTH);
            frame.setContentPane(contentPane);
            frame.pack();
            frame.setVisible(true);        
    }
    
    
}

