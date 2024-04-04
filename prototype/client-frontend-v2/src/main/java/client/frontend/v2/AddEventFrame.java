package client.frontend.v2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddEventFrame extends JDialog {


    JTextField labelEventFill;
    


    public AddEventFrame(JFrame fen){
        super(fen,true);


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
