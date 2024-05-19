package client.frontend.v2;

import javax.swing.table.DefaultTableModel;

public class StatsTableModel extends DefaultTableModel {

    public StatsTableModel(Object[] o , int a){
        super (o,a);
    } 

   
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;  // Toutes les cellules sont non éditables
        }  
    
    
}
