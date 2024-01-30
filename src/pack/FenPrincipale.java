/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pack;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.*;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;

/**
 *
 * @author poteaum
 */
public class FenPrincipale extends JFrame {

    
    /**
     * Affiche la fenetre principale
     * @throws IOException 
     */
    public FenPrincipale() throws IOException {
        String minDate = "2022-08-31 00:00:00";
        String maxDate = "2023-09-01 23:00:00";
        setTitle("Affichage figures");
//        RecupDonnees recupDonnees = new RecupDonnees("GreenEr_data.csv");
        DataContainer recupDonnees = new DataContainer("GreenEr_data.csv");
        int nbVariales = recupDonnees.getNumberOfVariables();

        //setBounds(1, 1, 1000, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel menu = new JPanel();
        JPanel choixConsumption = new JPanel();
        JPanel entreeDate = new JPanel();
        JPanel texteDateInit = new JPanel();
        JPanel texteDateFin = new JPanel();
        JPanel texteDate = new JPanel();
        /*JPanel centerPanel = new JPanel();
        JPanel eastPanel = new JPanel();
        JPanel southPanel = new JPanel();*/
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        choixConsumption.setLayout(new FlowLayout());
        ArrayList<JCheckBox> listCheckbox = new ArrayList<JCheckBox>();
        for (String variableName : recupDonnees.getAvailableVariables()) {
            JCheckBox cb = new JCheckBox(variableName, false);
            choixConsumption.add(cb);
            listCheckbox.add(cb);
        }
        JCheckBox consMoinsProd = new JCheckBox("Green_Er_Consumption_kW - Green_Er_production_kW", false);
        choixConsumption.add(consMoinsProd);
        listCheckbox.add(consMoinsProd);
        
        
        
        for (JCheckBox cb : listCheckbox) {
            if (cb.isSelected()) {
                System.out.println(cb.getText());
            }
        }

        menu.add(choixConsumption);
        
        JPanel choixAffichage = new JPanel(new GridLayout(3,0));
        choixAffichage.setLayout(new FlowLayout());
        choixAffichage.add(new Label("Choose the display mode : "));
        JCheckBox choixHour = new JCheckBox("hourly", true);
        JCheckBox choixDay = new JCheckBox("daily", false);
        JCheckBox choixMonth = new JCheckBox("monthly", false);
        ButtonGroup checkBoxGroup = new ButtonGroup();
        checkBoxGroup.add(choixHour);
        checkBoxGroup.add(choixDay);
        checkBoxGroup.add(choixMonth);
        choixAffichage.add(choixHour);
        choixAffichage.add(choixDay);
        choixAffichage.add(choixMonth);
        ArrayList<JCheckBox> listAffichage = new ArrayList<JCheckBox>();
        listAffichage.add(choixDay);
        listAffichage.add(choixHour);
        listAffichage.add(choixMonth);
        menu.add(choixAffichage);
        
        
        
        texteDate.setLayout(new BoxLayout(texteDate, BoxLayout.Y_AXIS));
        entreeDate.setLayout(new FlowLayout());
        texteDateInit.setLayout(new FlowLayout());
        texteDateFin.setLayout(new FlowLayout());
        entreeDate.add(new Label("Veuillez ajouter les dates désirées"), BorderLayout.CENTER);
        texteDateInit.add(new Label("Initial date (format : yyyy-MM-dd HH:mm:ss)"));
        JTextField dateMinEntree = new JTextField("yyyy-MM-dd HH:mm:ss");
        texteDateInit.add(dateMinEntree);
        texteDateFin.add(new Label("Final date (format : yyyy-MM-dd HH:mm:ss)"));
        JTextField dateMaxEntree = new JTextField("yyyy-MM-dd HH:mm:ss");
        texteDateFin.add(dateMaxEntree);

        texteDate.add(texteDateInit);
        texteDate.add(texteDateFin);
        entreeDate.add(texteDate);

        JButton plotButton = new JButton("plot");
        JFrame fen = new JFrame("Erreur !");
        plotButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        fen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fen.setLayout(new FlowLayout());
        plotButton.addActionListener(new ChoixDateBouton(minDate, maxDate, dateMinEntree, dateMaxEntree, fen, plotButton, listCheckbox, recupDonnees, listAffichage));
        //plotButton.addActionListener(new ChoixDateBouton(minDate, maxDate, dateMaxEntree, fen, plotButton));
        entreeDate.add(plotButton);
        menu.add(entreeDate);
        //menu.add(plotButton);
        add(menu);
        //fen.setVisible(true);
        while (fen.isVisible()) {
            plotButton.setEnabled(false);
            System.out.println(true);
        }

        /*for (JCheckBox cb : listCheckbox) {
            if(cb.isSelected()){
                System.out.println(cb.getText());
            }
        }*/
        setVisible(true);
        pack();

    }

}
