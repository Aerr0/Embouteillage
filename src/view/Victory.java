package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Victory {

	private MainMenu mainMenu;
	private Game game;
	
	private JFrame mainFrame;
	private JFrame frame;
	
	private JButton menu, rejouer, suivant;
	
	private String path;
	private boolean victoireComplete;
	
	public Victory (JFrame f, String p) {
		mainFrame = f;
		path = p;
		
		if (getNextLevel() == null) {
			victoireComplete = true;
		} else {
			victoireComplete = false;
		}
		
		createView();
		placeComponent();
		createController();
	}
	
	public void display(){
		frame.pack();
		frame.setLocationRelativeTo( null );
		frame.setVisible( true );
	}
	
	public void createView() {
		frame = new JFrame("Victoire !!");
		 
		menu = new JButton("Quitter");
		
		rejouer = new JButton("Rejouer");
		
		suivant = new JButton("Suivant !");
	}
	
	public void placeComponent() {
		ImagePanel panel; 
		if (victoireComplete) {
			panel = new ImagePanel(new ImageIcon("images/finish.png").getImage());
		} else {
			panel = new ImagePanel(new ImageIcon("images/victory.png").getImage());
		}
		
		frame.getContentPane().add(panel);
		
		JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER)); {
			p.add(menu);
			p.add(rejouer);
			if (! isPerso() && ! victoireComplete) {
				p.add(suivant);
			}
		}
		frame.add(p, BorderLayout.SOUTH);
	}
	
	public void createController() {
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				mainFrame.getContentPane().removeAll();
				
				mainMenu = new MainMenu( mainFrame );
				mainFrame.setContentPane( mainMenu.getJFrame().getContentPane() );
				mainFrame.repaint();
				frame.dispose();
			}
		});
		
		menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.getContentPane().removeAll();
				
				mainMenu = new MainMenu( mainFrame );
				mainFrame.setContentPane( mainMenu.getJFrame().getContentPane() );
				mainFrame.repaint();
				frame.dispose();
			}
		});
		
		rejouer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.getContentPane().removeAll();
				
				game = new Game( mainFrame, path );
				mainFrame.setContentPane( game.getJFrame().getContentPane() );
				mainFrame.repaint();
				frame.dispose();
			}
			
		});
		
		suivant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.getContentPane().removeAll();
				
				game = new Game( mainFrame, getNextLevel() );
				mainFrame.setContentPane( game.getJFrame().getContentPane() );
				mainFrame.repaint();
				frame.dispose();
			}
			
		});
	}
	
	public String getNextLevel() {
		String str = "niveaux/";
		String difficulty;
		String level = null;
		
		// assignation de la difficulté
		String tmpD = path.substring(8);
		switch (tmpD.charAt(0)) {
			case 'f': difficulty = "facile/F"; break;
			case 'n': difficulty = "normal/N"; break;
			case 'd': difficulty = "difficile/D"; break;
			case 'e': difficulty = "expert/E"; break;
			default: difficulty = "perso/"; break;
		}
		
		// assignation du niveau
		String tmpL = tmpD.substring(difficulty.length());
		switch (tmpL.charAt(0)) {
			case '1': level = "2.lvl"; break; 
			case '2': level = "3.lvl"; break;
			case '3': level = "4.lvl"; break;
			case '4': level = "5.lvl"; break;
			case '5': level = "6.lvl"; break;
			case '6': level = "7.lvl"; break;
			case '7': level = "8.lvl"; break;
			case '8': level = "9.lvl"; break;
			case '9': level = "1.lvl";
				switch(difficulty.charAt(0)) {
					case 'f': difficulty = "normal/N"; break;
					case 'n': difficulty = "difficile/D"; break;
					case 'd': difficulty = "expert/E"; victoireComplete = true; break;
				}
				break;
		}
		
		str += difficulty + level;
		
		return str;
	}
	
	private boolean isPerso() {
		String str = path.substring(8);
		if (str.charAt(0) == 'p') {
			return true;
		}
		
		return false;
	}
}

class ImagePanel extends JPanel {

	private Image img;

	public ImagePanel(String img) {
		this(new ImageIcon(img).getImage());
	}

	public ImagePanel(Image img) {
		this.img = img;
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setLayout(null);
		setVisible(true);
	}

	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}

}
