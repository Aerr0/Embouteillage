package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class LevelSelection {
	private static int numb = 0;
	private static String remember = "";
	
	private Difficulty difficulty;
	private Game game;
	private String diff;
	
	private JFrame mainFrame;
	private JButton back, level1, level2, level3, next, previous;
	
	private JButton[] levelTab = { level1, level2, level3 };
	private String[] iconTexts = new String[3];
	
	public LevelSelection( JFrame frame, String s ){
		mainFrame = frame;
		diff = s;
		
		createView();
		placeComponent();
		createController();
	}
	
	public void createView(){
		next = new JButton( ">" );
		next.setPreferredSize( new Dimension( 50, 50 ) );
		
		previous = new JButton( "<" );
		previous.setPreferredSize( new Dimension( 50, 50 ) );
		
		if( diff.equals( "Facile" ) ){ gestionBouton( diff.toLowerCase() ); }
		if( diff.equals( "Normal" ) ){ gestionBouton( diff.toLowerCase() ); }
		if( diff.equals( "Difficile" ) ){ gestionBouton( diff.toLowerCase() ); }
		if( diff.equals( "Expert" ) ){ gestionBouton( diff.toLowerCase() ); }
		if( diff.equals( "Perso" ) ){ gestionBouton( diff.toLowerCase() ); }
		
		back = new JButton( "Retour" );
		back.setPreferredSize( new Dimension( 120, 40 ) );
	}
	
	public void placeComponent(){
		JPanel p = new JPanel( new GridBagLayout() ); {
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets( 10, 10, 10, 10 );
			gbc.anchor = GridBagConstraints.CENTER;
			p.add( previous, gbc );
		}
		mainFrame.add( p, BorderLayout.WEST );
		
		p = new JPanel( new GridBagLayout() ); {
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets( 10, 10, 10, 10 );
			
			if( levelTab[1] == null ){
				p.add( levelTab[0], gbc );
			}else{
				if( levelTab[2] == null ){
					p.add( levelTab[0], gbc );
					
					gbc.gridx = 1;
					p.add( levelTab[1], gbc );
				}else{
					p.add( levelTab[0], gbc );
					
					gbc.gridx = 1;
					p.add( levelTab[1], gbc );
					
					gbc.gridx = 0;
					gbc.gridy = 1;
					gbc.gridwidth = 2;
					p.add( levelTab[2], gbc );
				}
			}
		}
		mainFrame.add( p );
		
		p = new JPanel( new GridBagLayout() ); {
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets( 10, 10, 10, 10 );
			gbc.anchor = GridBagConstraints.CENTER;
			p.add( next, gbc );
		}
		mainFrame.add( p, BorderLayout.EAST );
		
		p = new JPanel( new GridBagLayout() ); {
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets( 10, 10, 10, 10 );
			gbc.anchor = GridBagConstraints.CENTER;
			p.add( back, gbc );
		}
		mainFrame.add( p, BorderLayout.SOUTH );
	}
	
	public void createController(){
		mainFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		previous.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				numb -= 3;
				mainFrame.getContentPane().removeAll();
				
				LevelSelection select = new LevelSelection( mainFrame, diff );
				mainFrame.setContentPane( select.getJFrame().getContentPane() );
				mainFrame.repaint();
			}
		});
		
		if( levelTab[1] == null ){
			levelTab[0].addActionListener( new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					String path = new String( "niveaux/" + diff.toLowerCase() + "/" + iconTexts[0] + ".lvl" );
					mainFrame.getContentPane().removeAll();
					
					game = new Game( mainFrame, path );
					mainFrame.setContentPane( game.getJFrame().getContentPane() );
					mainFrame.repaint();
				}
			});
		}else{
			if( levelTab[2] == null ){
				levelTab[0].addActionListener( new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						String path = new String( "niveaux/" + diff.toLowerCase() + "/" + iconTexts[0] + ".lvl" );
						mainFrame.getContentPane().removeAll();
						
						game = new Game( mainFrame, path );
						mainFrame.setContentPane( game.getJFrame().getContentPane() );
						mainFrame.repaint();
					}
				});
				
				levelTab[1].addActionListener( new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						String path = new String( "niveaux/" + diff.toLowerCase() + "/" + iconTexts[1] + ".lvl" );
						mainFrame.getContentPane().removeAll();
						
						game = new Game( mainFrame, path );
						mainFrame.setContentPane( game.getJFrame().getContentPane() );
						mainFrame.repaint();
					}
				});
			}
			else{
				levelTab[0].addActionListener( new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						String path = new String( "niveaux/" + diff.toLowerCase() + "/" + iconTexts[0] + ".lvl" );
						mainFrame.getContentPane().removeAll();
						
						game = new Game( mainFrame, path );
						mainFrame.setContentPane( game.getJFrame().getContentPane() );
						mainFrame.repaint();
					}
				});
				
				levelTab[1].addActionListener( new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						String path = new String( "niveaux/" + diff.toLowerCase() + "/" + iconTexts[1] + ".lvl" );
						mainFrame.getContentPane().removeAll();
						
						game = new Game( mainFrame, path );
						mainFrame.setContentPane( game.getJFrame().getContentPane() );
						mainFrame.repaint();
					}
				});
				
				levelTab[2].addActionListener( new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						String path = new String( "niveaux/" + diff.toLowerCase() + "/" + iconTexts[2] + ".lvl" );
						mainFrame.getContentPane().removeAll();
						
						game = new Game( mainFrame, path );
						mainFrame.setContentPane( game.getJFrame().getContentPane() );
						mainFrame.repaint();
					}
				});
			}
		}
		
		next.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				numb += 3;
				mainFrame.getContentPane().removeAll();
				
				LevelSelection select = new LevelSelection( mainFrame, diff );
				mainFrame.setContentPane( select.getJFrame().getContentPane() );
				mainFrame.repaint();
			}
		});
		
		back.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.getContentPane().removeAll();
				
				difficulty = new Difficulty( mainFrame );
				mainFrame.setContentPane( difficulty.getJFrame().getContentPane() );
				mainFrame.repaint();
			}
		});
	}
	
	public void gestionBouton( String s ){
		if( !remember.equals( s ) ){
			numb = 0;
			remember = s; 	
		}
		
		ArrayList<String> fileName = listerRepertoire( new File( "niveaux/" + s ) );
		int index = 0;
		int correcteur = 0;
		int cor2 = 0;
		
		if( numb + 1 == fileName.size() ){
			correcteur = fileName.size() - numb; 
		}
		
		if( numb + 2 == fileName.size() ){
			cor2 = fileName.size() - numb - 1;
		}
		
		for( int i = 0 + numb + correcteur + cor2; i < 3 + numb - correcteur; i++ ){
			ImageIcon icon = null;
			
			if( numb == 0 ){ previous.setEnabled( false ); }
			else{ previous.setEnabled( true ); }
			
			if( correcteur > 0 || cor2 > 0 ){ 
				icon = new ImageIcon( "niveaux/" + s + "/" + fileName.get( i - correcteur - cor2 ) ); 
				next.setEnabled( false );
				iconTexts[index] = fileName.get(  i - correcteur - cor2 ).substring( 0, fileName.get( i - correcteur - cor2 ).lastIndexOf( "." ) );
			}
			else{ 
				icon = new ImageIcon( "niveaux/" + s + "/" + fileName.get( i ) ); 
				next.setEnabled( true );
				if( fileName.size() == 3 ){ next.setEnabled( false ); }
				iconTexts[index] = fileName.get(i).substring( 0, fileName.get( i ).lastIndexOf( "." ) );
			}
			Image img = icon.getImage();
			Image newImg = img.getScaledInstance( 350, 250,  java.awt.Image.SCALE_SMOOTH );
			icon = new ImageIcon( newImg );
			
			levelTab[index] = new JButton( icon );
			levelTab[index].setPreferredSize( new Dimension( 345, 250 ) );
			
			index++;
		}
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
	
	public JFrame getJFrame(){
		return mainFrame;
	}
}
