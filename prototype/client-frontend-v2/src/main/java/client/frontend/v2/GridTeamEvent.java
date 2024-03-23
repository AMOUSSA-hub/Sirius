package client.frontend.v2;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class GridTeamEvent extends JPanel {



    public GridTeamEvent(){

        setLayout(new GridLayout(1,7));

        for (int i = 0; i < 7; i++) {
            this.add(new CaseEvent());
        }
        

    }



    public void addCaseEvent(){

    }



    
}
