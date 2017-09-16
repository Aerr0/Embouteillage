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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Rules {
	
	private MainMenu mainMenu;
	
	private JFrame mainFrame;
	private JButton back;
	private JLabel title;
	private JTextArea textArea;
	private JScrollPane scrollPane;
	
	public Rules( JFrame frame ){
		mainFrame = frame;
		createView();
		placeComponent();
		createController();
	}
	
	private void createView(){
		title = new JLabel( "Règles du jeu" );
		title.setFont( new Font( "Arial", Font.BOLD , 40 ) );
		
		back = new JButton( "Retour" );
		
		textArea = new JTextArea();
		textArea.setPreferredSize( new Dimension( 450, 300 ) );
		textArea.setEnabled( false );
		scrollPane = new JScrollPane( textArea );
		
		String string, stringText = "";
		
		try {
			FileReader fr = new FileReader( new File( "src/view/Rules.txt" ) );
			BufferedReader br = new BufferedReader( fr );
		
			try {
				while( ( string = br.readLine() ) != null ){
					stringText += string + "\n";
				}
				
				br.close();
				fr.close();
			} catch ( IOException e ){ e.printStackTrace(); }
		} 
		catch ( FileNotFoundException e ){ e.printStackTrace(); }
		
		textArea.setText( stringText );
		textArea.setDisabledTextColor( Color.BLACK );
	}
	
	private void placeComponent(){
		JPanel p = new JPanel( new GridBagLayout() );{
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets( 50, 0, 0, 0 );
			p.add( title, gbc );
		}
		mainFrame.add( p, BorderLayout.NORTH );
		
		p = new JPanel();{
			p.add( scrollPane );
		}
		mainFrame.add( p );
		
		p = new JPanel();{
			p.add( back );
		}
		mainFrame.add( p, BorderLayout.SOUTH );
	}
	
	private void createController(){
		mainFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		back.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ){
				mainFrame.getContentPane().removeAll();
				
				mainMenu = new MainMenu( mainFrame );
				mainFrame.setContentPane( mainMenu.getJFrame().getContentPane() );
				mainFrame.repaint();
			}
		});
	}
	
	public JFrame getJFrame(){
		return mainFrame;
	}
}