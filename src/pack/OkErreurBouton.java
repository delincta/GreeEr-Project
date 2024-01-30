/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 *
 * @author poteaum
 */
public class OkErreurBouton implements ActionListener{
    JFrame fen;
    public OkErreurBouton(JFrame fen){
       this.fen = fen;
       
       
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        this.fen.dispose();
    }
    
}
