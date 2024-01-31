import java.awt.Font;

import javax.swing.JButton;

public class BoutonMenu extends JButton {
    
    Font font = new Font("Audiowide",Font.BOLD, 15);

    BoutonMenu(String titre) {
        this.setText(titre);
        this.setBackground(Contenu.bg);
        this.setVisible(true);
        this.setLayout(null);
        this.setForeground(Contenu.yellow);
        this.setFont(font);
        
    }
}
