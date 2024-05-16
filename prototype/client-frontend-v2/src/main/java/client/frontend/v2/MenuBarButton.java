package client.frontend.v2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MenuBarButton extends JButton {


    public MenuBarButton(String label){
        super(label);
        setPreferredSize(new Dimension(200,80));
        this.setBackground(Color.black );
        this.setForeground(Color.white);
        setFont(new Font(null,Font.BOLD,15));
    }

    public MenuBarButton(ImageIcon icon){
        super(icon);
        setPreferredSize(new Dimension(200,80));
        this.setBackground(Color.black );
        this.setForeground(Color.white);
        setFont(new Font(null,Font.BOLD,15));
    }
    
}
