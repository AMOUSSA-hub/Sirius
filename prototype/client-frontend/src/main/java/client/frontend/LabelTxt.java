package client.frontend;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class LabelTxt extends JLabel {
    
    static Font font = new Font("Audiowide",Font.BOLD, 18);

    LabelTxt() { //Initialise la police et la couleur 
        this.setFont(font);
        this.setForeground(Color.WHITE);
        
    }

    LabelTxt(String txt) {    //Initialise le texte en mettant la couleur et la police 
        this();
        this.setText(txt);
       
    }

    LabelTxt(String txt,int taillePolice) {  //Initialise un texte avec une taille de police avec la couleur et la police 
        this(txt);
        this.setFont(new Font(font.getFontName(), Font.BOLD, taillePolice));
        
    }

    LabelTxt(int taillePolice) {
        this();
        this.setFont(new Font(font.getName(), Font.BOLD, taillePolice));
    }
}
