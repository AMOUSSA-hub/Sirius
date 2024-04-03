package client.frontend.v2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

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
    @Override
    protected void paintComponent(Graphics g) {
        //setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));
        super.paintComponent(g);
    }
    
}
