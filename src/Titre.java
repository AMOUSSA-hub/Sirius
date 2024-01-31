import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextPane;

public class Titre extends JTextPane {


    Font font = new Font("Audiowide",Font.BOLD, 115);
    static final Color FOND = Contenu.bg;
    static final int WIDTH = Contenu.WIDTH ;
    static final int HEIGHT = 0;

    
    Titre(String titre) {
            
        //this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setFocusable(false);
        this.setFont(font);
        this.setBackground(FOND);
        this.setForeground(Color.WHITE);
        this.setText(titre);
        
        //StyledDocument doc = this.getStyledDocument();
        //SimpleAttributeSet center = new SimpleAttributeSet();
        //StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        //doc.setParagraphAttributes(0, doc.getLength(), center, false);
    }


}
