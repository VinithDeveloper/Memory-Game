package testproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;

public class MemoryGame extends JFrame {
    private final int ROWS = 4;
    private final int COLS = 5;
    private final int CARD_SIZE = 170;
    private final int IMAGE_SIZE = 170;
    private final int NUM_CARDS = ROWS * COLS;
    private final int NUM_IMAGES = NUM_CARDS / 2;
    private final String IMAGE_DIR = "src/testproject/images/";

    private ArrayList<String> cardNames; // Liste zum Speichern der Namen der Karten
    private ArrayList<ImageIcon> shuffledIcons; // Liste zum Speichern der gemischten Icons
    private JButton[] cardButtons; // Array für die Kartenbuttons
    private int pairsFound; // Anzahl der gefundenen Paare
    private int firstCardIndex = -1; // Index der ersten aufgedeckten Karte
    private int secondCardIndex = -1; // Index der zweiten aufgedeckten Karte
    private int[] playerScores; // Array für die Punktzahlen der Spieler
    private String player1Name; // Name von Spieler 1
    private String player2Name; // Name von Spieler 2
    private int currentPlayer = 1; // Aktueller Spieler
    private JLabel player1ScoreLabel; // Label für die Punktzahl des Spielers 1
    private JLabel player2ScoreLabel; // Label für die Punktzahl des Spielers 2
    private JLabel currentPlayerLabel; // Label für den aktuellen Spieler

    private ImageIcon backImage; // Hintergrundbild für die Rückseite der Karten

    public MemoryGame() {
        cardNames = new ArrayList<>(); // Neue Liste für die Karten-Namen erstellen
        String[] imageNames = { "memoryBildEins.jpg", "memoryBildZwei.jpg", "memoryBildDrei.jpg", "memoryBildVier.jpeg",
                "memoryBildFunf.jpeg", "memoryBildSechs.jpg", "memoryBildSieben.jpg", "memoryBildAcht.jpg",
                "memoryBildNeun.jpg", "memoryBildZehn.jpg" };
        for (String imageName : imageNames) {
            cardNames.add(imageName); // Karten-Namen zur Liste hinzufügen
            cardNames.add(imageName); // Jeden Namen doppelt hinzufügen, um Paare zu erstellen
        }

        shuffledIcons = new ArrayList<>(); // Neue Liste für die gemischten Icons erstellen
        Collections.shuffle(cardNames); // Karten-Namen mischen

        cardButtons = new JButton[NUM_CARDS]; // Array für die Kartenbuttons erstellen
        backImage = new ImageIcon(IMAGE_DIR + "MemoryBackCard.jpg"); // Hintergrundbild für die Rückseite der Karten
        

        for (int i = 0; i < NUM_CARDS; i++) {
            JButton button = new JButton(backImage); // Setze das Hintergrundbild als Standard für alle Kartenbuttons
            button.setPreferredSize(new Dimension(CARD_SIZE, CARD_SIZE));
            button.addActionListener(new CardListener(i));
            cardButtons[i] = button; // Button zum Array hinzufügen
            
        }

        JPanel cardPanel = new JPanel(new GridLayout(ROWS, COLS));
        for (int i = 0; i < NUM_CARDS; i++) {
            cardPanel.add(cardButtons[i]);
        }

        playerScores = new int[2];

        JPanel playerInfoPanel = new JPanel(new GridLayout(1, 3));
        
     
        player1ScoreLabel = new JLabel(player1Name + ": 0 Punkte"); // Label für die Punktzahl des Spielers 1 erstellen
        player1ScoreLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Schriftart und -größe ändern
        player1ScoreLabel.setForeground(Color.BLUE); // Textfarbe ändern
        player1ScoreLabel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2)); // Eine Grenze um das Label hinzufügen
        playerInfoPanel.add(player1ScoreLabel);

        player2ScoreLabel = new JLabel(player2Name + ": 0 Punkte"); // Label für die Punktzahl des Spielers 2 erstellen
        player2ScoreLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Schriftart und -größe ändern
        player2ScoreLabel.setForeground(Color.RED); // Textfarbe ändern
        player2ScoreLabel.setBorder(BorderFactory.createLineBorder(Color.RED, 2)); // Eine Grenze um das Label hinzufügen
        playerInfoPanel.add(player2ScoreLabel);

        currentPlayerLabel = new JLabel("Aktueller Spieler: " + player1Name); // Label für den aktuellen Spieler erstellen
        currentPlayerLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Schriftart und -größe ändern
        currentPlayerLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Eine Grenze um das Label hinzufügen
        playerInfoPanel.add(currentPlayerLabel);

        // Verwenden Sie BorderLayout und fügen Sie die Panels entsprechend hinzu
        setLayout(new BorderLayout());
        add(cardPanel, BorderLayout.CENTER);
        add(playerInfoPanel, BorderLayout.SOUTH);

        // Dialog anzeigen, um die Namen der Spieler einzugeben
        enterPlayerNames();

        pack();
        setTitle("UFC Memory Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        pairsFound = 0;
        setIconImage(Toolkit.getDefaultToolkit().getImage("src/testproject/images/memoryBild.png"));
    }

    private void enterPlayerNames() {
        // Schriftart Sternbach UFC Schrift einbinden
        Font sternbachFont = null;
        Font sternbachItalicFont = null;
        Font sternbachHollowFont = null;
        
        try {
            sternbachFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/testproject/fonts/Sternbach.otf")).deriveFont(Font.PLAIN, 20);
            sternbachItalicFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/testproject/fonts/Sternbach-Italic.otf")).deriveFont(Font.PLAIN, 20);
            sternbachHollowFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/testproject/fonts/Sternbach-Hollow.otf")).deriveFont(Font.PLAIN, 20);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        
        //Eingaben
        JDialog dialog = new JDialog(this, "Spieler-Namen eingeben", true);
        dialog.setSize(400, 180);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("UFC Memory Game");
        title.setFont(sternbachFont.deriveFont(30f)); // UFC Font
        title.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        JLabel player1Label = new JLabel("Spieler 1:");
        player1Label.setFont(sternbachItalicFont.deriveFont(20f)); // UFC Font mit Italic
        formPanel.add(player1Label);

        JTextField player1Field = new JTextField();
        player1Field.setFont(sternbachHollowFont.deriveFont(16f)); // UFC Font mit Hollow
        formPanel.add(player1Field);

        JLabel player2Label = new JLabel("Spieler 2:");
        player2Label.setFont(sternbachItalicFont.deriveFont(20f)); // UFC Font mit Italic
        formPanel.add(player2Label);

        JTextField player2Field = new JTextField();
        player2Field.setFont(sternbachHollowFont.deriveFont(16f)); // UFC Font mit Hollow
        formPanel.add(player2Field);

        panel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton startButton = new JButton("Spiel starten");
        startButton.setFont(sternbachFont.deriveFont(16f)); // UFC Font mit Hollow
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                player1Name = player1Field.getText();
                player2Name = player2Field.getText();
                
                buttonPanel.add(startButton);
                panel.add(buttonPanel, BorderLayout.SOUTH);

                dialog.add(panel);
                dialog.setVisible(true);

                if (!player1Name.isEmpty() && !player2Name.isEmpty()) {
                    dialog.dispose();
                    currentPlayerLabel.setText("Aktueller Spieler: " + player1Name);
                    player1ScoreLabel.setText(player1Name + ": 0 Punkte");
                    player2ScoreLabel.setText(player2Name + ": 0 Punkte");
                } else {
                    JOptionPane.showMessageDialog(dialog, "Bitte geben Sie die Namen für beide Spieler ein.");
                }
            }
        });

        buttonPanel.add(startButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showCard(int index) {
        String imageName = cardNames.get(index);
        String imagePath = IMAGE_DIR + imageName;
        ImageIcon icon = new ImageIcon(imagePath);

        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            Image scaledImage = image.getScaledInstance(IMAGE_SIZE, IMAGE_SIZE, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            cardButtons[index].setIcon(scaledIcon);
            cardButtons[index].setDisabledIcon(scaledIcon);	
        } catch (IOException e) {
            e.printStackTrace();
        }

        cardButtons[index].setEnabled(false);
        currentPlayerLabel.setText("Aktueller Spieler: " + getPlayerName(currentPlayer));
    }

    private void hideCard(int index) {
        String imagePath = IMAGE_DIR + "MemoryBackCard.jpg"; 
        ImageIcon backgroundIcon = new ImageIcon(imagePath);
        Image backgroundImage = backgroundIcon.getImage().getScaledInstance(CARD_SIZE, CARD_SIZE, Image.SCALE_SMOOTH); //bild ist skaliert bei hide
        ImageIcon scaledBackgroundIcon = new ImageIcon(backgroundImage);
        cardButtons[index].setIcon(scaledBackgroundIcon); // Icon des Kartenbuttons auf das skalierte Hintergrundbild setzen
        cardButtons[index].setEnabled(true); // Kartenbutton aktivieren, um ihn klickbar zu machen
    }


    private void checkMatch() {
        if (cardNames.get(firstCardIndex).equals(cardNames.get(secondCardIndex)) && firstCardIndex != secondCardIndex) {
            pairsFound++; // Anzahl der gefundenen Paare erhöhen
            int currentPlayerScore = playerScores[currentPlayer - 1]; // Punktzahl des aktuellen Spielers abrufen

            currentPlayerScore++; // Punktzahl des aktuellen Spielers erhöhen
            playerScores[currentPlayer - 1] = currentPlayerScore; // Aktualisierte Punktzahl speichern
            player1ScoreLabel.setText(player1Name + ": " + playerScores[0] + " Punkte"); // Punkteanzeigen aktualisieren für Spieler 1
            player2ScoreLabel.setText(player2Name + ": " + playerScores[1] + " Punkte"); // Punkteanzeigen aktualisieren für Spieler 2

            cardButtons[firstCardIndex].setEnabled(false); // Erstes Kartenpaar deaktivieren
            cardButtons[secondCardIndex].setEnabled(false); // Zweites Kartenpaar deaktivieren
        } else {
            hideCard(firstCardIndex); // Erstes Kartenpaar verstecken
            hideCard(secondCardIndex); // Zweites Kartenpaar verstecken

            currentPlayer = (currentPlayer == 1) ? 2 : 1; // Spieler wechseln
            currentPlayerLabel.setText("Aktueller Spieler: " + getPlayerName(currentPlayer)); // Anzeigen des aktuellen Spielers
        }

        if (pairsFound == NUM_IMAGES) {
            // Überprüfen, ob alle Paare gefunden wurden
            if (playerScores[0] > playerScores[1]) {
                JOptionPane.showMessageDialog(this, getPlayerName(1) + " gewinnt mit " + playerScores[0] + " Punkten!");
            } else if (playerScores[1] > playerScores[0]) {
                JOptionPane.showMessageDialog(this, getPlayerName(2) + " gewinnt mit " + playerScores[1] + " Punkten!");
            } else {
                JOptionPane.showMessageDialog(this, "Unentschieden! Beide Spieler haben " + playerScores[0] + " Punkte erreicht!");
            }

            System.exit(0);
        }

        firstCardIndex = -1; // Zurücksetzen des ersten Kartenindex
        secondCardIndex = -1; // Zurücksetzen des zweiten Kartenindex
    }

    private String getPlayerName(int playerNumber) {
        return (playerNumber == 1) ? player1Name : player2Name;
    }

    private class CardListener implements ActionListener {
        private int index;

        public CardListener(int index) {
            this.index = index;
        }

        public void actionPerformed(ActionEvent event) {
            if (cardButtons[index].getIcon() == backImage) {
                if (firstCardIndex == -1) {
                    firstCardIndex = index;
                    showCard(firstCardIndex);
                } else if (secondCardIndex == -1 && index != firstCardIndex) {
                    secondCardIndex = index;
                    showCard(secondCardIndex);
                    javax.swing.Timer timer = new javax.swing.Timer(1000, new ActionListener() {
                        public void actionPerformed(ActionEvent event) {
                            checkMatch();
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MemoryGame game = new MemoryGame();
        });
    }
}
