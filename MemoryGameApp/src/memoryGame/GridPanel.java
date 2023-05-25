
package memoryGame;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;



public class GridPanel extends JPanel {
	private static final long serialVersionUID = 8318196957210099476L;
	
	int numButtons;
	static String pics[] = {"/memoryGame/images/memoryBildEins.jpg",
			"/memoryGame/images/memoryBildZwei.jpg","/memoryGame/images/memoryBildDrei.jpg",
			"/memoryGame/images/memoryBildVier.jpeg","/memoryGame/images/memoryBildFunf.jpeg",
			"/memoryGame/images/memoryBildSechs.jpg","/memoryGame/images/memoryBildSieben.jpg",
			"/memoryGame/images/memoryBildAcht.jpg"
	};
	static JButton[]buttons;
	ImageIcon cardBack= new ImageIcon(this.getClass().getResource("/memoryGame/images/cardBack.jpeg"));
	ImageIcon[]icons;
	private ImageIcon temp;
	
	static int score = 0;
	private boolean gameOver;
	
	Timer myTimer;
	int openImages;
	public int currentIndex;
	public int oddClickIndex;
	public int numClicks;
	
	public GridPanel() {
		setBorder(new EmptyBorder(0,0,0,0));
		setLayout(new GridLayout(0,4,0,0));
		setBackground(Color.WHITE);
		setVisible(true);
		addButtons();
	}
	
	private void addButtons() {
	numButtons = pics.length * 2;
	buttons = new JButton[numButtons];
	icons = new ImageIcon[numButtons];
	
	for(int i=0,j=0; i<pics.length;i++) {
		icons[j]= new ImageIcon(this.getClass()	.getResource(pics[i]));
		j= makeButtons(j);
		
		
		icons[j]=icons[j-1];
		j = makeButtons(j);
	}
		
		Random rnd = new Random();
		for(int i = 0;i < numButtons; i++) {
			int j = rnd.nextInt(numButtons);
			temp = icons[i];
			icons[i] = icons[j];
			icons[j] = temp;
			
		}
		
		myTimer = new Timer(1000, new TimerListener());
		
	}
	
	private int makeButtons(int j) {
		buttons[j] = new JButton("");
		buttons[j].addActionListener(new GridPanel.ImageButtonListener());
		buttons[j].setIcon(cardBack);
		buttons[j].setBackground(Color.WHITE);
		add(buttons[j++]);
		return j;		
	}
	
	private class TimerListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
				buttons[currentIndex].setIcon(cardBack);
				buttons[oddClickIndex].setIcon(cardBack);
				myTimer.stop();
		}
	}
	
	private class ImageButtonListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(myTimer.isRunning())
				return;
			openImages++;
			System.out.println(openImages);
			
			for(int i= 0;i < numButtons; i++) {
				if(e.getSource()== buttons[i]) {
					buttons[i].setIcon(icons[i]);
					currentIndex = i;
					
				}
			}
			
			if(openImages % 2== 0) {
				if(currentIndex == oddClickIndex) {
					numClicks--;
					return;
				}
				
				if(icons[currentIndex] != icons[oddClickIndex]) {
					myTimer.start();
				} else {
					score++;
					System.out.println("score: " + score);
					if(score == 8) {
						setGameOver(true);
						
					}
				}
			} else {
				
				oddClickIndex = currentIndex;
			}
			
		}

	
	}
	
	public static void 	setScore(int score) {
		GridPanel.score = score;
	}
	
	public int getScore() {
		return score;
	}
	
	public boolean isGameOver() {
		return gameOver;
	}
	
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

}
