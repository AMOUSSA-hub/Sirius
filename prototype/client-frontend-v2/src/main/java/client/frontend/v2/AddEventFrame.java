package client.frontend.v2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.text.ParseException;

import edu.ezip.ing1.pds.business.dto.Stat;
import edu.ezip.ing1.pds.business.dto.TeamEvent;
import edu.ezip.ing1.pds.client.MainInsertClient;

public class AddEventFrame extends JDialog {


    JTextField labelEventFill;
    


    public AddEventFrame(JFrame fen){
        super(fen,true);


        // Chaîne de caractères représentant la date
        String dateString = "2024-05-05 12:03:00";

        // Format de la date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateObject = null;
        try {
            // Convertir la chaîne en objet Date
             dateObject = dateFormat.parse(dateString);
            System.out.println(dateObject); // Affiche la date convertie
        } catch (java.text.ParseException e) {
            // Gérer l'exception si la conversion échoue
            e.printStackTrace();
        }



        MainInsertClient.sendRequest(new TeamEvent(0, "Game6","FRIENDLY_GAME", dateObject,dateObject), "INSERT_EVENTS");

        JButton addStats = new JButton("ajouter des statistiques");

            MainInsertClient.sendRequest(new Stat((short)0,(short) 0,(short) 0,(short) 0,(short) 0,(short) 0, 6, 1), "INSERT_STATS");
    


            //Label évènement
        JPanel labelFillPan = createPanelFillAttributs("label", 200, 60);
        labelEventFill = new JTextField();
        labelEventFill.setPreferredSize(new Dimension(100, 25));
        JLabel nomLabel = new JLabel("titre de l'évènement");
        labelFillPan.add(nomLabel);
        labelFillPan.add(labelEventFill);
        

            JPanel bodyPanel = new JPanel();

            bodyPanel.add(labelFillPan);
            JPanel botPanel = new JPanel(new FlowLayout());
            
            JButton submit = new JButton("valider");
            JButton quit = new JButton("quitter");


        botPanel.add(submit);
        botPanel.add(quit);
        this.add(bodyPanel);
        this.add(botPanel, BorderLayout.SOUTH);
        this.pack();
        quit.addActionListener(e -> this.dispose());
        setLocationRelativeTo(fen);
        setVisible(true);


        
    }


     private JPanel createPanelFillAttributs(String titre,int width,int height){
    JPanel panel = new JPanel();
    panel.setBackground(Color.WHITE);
    panel.setPreferredSize(new Dimension(width, height));
    panel.setBorder(BorderFactory.createTitledBorder(titre));
    return panel;
  }
}
