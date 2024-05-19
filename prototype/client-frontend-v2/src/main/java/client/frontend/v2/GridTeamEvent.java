package client.frontend.v2;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.List;
import java.io.Serial;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JPanel;

import edu.ezip.ing1.pds.business.dto.TeamEvent;

public class GridTeamEvent extends JPanel {

    GridLayout gl;




    public GridTeamEvent( Set<TeamEvent> list){

        gl = new GridLayout(1,1, 20,20);
        setLayout(gl);
        this.setBackground(Color.GRAY);
        
        fillGrid(list);
        
        

    }

    public void fillGrid( Set<TeamEvent> list){

        removeAll();
           TreeSet<TeamEvent> sortedSet = new TreeSet<>(new Comparator<TeamEvent>() {
            @Override
            public int compare(TeamEvent event1, TeamEvent event2) {
                return event1.getDateDebut().compareTo(event2.getDateDebut());
            }
        });
        sortedSet.addAll(list);
        for (TeamEvent teamEvent : list) {
            this.addCaseEvent(teamEvent);
         }
             
    }



    public void addCaseEvent(TeamEvent te){  
        gl.setColumns(gl.getColumns()+1);
        this.add(new CaseEvent(te,this));
        this.revalidate();
        this.repaint();
      }

    
    public void removeCaseEvent( CaseEvent ca){
        gl.setColumns(gl.getColumns()-1);
        remove(ca);
        this.revalidate();
        this.repaint();
    }




    @Override
    public void removeAll() {
        // TODO Auto-generated method stub
        super.removeAll();
        gl.setColumns(1);
        gl.setRows(0);
        this.repaint();
        this.revalidate();

    }





    
}
