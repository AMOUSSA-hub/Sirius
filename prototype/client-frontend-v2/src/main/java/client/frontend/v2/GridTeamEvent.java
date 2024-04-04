package client.frontend.v2;

import java.awt.GridLayout;
import java.util.Set;

import javax.swing.JPanel;

import edu.ezip.ing1.pds.business.dto.TeamEvent;

public class GridTeamEvent extends JPanel {



    public GridTeamEvent( Set<TeamEvent> list){

        setLayout(new GridLayout(1,7));

        for (TeamEvent teamEvent : list) {
           add(new CaseEvent(teamEvent)) ;
        }
            
        
        

    }



    public void addCaseEvent(){

    }



    
}
