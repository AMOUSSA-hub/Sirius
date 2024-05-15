package client.frontend.v2;

import java.awt.BorderLayout;
import edu.ezip.ing1.pds.business.dto.TeamEvent;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import edu.ezip.ing1.pds.client.MainSelectClient;
import java.io.*;
import java.util.Set;

import javax.swing.*;

public class CalendarFrame extends JPanel {


    MainSelectClient msc;
    static Set<TeamEvent> events;
    static JFrame fen ;

    public CalendarFrame(MainSelectClient select, JFrame fen){
        this.msc = select;
        this.fen = fen;
        setBackground(Color.gray);
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());

        



    JPanel dateSelectorPanel = new JPanel(new GridLayout(1,4));
    JButton leftArrow = new JButton("<");
    JButton rightArrow = new JButton(">");
    JButton labelDate = new JButton(" du DD-MM-YYYY au DD-MM-YYYY ");
    
    labelDate.setBackground(Color.WHITE);

    JButton addEventButton = new JButton("Ajouter un évènement");
    JButton refreshBUttonEvent = new JButton("récupérer les évènements");

    try{
        this.events = msc.getAllEvents();   
    }catch(Exception ex){
        System.err.println(ex);
    }


    GridTeamEvent gte =  new GridTeamEvent(this.events);



    

    labelDate.addActionListener(e -> {
        String date = new DatePick(this).Set_Picked_Date();
        if (!date.equals("")) {
            labelDate.setText(date);
        }
    });


    addEventButton.addActionListener(e -> new AddEventFrame(fen,gte));
    

    dateSelectorPanel.add(leftArrow);
    dateSelectorPanel.add(labelDate);
    dateSelectorPanel.add(rightArrow);
    dateSelectorPanel.add(refreshBUttonEvent);


    // Mise en place de sélection de la date
    // gbc.gridx = 2;
    // gbc.gridy = 1;
    // gbc.gridwidth = 1;
    // gbc.gridheight = 1;
    // gbc.fill = GridBagConstraints.NONE;
    // gbc.anchor = GridBagConstraints.NORTH;
    // gbc.weightx = 0.0;
    // gbc.weighty = 0.0;
    // gbc.insets = new Insets(1, 1, 1, 1);
    // this.add(dateSelectorPanel,gbc);


    //Mise en place du bouton d'ajout d'un évènement
    gbc.gridx = 1;
    gbc.gridy = 1;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    gbc.fill = GridBagConstraints.NONE;
    gbc.anchor = GridBagConstraints.EAST;
    gbc.weightx = 0.0;
    gbc.weighty = 0.0;
    gbc.insets = new Insets(1, 1, 1, 1);
    this.add(addEventButton,gbc);

    JScrollPane jp = new JScrollPane(gte);
//Mise en place de la grille d'évènement
gbc.gridx = 1;
gbc.gridy = 2;
gbc.gridwidth = 2;
gbc.gridheight = 1;
gbc.fill = GridBagConstraints.BOTH;
gbc.anchor = GridBagConstraints.CENTER;
gbc.weightx = 1.0;
gbc.weighty = 1.0;
gbc.insets = new Insets(1, 1, 100, 1);
this.add(jp,gbc);



        
    }

    public static Set<TeamEvent> getEvents() {
        return events;
    }

    public static void setEvents(Set<TeamEvent> events) {
        CalendarFrame.events = events;
    }

    public static JFrame getFen() {
        return fen;
    }
    
}
