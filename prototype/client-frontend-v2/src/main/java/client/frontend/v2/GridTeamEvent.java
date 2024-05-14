package client.frontend.v2;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Set;

import javax.swing.JPanel;

import edu.ezip.ing1.pds.business.dto.TeamEvent;

public class GridTeamEvent extends JPanel {

    GridLayout gl;



    public GridTeamEvent( Set<TeamEvent> list){

        gl = new GridLayout(1,1, 20,20);
        setLayout(gl);
        this.setBackground(Color.GRAY);
        System.out.println(list.size());

        for (TeamEvent teamEvent : list) {
           this.addCaseEvent(teamEvent);
        }
            
        
        

    }



    public void addCaseEvent(TeamEvent te){  
        gl.setColumns(gl.getColumns()+1);
        this.add(new CaseEvent(te)) ;
        this.revalidate();
        this.repaint();
      }

    
    public void removeCaseEvent( CaseEvent ca){
        gl.setColumns(gl.getColumns()-1);
        remove(ca);
        this.revalidate();
        this.repaint();
    }





    
}
