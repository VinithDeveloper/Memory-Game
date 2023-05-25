package memoryGame;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class GamePanel extends JFrame {

	private static final long serialVersionUID = -1457607589528988866L;
	
	GridPanel gridPane;	
	ScorePanel scorePane;
	WinPanel winPane;
	
	GamePanel(){
		setLayout(new BorderLayout(0,0));
		gridPane = new GridPanel();
		add(gridPane, BorderLayout.CENTER);
		
		winPane = new WinPanel();
		add(winPane, BorderLayout.NORTH);
		winPane.setVisible(false);
		
		scorePane = new ScorePanel();
		add(scorePane, BorderLayout.EAST);
		setVisible(true);
	

	}

}
