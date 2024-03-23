package client.frontend.v2;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.*;
import javax.swing.border.Border;

public class CaseEvent  extends JPanel {

    public CaseEvent(){
        this.setBackground(Color.WHITE);

        JLabel labelEvent = new JLabel("<html>_________<br>___<html>");

        Border lineborder = BorderFactory.createLineBorder(Color.black, 1); 
        labelEvent.setBorder(lineborder);

        this.add(labelEvent,BorderLayout.CENTER);




    }
    
}
