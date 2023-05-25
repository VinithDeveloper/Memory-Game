package memoryGame;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ScorePanel extends JPanel  {
	
	public ScorePanel() {
	setBorder(new EmptyBorder(0,0,0,0));
	setLayout(new GridLayout(0,4,0,0));
	setBackground(Color.YELLOW);
	setVisible(true);

}
}
	
