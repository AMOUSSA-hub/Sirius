package client.frontend.v2;

import java.awt.GridLayout;
import java.util.Set;

import javax.swing.JPanel;

import edu.ezip.ing1.pds.business.dto.TeamEvent;

public class GridTeamEvent extends JPanel {



    public GridTeamEvent( Set<TeamEvent> list){

        setLayout(new GridLayout(1,7));

        System.out.println(list.size());

        for (TeamEvent teamEvent : list) {
           this.addCaseEvent(teamEvent);
        }
            
        
        

    }



    public void addCaseEvent(TeamEvent te){  
        this.add(new CaseEvent(te)) ;
      }

    
    public void removeCaseEvent( CaseEvent ca){
        remove(ca);
        this.revalidate();
        this.repaint();
    }





    
}
