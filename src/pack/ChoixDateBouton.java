/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pack;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;

/**
 *
 * @author poteaum
 */
public class ChoixDateBouton implements ActionListener {
    String minDate;
    String maxDate;
    JTextField date1;
    JTextField date2;
    JFrame fen;
    JButton boutonRef;
    ArrayList<JCheckBox> listCheckbox;
    DataContainer recupDonnees;
    ArrayList<JCheckBox> listAffichage;
    /**
     * Vérifie que les dates sont au bon format et entre la plage d'observation
     * @param minDate date minimale plage de donnée
     * @param maxDate date maximale plage de donnée
     * @param date1 date minimale entree par utlisateur
     * @param date2 date maximale entree par utlisateur
     * @param fen fenetre d'affichage des erreurs
     * @param bouton bouton plot de la fenetre principale
     * @param listCheckbox liste des variables, on peut ainsi récupérer les checkbox qui ont été cochées
     * @param recupDonnees l'ensemble des données contenues dans "GreenEr_data.csv"
     * @param listAffichage liste des modes d'affichage des courbes (hour, month, day) on peut ainsi récupérer la checkbox qui a été cochée
     */
    public ChoixDateBouton(String minDate, String maxDate,JTextField date1, JTextField date2, JFrame fen, JButton bouton, ArrayList<JCheckBox> listCheckbox, DataContainer recupDonnees, ArrayList<JCheckBox> listAffichage){
       //System.out.println(date1);
       System.out.println(minDate);
        
       this.minDate=minDate;
       this.maxDate=maxDate;
       this.date1=date1;
       this.date2=date2;
       this.fen = fen;
       this.boutonRef = bouton;
       this.listCheckbox = listCheckbox;
       this.recupDonnees = recupDonnees;
       this.listAffichage = listAffichage;
       
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        //panLabel.setLayout(new BoxLayout(panLabel, BoxLayout.Y_AXIS));
        fen.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        fen.setResizable(false);
        
        
        JButton okButton = new JButton("Ok");
        okButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        okButton.addActionListener(new OkErreurBouton(fen));
        //JFrame fen = new JFrame();
        
        
        try { // Si le format est bon
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date minD = format.parse(this.minDate);
            Date maxD = format.parse(this.maxDate);
            Date dateText1 = format.parse(this.date1.getText());
            Date dateText2 = format.parse(this.date2.getText());
            System.out.println(minD);
            String messageErreur1 = "";
            String messageErreur2 = "";
            int countErreur1 = 0;
            int countErreur2 = 0;
            
            
            
            
                if (dateText1.after(minD) && dateText1.before(maxD)) {
                    System.out.println(minD);
                    ArrayList<String> nameCbSelected = new ArrayList<String>();
                    String nameDisplayType = "";
                   for (JCheckBox cb : listCheckbox) {
                        if(cb.isSelected()){
                            nameCbSelected.add(cb.getText());
                        }
                    }
                   
                   for (JCheckBox dt : listAffichage) {
                       if (dt.isSelected()){
                           nameDisplayType = dt.getText();
                       }
                   }
                   
                   PlotGraph plotGraph = new PlotGraph(nameCbSelected, recupDonnees, date1, date2, minD, maxD, nameDisplayType);
                } else{
                    countErreur1++;
                    messageErreur1 = messageErreur1 + " initial date ";
                   
                }
                if (dateText2.after(minD) && dateText2.before(maxD)) {
                } else{
                    countErreur2++;
                    messageErreur2 = messageErreur2 + " final date ";
                   
                }
                
                int countErreur = countErreur1 + countErreur2;
                
                if (countErreur != 0){
                    String messageErreur = "";
                   
                    if (countErreur == 2){
                        messageErreur = messageErreur1+ " and " + messageErreur2 + " are ";
                    } else {
                        if (countErreur1 == 1){
                            messageErreur = messageErreur1+ " is ";
                        }
                         if (countErreur2 == 1){
                            messageErreur = messageErreur2+ " is ";
                        }
                    }
                    
                    
                    fen.getContentPane().removeAll();
                    JLabel label = new JLabel("Problem with the dates");
                    JPanel panLabel = new JPanel();
                    panLabel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
                    
                    label.setText("NO!! the" + messageErreur + "not included between the two dates");
                    
                    fen.add(label);
                    fen.add(okButton);
                    
                    fen.pack();
                    fen.setVisible(true);
                    boutonRef.setEnabled(false);
                }


        } catch (ParseException ex) {
            fen.getContentPane().removeAll();
            
            JLabel label = new JLabel("Problem with the dates");
        
            label.setText(" Date format ERROR!! Could you re-enter the date in the correct format? ");
            
            fen.add(label);
            fen.add(okButton);
            fen.pack();
            fen.setVisible(true);
            boutonRef.setEnabled(false);
        }
        fen.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                //fen.dispose();
                boutonRef.setEnabled(true);
                
                
            }

           
        });
        
        
        

        

    }
}
