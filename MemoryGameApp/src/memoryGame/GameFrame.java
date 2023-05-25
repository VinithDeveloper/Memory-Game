package memoryGame;

import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 * 
 * @author Simon Lötscher
 * Project:Memory Game
 * Version:27.04.2023
 *
 */

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1916604952693054273L;
	private GamePanel gp;

	public GameFrame() {
	
	setTitle("Memory Spiel");
	setBackground (Color.BLUE);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(5,5,900,700);
	setIconImage(Toolkit.getDefaultToolkit()
			.getImage(GameFrame.class
			.getResource("/memoryGame/images/memoryBild.png")));
	gp = new GamePanel();
	setContentPane(gp);
			
	}
}
