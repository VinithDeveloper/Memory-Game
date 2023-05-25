package memoryGame;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;



public class WinPanel extends JPanel {
	private static final long serialVersionUID = 8318196957210099476L;
	ImageIcon cardBack = new ImageIcon(this.getClass().getResource("images/cardBack.jpeg"));
	JButton winner = new JButton();
	
	public WinPanel() {
		setBorder(new EmptyBorder(0,0,0,0));
		setLayout(new GridLayout(0,1,0,0));
		setBackground(Color.WHITE);
		
		winner.setIcon(cardBack);
		add(winner);		
	}
	

}

