package client.frontend.v2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.util.List;

import javax.swing.JPanel;

public class GridSquad extends JPanel {
    private GridLayout gl;
    private JFrame fen;
    public GridSquad(String[] titles, JFrame fen){
        gl = new GridLayout(1,titles.length);
        setLayout(gl);
        this.fen =fen;


        for (int i = 0; i <=titles.length-1; i++) {
            CaseGridSquad c  = new CaseGridSquad(titles[i],fen);
            c.setBackground(new Color(96,96,96));
            this.add(c);   
        }

        


    }

    public void addRow(List<Object> j){
        gl.setRows(gl.getRows()+1);

        for (int i = 0; i < j.size()-1 ; i++) {
            Object info = j.get(i); 
            add(new CaseGridSquad(info.toString(),fen));
        }
        revalidate();
        repaint();

    }




    
}
