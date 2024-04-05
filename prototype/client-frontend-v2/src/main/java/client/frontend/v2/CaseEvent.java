package client.frontend.v2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.border.Border;

import edu.ezip.ing1.pds.business.dto.TeamEvent;
import edu.ezip.ing1.pds.client.MainInsertClient;

public class CaseEvent  extends JPanel {

    private String formattedDate;

    public CaseEvent(TeamEvent event){
        this.setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        formattedDate = new SimpleDateFormat("EEEE dd MMMM 'à' HH'h'mm", new DateFormatSymbols() {
            @Override
            public String[] getWeekdays() {
                return new String[]{"", "Dimanche", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"};
            }

            @Override
            public String[] getMonths() {
                return new String[]{"janvier","février", "mars", "avril", "mai", "juin", "juillet", "août", "septembre", "octobre", "novembre", "décembre"};
            }
        }).format(event.getDateDebut());

        JPanel nothJPanel = new JPanel(new GridLayout(3,1));
        JPanel soutJPanel = new JPanel(new FlowLayout());

        JLabel titleEvent = new JLabel(event.getLabel().toUpperCase(),SwingConstants.CENTER);
        JLabel dateEvent = new JLabel(formattedDate,SwingConstants.CENTER);
        JLabel typeEvent = new JLabel(event.getType(),SwingConstants.CENTER);

        JButton editButton = new JButton("modifier");
        JButton deleteButton = new JButton("supprimer");

        dateEvent.setForeground(Color.BLUE);
        typeEvent.setForeground(Color.GRAY);
        typeEvent.setFont(new Font("Italic",Font.ITALIC,10));

        this.setBorder(BorderFactory.createLineBorder(Color.black, 1));

        nothJPanel.add(titleEvent);
        nothJPanel.add(dateEvent);
        nothJPanel.add(typeEvent);

        soutJPanel.add(editButton);
        soutJPanel.add(deleteButton);

        deleteButton.addActionListener(e ->{
        int response = JOptionPane.showConfirmDialog(null, "Voulez-vous supprimer cet évènement ?", "supprimer Evenement", JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
           System.out.println( "Evenement: "+event.getLabel()+" id: "+event.getId()+" supprime");

           if(MainInsertClient.deleteRequest(event, "DELETE_EVENT") == 1){
                ((GridTeamEvent)this.getParent()).removeCaseEvent(this);
           }
        }
        });

        this.add(nothJPanel,BorderLayout.CENTER);
        this.add(soutJPanel,BorderLayout.SOUTH);




    }
    
    
}
