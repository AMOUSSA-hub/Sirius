import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Files;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AddPlayer extends JDialog {
  ImageIcon imageJoueur;
  JLabel nomLabel,prenomLabel, posteLabel, tailleLabel,taille2Label;
 
   JSpinner agSpinner = new JSpinner(new SpinnerNumberModel(16, 16, 50, 1));
   JSpinner tailleSpinner = new JSpinner(new SpinnerNumberModel(160, 140, 220, 1));
   JSpinner numeroSpinner = new JSpinner(new SpinnerNumberModel(1,1,99,1));
   JComboBox<String> pied, poste;
 JTextField nom, prenom, taille;

  public AddPlayer(JFrame parent, String title, boolean modal){
    super(parent, title, modal);
    this.setSize(Contenu.WIDTH*2/5, Contenu.HEIGHT/3);
    this.setLocationRelativeTo(null);
    this.setResizable(false);
    this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    this.initComponent();
  }

  public void showAddPlayer(){
    this.setVisible(true);      
    //return this.zInfo;      
  }

  private void initComponent(){


    //Le nom
    JPanel panNom = new JPanel();
    panNom.setBackground(Color.white);
    panNom.setPreferredSize(new Dimension(200, 60));
    nom = new JTextField();
    nom.setPreferredSize(new Dimension(100, 25));
    panNom.setBorder(BorderFactory.createTitledBorder("Nom du Joueur"));
    nomLabel = new JLabel("Nom :");
    panNom.add(nomLabel);
    panNom.add(nom);
    


    //Le prenom 
    JPanel panPrenom = new JPanel();
    panPrenom.setBackground(Color.white);
    panPrenom.setPreferredSize(new Dimension(200, 60));
    prenom = new JTextField();
    prenom.setPreferredSize(new Dimension(100, 25));
    panPrenom.setBorder(BorderFactory.createTitledBorder("Prenom du Joueur"));
    prenomLabel = new JLabel("Prenom :");
    panPrenom.add(prenomLabel);
    panPrenom.add(prenom);

    //Le pied fort 
    JPanel panPied = new JPanel();
    panPied.setBackground(Color.white);
    panPied.setPreferredSize(new Dimension(100, 60));
    panPied.setBorder(BorderFactory.createTitledBorder("Pied Fort"));
    pied = new JComboBox<String>();
    pied.addItem("G");
    pied.addItem("D");
    panPied.add(pied);

    //Le numero de Maillot 
    JPanel panNumero = new JPanel();
    panNumero.setBackground(Color.WHITE);
    panNumero.setBorder(BorderFactory.createTitledBorder("Numero"));
    panNumero.setPreferredSize(new Dimension(100, 60));
    panNumero.add(numeroSpinner);

    //L'âge 
    JPanel panAge = new JPanel();
    panAge.setBackground(Color.white);
    panAge.setBorder(BorderFactory.createTitledBorder("Age"));
    panAge.setPreferredSize(new Dimension(100, 60));
    panAge.add(agSpinner);

    //La taille
    JPanel panTaille = new JPanel();
    panTaille.setBackground(Color.white);
    panTaille.setPreferredSize(new Dimension(220, 60));
    panTaille.setBorder(BorderFactory.createTitledBorder("Taille du Joueur"));
    tailleLabel = new JLabel("Taille : ");
    taille2Label = new JLabel(" cm");
    panTaille.add(tailleSpinner);
    panTaille.add(taille2Label);

    //Le poste
    JPanel panPoste = new JPanel();
    panPoste.setBackground(Color.white);
    panPoste.setPreferredSize(new Dimension(220, 60));
    panPoste.setBorder(BorderFactory.createTitledBorder("Poste du Joueur  "));
    poste = new JComboBox<String>();
    poste.addItem("G");
    poste.addItem("DG");
    poste.addItem("DC");
    poste.addItem("DD");
    poste.addItem("MDC");
    poste.addItem("MC");
    poste.addItem("MOC");
    poste.addItem("MD");
    poste.addItem("MG");
    poste.addItem("AD");
    poste.addItem("AG");
    poste.addItem("BU");

    posteLabel = new JLabel("Poste");
    panPoste.add(posteLabel);
    panPoste.add(poste);

    Bouton imgBouton = new Bouton(100,100,"Add Player",Color.WHITE,Color.BLACK);
    JPanel img = new JPanel();
    img.add(imgBouton);

    imgBouton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG images ", "jpg","png");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            imageJoueur = new ImageIcon(chooser.getSelectedFile().getAbsolutePath());
            imageJoueur.setImage(imageJoueur.getImage().getScaledInstance(imgBouton.width, imgBouton.height, java.awt.Image.SCALE_SMOOTH));
            imgBouton.setIcon(imageJoueur);
            System.out.println(imageJoueur.toString());
            BufferedImage bf = (BufferedImage)imageJoueur.getImage();
            String sourcePath = "chemin/vers/image/source.jpg";
        
            // Chemin du fichier de destination
            String destinationPath = "chemin/vers/image/destination.jpg";
    
            try {
                // Charger l'image depuis le fichier source
                BufferedImage originalImage = ImageIO.read(new File(sourcePath));
    
                // Créer une nouvelle image avec les mêmes données
                BufferedImage copiedImage = new BufferedImage(
                    originalImage.getWidth(),
                    originalImage.getHeight(),
                    originalImage.getType()
                );
    
                // Copier les données de l'image originale dans la nouvelle image
                copiedImage.getGraphics().drawImage(originalImage, 0, 0, null);
    
                // Enregistrer la nouvelle image dans le fichier de destination
                ImageIO.write(copiedImage, "jpg", new File(destinationPath));
    
                System.out.println("L'image a été copiée avec succès.");
    
            } catch (IOException e8) {
                System.err.println(e8);
            }
        
        }
        else {
          JOptionPane.showMessageDialog(null,"Pas d'image selectionnée","Erreur",JOptionPane.ERROR_MESSAGE);
        }
    }
      });

    JPanel content = new JPanel();
    content.setBackground(Color.white);
    content.add(img);
    content.add(panNom);
    content.add(panPrenom);

    content.add(panAge);
    content.add(panNumero);
    content.add(panTaille);
    content.add(panPoste);
    content.add(panPied);
    JPanel control = new JPanel();
    JButton okBouton = new JButton("OK");

    okBouton.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {        
        //zInfo = new AddPlayerInfo(nom.getText(), (String)sexe.getSelectedItem(), getAge(), (String)poste.getSelectedItem() ,getTaille());
        setVisible(false);
      }      
    });

    JButton cancelBouton = new JButton("Annuler");
    cancelBouton.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {
        setVisible(false);
      }      
    });

    control.add(okBouton);
    control.add(cancelBouton);


    this.getContentPane().add(content, BorderLayout.CENTER);
    this.getContentPane().add(control, BorderLayout.SOUTH);
  }  
}