package testproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MemoryGame extends JFrame {
    private final int ROWS = 4;
    private final int COLS = 5;
    private final int CARD_SIZE = 100;
    private final int IMAGE_SIZE = 80;
    private final int NUM_CARDS = ROWS * COLS;
    private final int NUM_IMAGES = NUM_CARDS / 2;
    private final String IMAGE_DIR = "C:\\Users\\Vinith\\OneDrive\\Desktop\\testproject\\src\\testproject\\images\\";

    private ArrayList<String> cardNames; // Liste zum Speichern der Namen der Karten
    private ArrayList<ImageIcon> shuffledIcons; // Liste zum Speichern der gemischten Icons
    private JButton[] cardButtons; // Array für die Kartenbuttons
    private int pairsFound; // Anzahl der gefundenen Paare
    private int firstCardIndex = -1; // Index der ersten aufgedeckten Karte
    private int secondCardIndex = -1; // Index der zweiten aufgedeckten Karte

    public MemoryGame() {
        cardNames = new ArrayList<>(); // Neue Liste für die Karten-Namen erstellen
        String[] imageNames = {"ananas.jpg", "apfel.jpg", "bananen.jpg", "birne.jpg", "erdbeere.jpg", "kirsche.jpg", "orange.jpg", "trauben.jpg", "wassermelone.jpg", "zitrone.jpg"};
        for (String imageName : imageNames) {
            cardNames.add(imageName); // Karten-Namen zur Liste hinzufügen
            cardNames.add(imageName); // Jeden Namen doppelt hinzufügen, um Paare zu erstellen
        }

        shuffledIcons = new ArrayList<>(); // Neue Liste für die gemischten Icons erstellen
        Collections.shuffle(cardNames); // Karten-Namen mischen

        cardButtons = new JButton[NUM_CARDS]; // Array für die Kartenbuttons erstellen
        for (int i = 0; i < NUM_CARDS; i++) {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(CARD_SIZE, CARD_SIZE));
            button.addActionListener(new CardListener(i));
            cardButtons[i] = button; // Button zum Array hinzufügen
        }

        JPanel cardPanel = new JPanel(new GridLayout(ROWS, COLS)); // JPanel mit Rasterlayout erstellen
        for (int i = 0; i < NUM_CARDS; i++) {
            cardPanel.add(cardButtons[i]); // Kartenbuttons zum JPanel hinzufügen
        }

        add(cardPanel); // JPanel zum JFrame hinzufügen
        pack();
        setTitle("Memory Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        pairsFound = 0; // Anfangszustand: keine Paare gefunden
    }

    private void showCard(int index) {
        String imageName = cardNames.get(index); // Namen der Karte anhand des Index abrufen
        String imagePath = IMAGE_DIR + imageName; // Pfad zum Bild erstellen
        ImageIcon icon = new ImageIcon(imagePath); // ImageIcon mit dem Bild erstellen
        Image image = icon.getImage().getScaledInstance(IMAGE_SIZE, IMAGE_SIZE, Image.SCALE_SMOOTH); // Bild skalieren
        cardButtons[index].setIcon(new ImageIcon(image)); // Icon zum Kartenbutton hinzufügen
        cardButtons[index].setEnabled(false); // Kartenbutton deaktivieren, um ihn nicht erneut zu klicken
    }

    private void hideCard(int index) {
        cardButtons[index].setIcon(null); // Icon des Kartenbuttons entfernen
        cardButtons[index].setEnabled(true); // Kartenbutton aktivieren, um ihn klickbar zu machen
    }

    private void checkMatch() {
        if (cardNames.get(firstCardIndex).equals(cardNames.get(secondCardIndex)) && firstCardIndex != secondCardIndex) {
            // Überprüfen, ob die Namen der Karten übereinstimmen und sie nicht dieselbe Karte sind
            pairsFound++; // Anzahl der gefundenen Paare erhöhen
            cardButtons[firstCardIndex].setEnabled(false); // Erstes Kartenpaar deaktivieren
            cardButtons[secondCardIndex].setEnabled(false); // Zweites Kartenpaar deaktivieren
        } else {
            hideCard(firstCardIndex); // Erstes Kartenpaar verstecken
            hideCard(secondCardIndex); // Zweites Kartenpaar verstecken
        }
        if (pairsFound == NUM_IMAGES) {
            // Überprüfen, ob alle Paare gefunden wurden
            JOptionPane.showMessageDialog(this, "Congratulations, you won!"); // Gewinnmeldung anzeigen
            System.exit(0); // Programm beenden
        }
        firstCardIndex = -1; // Zurücksetzen des ersten Kartenindex
        secondCardIndex = -1; // Zurücksetzen des zweiten Kartenindex
    }

    private class CardListener implements ActionListener {
        private int index;

        public CardListener(int index) {
            this.index = index;
        }

        public void actionPerformed(ActionEvent event) {
            if (cardButtons[index].getIcon() == null) {
                // Überprüfen, ob die Karte bereits aufgedeckt ist
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

