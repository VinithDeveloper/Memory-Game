package testproject;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MemoryGame extends JFrame implements ActionListener {
    
    private ArrayList<JButton> buttons = new ArrayList<JButton>();
    private int buttonCount = 16;
    private int matchedCount = 0;
    private int guessCount = 0;
    private JButton firstGuessButton;
    private JButton secondGuessButton;
    
    private String[] iconNames = {"cat.png", "dog.png", "elephant.png", "giraffe.png", "hippo.png", "monkey.png", "panda.png", "parrot.png"};
    private ArrayList<String> icons = new ArrayList<String>();
    
    public MemoryGame() {
        super("Memory Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 4));
        setSize(400, 400);
        
        // Initialize icons
        for (int i = 0; i < iconNames.length; i++) {
            icons.add(iconNames[i]);
            icons.add(iconNames[i]);
        }
        Collections.shuffle(icons);
        
        // Add buttons
        for (int i = 0; i < buttonCount; i++) {
            JButton button = new JButton();
            button.addActionListener(this);
            add(button);
            buttons.add(button);
        }
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton)e.getSource();
        String iconName = icons.get(buttons.indexOf(button));
        button.setIcon(new ImageIcon(iconName));
        button.setEnabled(false);
        
        if (firstGuessButton == null) {
            firstGuessButton = button;
        } else {
            secondGuessButton = button;
            guessCount++;
            if (firstGuessButton.getIcon().toString().equals(secondGuessButton.getIcon().toString())) {
                matchedCount++;
                if (matchedCount == iconNames.length) {
                    JOptionPane.showMessageDialog(this, "You won in " + guessCount + " guesses!");
                    System.exit(0);
                }
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                firstGuessButton.setIcon(null);
                secondGuessButton.setIcon(null);
                firstGuessButton.setEnabled(true);
                secondGuessButton.setEnabled(true);
            }
            firstGuessButton = null;
            secondGuessButton = null;
        }
    }
    
    public static void main(String[] args) {
        MemoryGame game = new MemoryGame();
    }

}
