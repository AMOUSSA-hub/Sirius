import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class LauncherFrame  extends JFrame{


    public LauncherFrame(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.gray);
        setSize(1200,600);

        add(new JLabel(new ImageIcon(new ImageIcon("client-front_V2/images/clubManagerTransparent.png").getImage().getScaledInstance(500,500, Image.SCALE_DEFAULT))),BorderLayout.CENTER);
        JButton enterButton = new JButton("Entrer");
        add(enterButton,BorderLayout.SOUTH);

        enterButton.addActionListener(e -> {this.dispose();new HomeFrame();});
        setLocationRelativeTo(null);
        setVisible(true);



    }
    
}
