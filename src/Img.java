import java.awt.Image;

import javax.swing.ImageIcon;

public class Img extends ImageIcon  {

    public static final int WIDTH = Contenu.WIDTH;
    public static final int HEIGHT = Contenu.HEIGHT;

     int largeurResized; 
     int hauteurResized;


    Img() {
        this.largeurResized = 0;
        this.hauteurResized = 0;
    }

     public ImageIcon resizeImage(ImageIcon img) {
        if (img.getIconWidth() > WIDTH || img.getIconHeight() > HEIGHT) {
        double facteurWidth = 1536.0/WIDTH; //Etant donne que tout est fait selon ma resolution, on cree des facteurs visant a redimensionner la taille de l'image selon l'ecran de l'utilisateur ici pour la largeur
        double facteurHeight = 960.0/HEIGHT; //Idem mais pour la hauteur 
        //System.out.println(facteurWidth);
        int newLargeur = (int)(img.getIconWidth()/facteurWidth);
        //System.out.println(newLargeur);
        int newHauteur = (int)(img.getIconHeight()/facteurHeight);
        Image im = img.getImage().getScaledInstance(newLargeur,newHauteur,Image.SCALE_SMOOTH);
        img = new ImageIcon(im);
        this.largeurResized = newLargeur;
        this.hauteurResized = newHauteur;
        return img;
        }
        return img;
    }
}