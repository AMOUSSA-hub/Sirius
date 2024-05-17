package client.frontend.v2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Accueil extends JPanel {

    private JLabel timeLabel;
    private JLabel messageLabel;
    private BufferedImage logoImage;

    public Accueil(JFrame frame) {
        // Configuration du layout en BorderLayout
        setLayout(new BorderLayout());

        try {
            // Chargement de l'image du club et redimensionnement
            logoImage = ImageIO.read(getClass().getResourceAsStream("/Moissy.png"));
            logoImage = resizeImage(logoImage, 200, 200); // Redimensionner l'image
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Création du label pour afficher l'heure
        timeLabel = new JLabel("", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 36));

        // Création du label pour afficher le message personnalisé
        messageLabel = new JLabel("Bienvenue au Moissy FC", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        // Panel pour afficher l'image avec une bordure
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(new JLabel(new ImageIcon(logoImage)), BorderLayout.CENTER);
        imagePanel.add(timeLabel, BorderLayout.SOUTH);

        // Panel pour afficher le message et l'heure
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(imagePanel, BorderLayout.CENTER);
        centerPanel.add(messageLabel, BorderLayout.SOUTH);

        // Ajout du panel central à la fenêtre
        add(centerPanel, BorderLayout.CENTER);

        // Mise à jour de l'heure initiale
        updateTime();

        // Planification de la mise à jour de l'heure toutes les secondes
        Timer timer = new Timer(1000, e -> updateTime());
        timer.start();

        frame.add(this);
    }

    private void updateTime() {
        // Obtenir l'heure actuelle
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentTime = sdf.format(new Date());

        // Mettre à jour le texte du label avec l'heure actuelle
        timeLabel.setText("<html><center>" + currentTime + "</center></html>");
    }

    // Méthode pour redimensionner une image
    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, width, height, null);
        g2d.dispose();
        return resizedImage;
    }
}
