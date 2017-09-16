package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Difficulty {
	
	private MainMenu mainMenu;
	
	private JFrame mainFrame;
	private LevelSelection select;
	private JButton easy, normal, hard, expert, perso, back;
	private JLabel selection;
	
	public Difficulty( JFrame frame ){
		mainFrame = frame;
		createView();
		placeComponent();
		createController();
	}
	
	private void createView(){
		Dimension buttonSize = new Dimension( 400, 70 );
		
		selection = new JLabel( "Sélection de la difficulté" );
		selection.setFont( new Font( "Arial", Font.BOLD , 40 ) );
		
		easy = new JButton( "Facile" );
		easy.setPreferredSize( buttonSize );
		
		normal = new JButton( "Normal" );
		normal.setPreferredSize( buttonSize );
		
		hard = new JButton( "Difficile" );
		hard.setPreferredSize( buttonSize );
		
		expert = new JButton( "Expert" );
		expert.setPreferredSize( buttonSize );
		
		perso = new JButton( "Perso" );
		perso.setPreferredSize( buttonSize );
		
		if( listerRepertoire( new File( "niveaux/perso" ) ).size() == 0 ){
			perso.setEnabled( false );
		}
		else{
			perso.setEnabled( true );
		}
		
		back = new JButton( "Retour" );
	}

	private void placeComponent(){		
		JPanel p = new JPanel( new GridBagLayout() );{
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets( 50, 0, 0, 0 );
			p.add( selection, gbc );
		}
		mainFrame.add( p, BorderLayout.NORTH );
		
		p = new JPanel( new GridBagLayout() ); {
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets( 10, 10, 10, 10 );
			
			gbc.gridx = 0;
			gbc.gridy = 0;
			p.add( easy, gbc );
			
			gbc.gridx = 0;
			gbc.gridy = 1;
			p.add( normal, gbc );
			
			gbc.gridx = 0;
			gbc.gridy = 2;
			p.add( hard, gbc );
			
			gbc.gridx = 0;
			gbc.gridy = 3;
			p.add( expert, gbc );
			
			gbc.gridy = 4;
			p.add( perso, gbc );
		}
		mainFrame.add( p );
		
		p = new JPanel();{
			p.add( back );
		}
		mainFrame.add( p, BorderLayout.SOUTH );
	}
	
	private void createController(){
		mainFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		easy.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ){
				mainFrame.getContentPane().removeAll();
				
				select = new LevelSelection( mainFrame, easy.getText() );
				mainFrame.setContentPane( select.getJFrame().getContentPane() );
				mainFrame.repaint();
			}
		});
		
		normal.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ){
				mainFrame.getContentPane().removeAll();
				
				select = new LevelSelection( mainFrame, normal.getText() );
				mainFrame.setContentPane( select.getJFrame().getContentPane() );
				mainFrame.repaint();
			}
		});
		
		hard.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ){
				mainFrame.getContentPane().removeAll();
				
				select = new LevelSelection( mainFrame, hard.getText() );
				mainFrame.setContentPane( select.getJFrame().getContentPane() );
				mainFrame.repaint();
			}
		});
		
		expert.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ){
				mainFrame.getContentPane().removeAll();
				
				select = new LevelSelection( mainFrame, expert.getText() );
				mainFrame.setContentPane( select.getJFrame().getContentPane() );
				mainFrame.repaint();
			}
		});
		
		perso.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ){
				mainFrame.getContentPane().removeAll();
				
				select = new LevelSelection( mainFrame, perso.getText() );
				mainFrame.setContentPane( select.getJFrame().getContentPane() );
				mainFrame.repaint();
			}
		});
		
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
	
	public ArrayList<String> listerRepertoire( File repertoire ){
		ArrayList<String> fileList = new ArrayList<String>();
		ArrayList<String> fileRet = new ArrayList<String>();
		
		for( String f : repertoire.list() ){
			fileList.add( f );
		}
		
		for( int i = 0; i < fileList.size(); i++ ){
			if( fileList.get( i ).endsWith( ".png" ) ){
				fileRet.add( fileList.get( i ) );
			}
		}
		
		return fileRet;
	}
}
