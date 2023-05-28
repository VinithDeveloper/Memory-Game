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
	private int currentPlayer = 1; // Aktueller Spieler
	private JLabel player1ScoreLabel; // Label für die Punktzahl des Spielers 1
	private JLabel player2ScoreLabel; // Label für die Punktzahl des Spielers 2
	private JLabel currentPlayerLabel; // Label für den aktuellen Spieler

	public MemoryGame() {
		cardNames = new ArrayList<>(); // Neue Liste für die Karten-Namen erstellen
		String[] imageNames = { "memoryBildEins.jpg", "memoryBildZwei.jpg", "memoryBildDrei.jpg", "memoryBildVier.jpeg","memoryBildFunf.jpeg", "memoryBildSechs.jpg",
				"memoryBildSieben.jpg", "memoryBildAcht.jpg","memoryBildNeun.jpg","memoryBildZehn.jpg" };
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

		JPanel cardPanel = new JPanel(new GridLayout(ROWS, COLS)); 
		for (int i = 0; i < NUM_CARDS; i++) {
			cardPanel.add(cardButtons[i]); 
		}

		playerScores = new int[2]; 

		player1ScoreLabel = new JLabel("Spieler 1: 0 Punkte"); // Label für die Punktzahl des Spielers 1 erstellen
		player1ScoreLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Schriftart und -größe ändern
		player1ScoreLabel.setForeground(Color.BLUE); // Textfarbe ändern
		player1ScoreLabel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2)); // Eine Grenze um das Label hinzufügen
				
		player2ScoreLabel = new JLabel("Spieler 2: 0 Punkte"); // Label für die Punktzahl des Spielers 2 erstellen
		player2ScoreLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Schriftart und -größe ändern
		player2ScoreLabel.setForeground(Color.RED); // Textfarbe ändern
		player2ScoreLabel.setBorder(BorderFactory.createLineBorder(Color.RED, 2)); // Eine Grenze um das Label hinzufügen
		
		currentPlayerLabel = new JLabel("Aktueller Spieler: Spieler 1"); // Label für den aktuellen Spieler erstellen
		currentPlayerLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Schriftart und -größe ändern
		currentPlayerLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Eine Grenze um das Label hinzufügen
		// Neues Panel für die Spielerinformationen
		JPanel playerInfoPanel = new JPanel(new GridLayout(1, 3)); 
		playerInfoPanel.add(player1ScoreLabel); 
		playerInfoPanel.add(player2ScoreLabel); 
		playerInfoPanel.add(currentPlayerLabel); 

		// Verwenden Sie BorderLayout und fügen Sie die Panels entsprechend hinzu
		setLayout(new BorderLayout()); 
		add(cardPanel, BorderLayout.CENTER);
		add(playerInfoPanel, BorderLayout.SOUTH);

		pack();
		setTitle("Memory Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		pairsFound = 0;
		setIconImage(Toolkit.getDefaultToolkit().getImage("src/testproject/images/memoryBild.png"));
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
		} catch (IOException e) {
			e.printStackTrace();
		}

		cardButtons[index].setEnabled(false);
		currentPlayerLabel.setText("Aktueller Spieler: Spieler " + currentPlayer);
	}

	private void hideCard(int index) {
		cardButtons[index].setIcon(null); // Icon des Kartenbuttons entfernen
		cardButtons[index].setEnabled(true); // Kartenbutton aktivieren, um ihn klickbar zu machen
	}

	private void checkMatch() {
		if (cardNames.get(firstCardIndex).equals(cardNames.get(secondCardIndex)) && firstCardIndex != secondCardIndex) {
			pairsFound++; // Anzahl der gefundenen Paare erhöhen
			int currentPlayerScore = playerScores[currentPlayer - 1]; // Punktzahl des aktuellen Spielers abrufen

			currentPlayerScore++; // Punktzahl des aktuellen Spielers erhöhen
			playerScores[currentPlayer - 1] = currentPlayerScore; // Aktualisierte Punktzahl speichern
			player1ScoreLabel.setText("Spieler 1: " + playerScores[0] + " Punkte"); // Punkteanzeigen aktualisieren für
																					// Spieler 1
			player2ScoreLabel.setText("Spieler 2: " + playerScores[1] + " Punkte"); // Punkteanzeigen aktualisieren für
																					// Spieler 2

			cardButtons[firstCardIndex].setEnabled(false); // Erstes Kartenpaar deaktivieren
			cardButtons[secondCardIndex].setEnabled(false); // Zweites Kartenpaar deaktivieren
		} else {
			hideCard(firstCardIndex); // Erstes Kartenpaar verstecken
			hideCard(secondCardIndex); // Zweites Kartenpaar verstecken

			currentPlayer = (currentPlayer == 1) ? 2 : 1; // Spieler wechseln
			currentPlayerLabel.setText("Aktueller Spieler: Spieler " + currentPlayer); // Anzeigen des aktuellen
																						// Spielers
		}

		if (pairsFound == NUM_IMAGES) {
			// Überprüfen, ob alle Paare gefunden wurden
			if (playerScores[0] > playerScores[1]) {
				JOptionPane.showMessageDialog(this, "Spieler 1 gewinnt mit " + playerScores[0] + " Punkten!");
			} else if (playerScores[1] > playerScores[0]) {
				JOptionPane.showMessageDialog(this, "Spieler 2 gewinnt mit " + playerScores[1] + " Punkten!");
			} else {
				JOptionPane.showMessageDialog(this,
						"Unentschieden! Beide Spieler haben " + playerScores[0] + " Punkte erreicht!");
			}

			System.exit(0);
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