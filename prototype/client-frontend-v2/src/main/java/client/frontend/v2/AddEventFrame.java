package client.frontend.v2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Timestamp;
import java.util.HashMap;

import javax.sound.midi.Soundbank;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;


import java.text.ParseException;

import edu.ezip.ing1.pds.business.dto.Game;
import edu.ezip.ing1.pds.business.dto.Stat;
import edu.ezip.ing1.pds.business.dto.TeamEvent;
import edu.ezip.ing1.pds.client.MainInsertClient;

public class AddEventFrame extends JDialog {





    public AddEventFrame(JFrame fen, GridTeamEvent gte){
        super(fen,"Créer un évènement",true);


    
        JPanel bodyPanel = new JPanel();

        bodyPanel.setLayout(new GridLayout(5,1));

            //Label évènement
        JPanel labelFillPan = createFillAttributs("label");
        JTextField labelEventFill = new JTextField();
        labelEventFill.setPreferredSize(new Dimension(200, 25));
        labelFillPan.add(labelEventFill);
        bodyPanel.add(labelFillPan);


         //Type évènement
         JPanel TypeFillPan = createFillAttributs("Type");
         String [] allTypeEvent = TeamEvent.getAllTypeEvent();
         JComboBox<String> typeEventList = new JComboBox<String>(allTypeEvent);
         typeEventList.setPreferredSize(new Dimension(200, 25));
         TypeFillPan.add(typeEventList);
         bodyPanel.add(TypeFillPan);
         




         //Panneau informations matchs
        JPanel MatchFillPan = createFillAttributs("Informations Match");
            //Adversaire
            JPanel opponentNameFillPan = createFillAttributs("Adversaire");
            JTextField opponentNameFill = new JTextField();        
            opponentNameFill.setPreferredSize(new Dimension(200, 25));
            opponentNameFillPan.add(opponentNameFill);
            MatchFillPan.add(opponentNameFillPan);
            
            //Stade
            JPanel arenaFillPan = createFillAttributs("Stade");
            JTextField arenaFill = new JTextField();        
            arenaFill.setPreferredSize(new Dimension(200, 25));
            arenaFillPan.add(arenaFill);
            MatchFillPan.add(arenaFillPan);

            //Competition
            JPanel championshipFillFillPan = createFillAttributs("Competition");
            JTextField championshipFill = new JTextField();        
            championshipFill.setPreferredSize(new Dimension(200, 25));
            championshipFillFillPan.add(championshipFill);
            MatchFillPan.add(championshipFillFillPan);
            
            MatchFillPan.setVisible(false);
        bodyPanel.add(MatchFillPan);

         //Date Début évènement
         JPanel startingDateFillPan = createFillAttributs("Date DEBUT");
         SpinnerDateModel startingDatemodel = new SpinnerDateModel();
         startingDatemodel.setCalendarField(Calendar.DAY_OF_MONTH);
        JSpinner startingDateSpinner = new JSpinner(startingDatemodel);
        startingDateSpinner.setPreferredSize(new Dimension(200, 25));
         startingDateFillPan.add(startingDateSpinner);
         bodyPanel.add(startingDateFillPan);

          //Date Fin évènement
          JPanel endingDateFillPan = createFillAttributs("Date FIN");
          SpinnerDateModel endingDatemodel = new SpinnerDateModel();
         endingDatemodel.setCalendarField(Calendar.DAY_OF_MONTH);
         JSpinner endingDateSpinner = new JSpinner(endingDatemodel);
         endingDateSpinner.setPreferredSize(new Dimension(200, 25));
         endingDateFillPan.add(endingDateSpinner);
          bodyPanel.add(endingDateFillPan);

         
          typeEventList.addActionListener(e -> {
           
        
            String selectedType = (String) typeEventList.getSelectedItem();
            if (selectedType != null) {
                if (selectedType.equals(TeamEvent.Type.FRIENDY_GAME.type) || selectedType.equals(TeamEvent.Type.GAME.type)) {
                    System.out.println("detected");
                    MatchFillPan.setVisible(true);
                } else {
                    MatchFillPan.setVisible(false);
                }

                this.pack();
            }
        });
   

            
            JPanel botPanel = new JPanel(new FlowLayout());
            
            JButton submit = new JButton("valider");
            JButton quit = new JButton("quitter");


            submit.addActionListener( e->{

                Timestamp startingDate = new Timestamp(((java.util.Date)startingDateSpinner.getValue()).getTime()); 
                Timestamp endingDate = new Timestamp(((java.util.Date)endingDateSpinner.getValue()).getTime()); 
                String label = labelEventFill.getText();


               
                boolean alreadyExist = false;
                boolean isGame  = false;

                if(((String) typeEventList.getSelectedItem()).equals(TeamEvent.Type.FRIENDY_GAME.type) || ((String) typeEventList.getSelectedItem()).equals(TeamEvent.Type.GAME.type)){
                    isGame = true;
                }

                for (TeamEvent te :CalendarFrame.events){

                    if(label.equals(te.getLabel()) && startingDate.equals(te.getDateDebut()) && endingDate.equals(te.getDateFin()) ){

                        alreadyExist = true;
                    }
                }

                if( label.trim().equals("") ||(isGame && (opponentNameFill.getText().trim().equals("") || arenaFill.getText().trim().equals("") || championshipFill.getText().trim().equals("") ))){
                    JOptionPane.showMessageDialog(null, "Veullez renseigner tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }

                else if(startingDate.compareTo(endingDate) >= 0 ){
                    JOptionPane.showMessageDialog(null, "la date de début doit être strictement antérieur à celle de fin. ", "Erreur", JOptionPane.ERROR_MESSAGE);                  

                }else if( alreadyExist){
                    JOptionPane.showMessageDialog(null, "Cet évènement existe dejà !", "Erreur", JOptionPane.ERROR_MESSAGE);     
                } else{
                    
                    TeamEvent te = new TeamEvent(0,label ,typeEventList.getSelectedItem().toString(),startingDate,endingDate);
                    te.setId(MainInsertClient.sendRequest(te, "INSERT_EVENTS")); 
                    //gte.addCaseEvent(te);
                    Mastermind.addEvent(te);
                    gte.fillGrid(Mastermind.getEventsSet());


                    if(isGame){
                        Game g = new Game(0,opponentNameFill.getText(), arenaFill.getText(), championshipFill.getText(), new java.sql.Timestamp(startingDate.getTime()),te.getId());
                        int res = MainInsertClient.sendRequest(g, "INSERT_MATCH");
                        if (res != -1) {
                            g.setId(res);
                            //FootballFormationFrame.games.add(g);
                            //FootballFormationFrame.refreshMatch();
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Evènement créé avec succès !", "Validation", JOptionPane.INFORMATION_MESSAGE);   

                    this.dispose();
                }

            });

        botPanel.add(submit);
        botPanel.add(quit);
        this.add(bodyPanel);
        this.add(botPanel, BorderLayout.SOUTH);
        this.pack();
        quit.addActionListener(e -> this.dispose());
        setLocationRelativeTo(fen);
        setVisible(true);


        
    }


    public AddEventFrame(JFrame fen, GridTeamEvent gte,TeamEvent te){
        super(fen,"Modifier un évènement",true);

    
        JPanel bodyPanel = new JPanel();

        bodyPanel.setLayout(new GridLayout(4,1));

            //Label évènement
        JPanel labelFillPan = createFillAttributs("label");
        JTextField labelEventFill = new JTextField();
        labelEventFill.setText(te.getLabel());
        labelEventFill.setPreferredSize(new Dimension(200, 25));
        labelFillPan.add(labelEventFill);
        bodyPanel.add(labelFillPan);


         //Type évènement
         JPanel TypeFillPan = createFillAttributs("Type");
         String [] allTypeEvent = TeamEvent.getAllTypeEvent();
         JComboBox<String> typeEventList = new JComboBox<String>(allTypeEvent);
         typeEventList.setEnabled(false);
         typeEventList.setSelectedItem(te.getType());
         typeEventList.setPreferredSize(new Dimension(200, 25));
         TypeFillPan.add(typeEventList);
         bodyPanel.add(TypeFillPan);
        

         //Date Début évènement
         JPanel startingDateFillPan = createFillAttributs("Date DEBUT");
         SpinnerDateModel startingDatemodel = new SpinnerDateModel();
         startingDatemodel.setCalendarField(Calendar.DAY_OF_MONTH);
        JSpinner startingDateSpinner = new JSpinner(startingDatemodel);
        startingDateSpinner.setValue(te.getDateDebut());
        startingDateSpinner.setPreferredSize(new Dimension(200, 25));
         startingDateFillPan.add(startingDateSpinner);
         bodyPanel.add(startingDateFillPan);

          //Date Fin évènement
          JPanel endingDateFillPan = createFillAttributs("Date FIN");
          SpinnerDateModel endingDatemodel = new SpinnerDateModel();
         endingDatemodel.setCalendarField(Calendar.DAY_OF_MONTH);
         JSpinner endingDateSpinner = new JSpinner(endingDatemodel);
         endingDateSpinner.setValue(te.getDateFin());
         endingDateSpinner.setPreferredSize(new Dimension(200, 25));
         endingDateFillPan.add(endingDateSpinner);
          bodyPanel.add(endingDateFillPan);

         

           

            
            JPanel botPanel = new JPanel(new FlowLayout());
            
            JButton submit = new JButton("valider");
            JButton quit = new JButton("quitter");


            submit.addActionListener( e->{

                Timestamp startingDate = new Timestamp(((java.util.Date)startingDateSpinner.getValue()).getTime()); 
                Timestamp endingDate = new Timestamp(((java.util.Date)endingDateSpinner.getValue()).getTime()); 
                String label = labelEventFill.getText();


                if( label.trim().equals("")){
                    JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
                else if(startingDate.compareTo(endingDate) >= 0 ){
                    JOptionPane.showMessageDialog(null, "la date de début doit être strictement antérieur à celle de fin. ", "Erreur", JOptionPane.ERROR_MESSAGE);                  

                }else{
                   
                    TeamEvent editedEvent = new TeamEvent(te.getId(),label ,typeEventList.getSelectedItem().toString(),startingDate,endingDate);
                    HashMap<String,Object> differencies =editedEvent.getDifferencies(te);

                    if(!differencies.isEmpty()){
                    MainInsertClient.updateRequest(te, differencies, "UPDATE_EVENT");
                    Mastermind.getEventsSet().remove(te);
                    Mastermind.getEventsSet().add(editedEvent);
                    gte.fillGrid(Mastermind.getEventsSet());
                    JOptionPane.showMessageDialog(null, "Evènement mis à jour!", "Validation", JOptionPane.INFORMATION_MESSAGE);   
                    }


                    this.dispose();
                    
                }

            });

        botPanel.add(submit);
        botPanel.add(quit);
        this.add(bodyPanel);
        this.add(botPanel, BorderLayout.SOUTH);
        this.pack();
        quit.addActionListener(e -> this.dispose());
        setLocationRelativeTo(fen);
        setVisible(true);




        }


     private JPanel createFillAttributs(String titre){
    JPanel panel = new JPanel();
    panel.setBackground(Color.WHITE);
    panel.setBorder(BorderFactory.createTitledBorder(titre));
    return panel;
  }
}
