package client.frontend.v2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.*;

public class CalendarFrame extends JPanel {


    public CalendarFrame(){

        setBackground(Color.gray);
        GridBagConstraints gbc = new GridBagConstraints();
        
        setLayout(new GridBagLayout());



    JPanel dateSelectorPanel = new JPanel(new GridLayout(1,3));
    JButton leftArrow = new JButton("<");
    JButton rightArrow = new JButton(">");
    JButton labelDate = new JButton(" du DD-MM-YYYY au DD-MM-YYYY ");
    labelDate.setBackground(Color.WHITE);

    labelDate.addActionListener(e -> {
        String date = new DatePick(this).Set_Picked_Date();
        if (!date.equals("")) {
            labelDate.setText(date);
        }
    });
    

    dateSelectorPanel.add(leftArrow);
    dateSelectorPanel.add(labelDate);
    dateSelectorPanel.add(rightArrow);


          // Mise en place du panneau minuteur
    gbc.gridx = 1;
    gbc.gridy = 1;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    gbc.fill = GridBagConstraints.NONE;
    gbc.anchor = GridBagConstraints.NORTH;
    gbc.weightx = 0.0;
    gbc.weighty = 0.0;
    gbc.insets = new Insets(1, 1, 1, 1);
    this.add(dateSelectorPanel,gbc);



        
    }
    
}
