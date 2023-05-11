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

    private ArrayList<String> cardNames;
    private ArrayList<ImageIcon> shuffledIcons;
    private JButton[] cardButtons;
    private int pairsFound;
    private int firstCardIndex = -1;
    private int secondCardIndex = -1;
    private int[] playerScores;
    private int currentPlayer = 0;

    public MemoryGame() {
        cardNames = new ArrayList<>();
        String[] imageNames = {"ananas.jpg", "apfel.jpg", "bananen.jpg", "birne.jpg", "erdbeere.jpg", "kirsche.jpg", "orange.jpg", "trauben.jpg", "wassermelone.jpg", "zitrone.jpg"};
        for (String imageName : imageNames) {
            cardNames.add(imageName);
            cardNames.add(imageName);
        }

        shuffledIcons = new ArrayList<>();
        Collections.shuffle(cardNames);

        cardButtons = new JButton[NUM_CARDS];
        for (int i = 0; i < NUM_CARDS; i++) {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(CARD_SIZE, CARD_SIZE));
            button.addActionListener(new CardListener(i));
            cardButtons[i] = button;
        }

        JPanel cardPanel = new JPanel(new GridLayout(ROWS, COLS));
        for (int i = 0; i < NUM_CARDS; i++) {
            cardPanel.add(cardButtons[i]);
        }

        add(cardPanel);
        pack();
        setTitle("Memory Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        pairsFound = 0;
        playerScores = new int[2]; // Array für die Punktzahlen der Spieler
    }

    private void showCard(int index) {
        String imageName = cardNames.get(index);
        String imagePath = IMAGE_DIR + imageName;
        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage().getScaledInstance(IMAGE_SIZE, IMAGE_SIZE, Image.SCALE_DEFAULT);
        cardButtons[index].setIcon(new ImageIcon(image));
        cardButtons[index].setEnabled(false);
    }

    private void hideCard(int index) {
        cardButtons[index].setIcon(null);
        cardButtons[index].setEnabled(true);
    }

    private void checkMatch() {
        if (cardNames.get(firstCardIndex).equals(cardNames.get(secondCardIndex)) && firstCardIndex != secondCardIndex) {
            pairsFound++;
            cardButtons[firstCardIndex].setEnabled(false);
            cardButtons[secondCardIndex].setEnabled(false);
            playerScores[currentPlayer]++; // Punktzahl für den aktuellen Spieler erhöhen
        } else {
            hideCard(firstCardIndex);
            hideCard(secondCardIndex);
            currentPlayer = (currentPlayer + 1) % 2; // Wechsel zum nächsten Spieler
        }

       
        if (pairsFound == NUM_IMAGES) {
            int player1Score = playerScores[0];
            int player2Score = playerScores[1];
            String winner;
            
            if (player1Score > player2Score) {
                winner = "Spieler 1";
            } else if (player2Score > player1Score) {
                winner = "Spieler 2";
            } else {
                winner = "Unentschieden";
            }
            
            JOptionPane.showMessageDialog(this, "Spiel beendet!\n\nSpieler 1: " + player1Score + " Punkte\nSpieler 2: " + player2Score + " Punkte\n\nGewinner: " + winner);
            System.exit(0);
        }
        
        firstCardIndex = -1;
        secondCardIndex = -1;
    }

    private class CardListener implements ActionListener {
        private int index;

        public CardListener(int index) {
            this.index = index;
        }

        public void actionPerformed(ActionEvent event) {
            if (cardButtons[index].getIcon() == null) {
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
