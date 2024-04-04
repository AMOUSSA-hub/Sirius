package client.frontend.v2;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.JPanel;

public class GridInfoSquad extends JScrollPane {

  private GridLayout bodyTabLayout;
  private JFrame fen;
  private JPanel bodyTabPanel;
  private JScrollPane scrollPane;
  private JPanel headTabPanel;

  public GridInfoSquad(String[] titles, JFrame fen) {
    super();
    bodyTabLayout = new GridLayout(0, titles.length);
    bodyTabPanel = new JPanel(bodyTabLayout);
    this.setViewportView(bodyTabPanel);
    this.fen = fen;
    headTabPanel = new JPanel(new GridLayout(1, titles.length));
    
    this.setColumnHeaderView(headTabPanel);

    for (String title : titles) {
      CaseGridSquad c = new CaseGridSquad(title, fen);
      c.setBackground(new Color(96, 96, 96));
      headTabPanel.add(c);
    }
  }
/* A MODIFIER POUR FAIRE EN SORTE QUE CA PRENNE EN ARGUMENT InfosJoueur */
   /**
    *Ajoute une ligne au tableau .
    */
  public void addRow(InfosJoueurs j) {
    bodyTabLayout.setRows(bodyTabLayout.getRows() + 1);
    Image im = j.getImagePhoto();
    if (im != null) bodyTabPanel.add(new CaseGridSquad(im, fen));
    else bodyTabPanel.add(new CaseGridSquad(" ", fen));
    for (String info : j.getTabInfo()) {
     bodyTabPanel.add(new CaseGridSquad(info, fen));
    }

    JPanel panel = new JPanel();
    panel.setBackground(Color.GREEN);
    panel.add(j.getBoutonModif());
    add(panel);
        panel.setOpaque(true);
        panel.setPreferredSize(new Dimension(fen.getWidth()/13,75));
        panel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        panel.setBackground(Color.GRAY);
        panel.setForeground(Color.white);
        //panel.setHorizontalAlignment(CENTER);
        //this.fen =fen;


    bodyTabPanel.add(panel,fen);
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
    unFillGrid();
    Collections.sort(infosPlayer,new JoueursCompare(sorting_attribut,isAscendant));
    for(InfosJoueurs y: infosPlayer){
      addRow(y);
      
    }

    revalidate();
    repaint();

    
  }


  

  
}
