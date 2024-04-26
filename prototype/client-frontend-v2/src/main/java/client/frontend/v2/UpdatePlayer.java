package client.frontend.v2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class UpdatePlayer extends AddPlayer {

    InfosJoueurs player;
    public UpdatePlayer(JFrame parent, String title, boolean modal,InfosJoueurs j) {
        super(parent, title, modal,null);
        player = j;
        this.initComponent();
        this.setVisible(true);

/*         this.nom.setText(player.getNom());
        this.prenom.setText(player.getPrenom());
        this.salaire.setText(player.getSalaire() +"");
        this.tailleSpinner.setValue(j.getTaille());
        this.poidsSpinner.setValue(j.getPoids()); */
        System.out.println(nom.getText() + " + " + player.getNom());
    }
    private void initComponent(){


        //Le nom
        JPanel panNom = createPanelAttributs("Nom du Joueur", 200, 60);
        nom = new JTextField(player.getNom());
        nom.setPreferredSize(new Dimension(100, 25));
        nomLabel = new JLabel("Nom :");
        panNom.add(nomLabel);
        panNom.add(nom);
        
    
    
        //Le prenom 
        JPanel panPrenom = createPanelAttributs("Prenom du Joueur", 200, 60);
        prenom = new JTextField(player.getPrenom());
        prenom.setPreferredSize(new Dimension(100, 25));
        prenomLabel = new JLabel("Prenom :");
        panPrenom.add(prenomLabel);
        panPrenom.add(prenom);
    
        //Le pied fort 
        JPanel panPied = createPanelAttributs("Pied fort du Joueur", 100, 60);
        pied = new JComboBox<String>();
        pied.addItem("G");
        pied.addItem("D");
        pied.setSelectedItem(player.getPied());
        panPied.add(pied);
    
        //Le numero de Maillot 
        JPanel panNumero = createPanelAttributs("Numero", 100, 60);
        panNumero.add(numeroSpinner);
        setOnlyChiffres(numeroSpinner);
    
    
        //L'âge 
        JPanel panAge = createPanelAttributs("Age", 220, 60);
        SpinnerDateModel modelNaissance = new SpinnerDateModel();
        modelNaissance.setCalendarField(Calendar.DAY_OF_MONTH);
        dateNaissanceSpinner = new JSpinner(modelNaissance);
        panAge.add(dateNaissanceSpinner);
    
        //La taille
        JPanel panTaille = createPanelAttributs("La taille du Joueur ", 220, 60);
        tailleLabel = new JLabel(" cm");
        panTaille.add(tailleSpinner);
        panTaille.add(tailleLabel);
        setOnlyChiffres(tailleSpinner);
    
        //Le poste
        JPanel panPoste = createPanelAttributs("Poste du Joueur", 220, 60);
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
        poste.setSelectedItem(player.getPosition());
        posteLabel = new JLabel("Poste");
        panPoste.add(posteLabel);
        panPoste.add(poste);
    
        //Le poids 
        JPanel panPoids = createPanelAttributs("Le poids du Joueur", 220, 60);
        poidsLabel = new JLabel(" Kg");
        panPoids.add(poidsSpinner);
        panPoids.add(poidsLabel);
        setOnlyChiffres(poidsSpinner);
        
    
        //Le contrat
        JPanel panContrat = createPanelAttributs("Date Fin de Contrat", 220, 60);
        SpinnerDateModel modelContrat = new SpinnerDateModel();
        modelContrat.setCalendarField(Calendar.DAY_OF_MONTH);
        dateContratSpinner = new JSpinner(modelContrat);
        panContrat.add(dateContratSpinner);
    
    
        //Le salaire
        JPanel panSalaire = createPanelAttributs("Salaire Mensuel (en euros)", 220, 60);
        salaire = new JTextField("0");
        salaire.setPreferredSize(new Dimension(150, 25));
        panSalaire.add(salaire);
        salaire.addKeyListener(new KeyListener() {
    
          @Override
          public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                e.consume();  // ignore event
            }
          }
    
          @Override
          public void keyPressed(KeyEvent e) {
    
          }
    
          @Override
          public void keyReleased(KeyEvent e) {
    
          }
          
        });
        
        //La nationalite 
        JPanel panNationalite = createPanelAttributs("La nationalite", 220, 60);
        nationalite = new JTextField();
        nationalite.setPreferredSize(new Dimension(100,25));
        panNationalite.add(nationalite);
    
    
        Bouton imgBouton = new Bouton(200,200,"Add Player",Color.WHITE,Color.BLACK);
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
                File imageFile = new File(imageJoueur.toString());
                System.out.println(imageJoueur.toString());
                
                byte[] imageData = new byte[(int) imageFile.length()];
                try (FileInputStream fis = new FileInputStream(imageFile)) {
                    fis.read(imageData);
                } catch (IOException e23) {
                    System.err.println(e23);
                }
                imageJoueurbyte = imageData;
                System.out.println(imageJoueurbyte.toString()); 
                //BufferedImage bf = (BufferedImage)imageJoueur.getImage();
                //String sourcePath = "chemin/vers/image/source.jpg";
            
                // Chemin du fichier de destination
                //String destinationPath = "chemin/vers/image/destination.jpg";
        
    /*             try {
                    // Charger l'image depuis le fichier source
                    //BufferedImage originalImage = ImageIO.read(new File(sourcePath));
        
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
                } */
            
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
        content.add(panPoids);
        content.add(panContrat);
        content.add(panSalaire);
        content.add(panNationalite);
        JPanel control = new JPanel();
        JButton okBouton = new JButton("OK");
    
        okBouton.addActionListener((e) -> this.dispose());
    
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
