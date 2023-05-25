package memoryGame;

import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 * 
 * @author Simon Lötscher
 * Project:Memory Game
 * Version:27.04.2023
 *
 */

public class MemoryGameApp extends JFrame {


	private static final long serialVersionUID = 1859028356359840318L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameFrame game = new GameFrame();
					game.setVisible(true);
				}catch (Exception e) {
				e.printStackTrace();	
				}
			}
		});
}
}