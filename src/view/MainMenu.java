package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainMenu {
	
	private Difficulty difficulty;
	private Rules rules;
	private CreateCard createCard;
	private Game game;
	
	private JFrame mainFrame;
	private JButton level, rulesButton, cardButton, play;
	private JLabel title;
	private JLabel title2;
	
	public MainMenu(){
		mainFrame = new JFrame( "Rush Hour" );
		mainFrame.setPreferredSize( new Dimension( 1280, 720 ) );
		
		createView();
		placeComponent();
		createController();
	}
	
	public MainMenu( JFrame frame ){
		mainFrame = frame;
		
		createView();
		placeComponent();
		createController();
	}
	
	public void display(){
		mainFrame.pack();
		mainFrame.setLocationRelativeTo( null );
		mainFrame.setVisible( true );
	}

	private void createView() {
		Dimension buttonSize = new Dimension( 250, 50 );
		
		title = new JLabel( "RUSH" );
		title.setFont( new Font( "Arial", Font.BOLD , 60 ) );
		title.setForeground( Color.RED );
		
		title2 = new JLabel( "HOUR" );
		title2.setFont( new Font( "Arial", Font.BOLD , 60 ) );
		title2.setForeground( Color.YELLOW );
		
		level = new JButton( "Niveaux" );
		level.setFont( new Font( "Arial", Font.PLAIN , 20 ) );
		level.setPreferredSize( buttonSize );
		
		rulesButton = new JButton( "Règles" );
		rulesButton.setFont( new Font( "Arial", Font.PLAIN , 20 ) );
		rulesButton.setPreferredSize( buttonSize );
		
		cardButton = new JButton( "Créer" );
		cardButton.setFont( new Font( "Arial", Font.PLAIN , 20 ) );
		cardButton.setPreferredSize( buttonSize );
		
		play = new JButton( "Jouer" );
		play.setFont( new Font( "Arial", Font.PLAIN , 20 ) );
		play.setPreferredSize( buttonSize );
	}

	private void placeComponent() {
		JPanel p = new JPanel( new GridBagLayout() ); {
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets( 50, 20, 50, 0 );
			p.add( title, gbc );
			p.add( title2, gbc );
			
			p.setBackground( new Color( 0, 100, 255 ) );
		}
		mainFrame.add( p, BorderLayout.NORTH );
		
		p = new JPanel( new GridBagLayout() ); {
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets( 5, 5, 5, 5 );
			
			p.add( rulesButton, gbc );			
			
			gbc.gridx = 1;
			p.add( level, gbc );
		
			gbc.gridx = 0;
			gbc.gridy = 1;
			p.add( cardButton, gbc );
			
			gbc.gridx = 1;
			gbc.gridy = 1;
			p.add( play, gbc );
		}
		mainFrame.add( p );
	}

	private void createController() {
		mainFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		level.addActionListener( new ActionListener(){
		public void actionPerformed( ActionEvent e ){
				mainFrame.getContentPane().removeAll();
				
				difficulty = new Difficulty( mainFrame );
				mainFrame.setContentPane( difficulty.getJFrame().getContentPane() );
				mainFrame.repaint();
			}
		});
		
		rulesButton.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ){
				mainFrame.getContentPane().removeAll();
				
				rules = new Rules( mainFrame );
				mainFrame.setContentPane( rules.getJFrame().getContentPane() );
				mainFrame.repaint();
			}
		});
		
		cardButton.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ){
				mainFrame.getContentPane().removeAll();
				
				createCard = new CreateCard( mainFrame );
				mainFrame.setContentPane( createCard.getJFrame().getContentPane() );
				mainFrame.repaint();
			}
		});
		
		play.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ){
				mainFrame.getContentPane().removeAll();
				
				game = new Game( mainFrame, "niveaux/facile/F1.lvl");
				mainFrame.setContentPane( game.getJFrame().getContentPane() );
				mainFrame.repaint();
			}
		});
	}
	
	public JFrame getJFrame(){
		return mainFrame;
	}
	
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainMenu().display();
            }
        });
    }

}
