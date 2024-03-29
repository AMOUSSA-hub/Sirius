package client.frontend.v2;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.JPanel;

public class GridInfoSquad extends JPanel {

  private GridLayout bodyTabLayout;
  private JFrame fen;
  private JPanel bodyTabPanel;
  private JScrollPane scrollPane;
  private JPanel headTabPanel;

  public GridInfoSquad(String[] titles, JFrame fen) {
    setLayout(new GridLayout(1, 1));
    this.fen = fen;
    headTabPanel = new JPanel(new GridLayout(1, titles.length));
    bodyTabLayout = new GridLayout(0, titles.length);
    bodyTabPanel = new JPanel(bodyTabLayout);
    scrollPane = new JScrollPane(bodyTabPanel);
    scrollPane.setColumnHeaderView(headTabPanel);

    for (String title : titles) {
      CaseGridSquad c = new CaseGridSquad(title, fen);
      c.setBackground(new Color(96, 96, 96));
      headTabPanel.add(c);
    }

    add(scrollPane);
  }
/* A MODIFIER POUR FAIRE EN SORTE QUE CA PRENNE EN ARGUMENT InfosJoueur */
   /**
    *Ajoute une ligne au tableau .
    */
  public void addRow(List<Object> j) {
    bodyTabLayout.setRows(bodyTabLayout.getRows() + 1);

    for (int i = 0; i < j.size() - 1; i++) {
      Object info = j.get(i);
      bodyTabPanel.add(new CaseGridSquad(info.toString(), fen));
    }

    revalidate();
    repaint();

  }
/**
 * Vide le corps du tableau.
 */
  public void unFillGrid(){
    bodyTabLayout.setRows(0);
    bodyTabPanel.removeAll();
    revalidate();
    repaint();


  }

  /**
   * Trie le tableau
   */
  public void sort(List<InfosJoueurs> infosPlayer,boolean isAscendant,String sorting_attribut){

    System.out.println(isAscendant+" "+ sorting_attribut);
    unFillGrid();

    Collections.sort(infosPlayer,new JoueursCompare(sorting_attribut,isAscendant));

    for(InfosJoueurs y: infosPlayer){
      //addRow(y);
      
    }

    
  }
  
}
