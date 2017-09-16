package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.BadSyntaxException;
import model.Board;
import model.Card;
import model.Coord;
import model.Direction;
import model.DraggableImageComponent;
import model.DraggableImageComponentGame;
import model.StdBoard;
import model.StdCard;
import model.Type;
import model.Vehicle;

public class Game {
	private MainMenu mainMenu;

	private static JFrame mainFrame;
	private static PanelGame board;
	private static Board model;
	private JButton cancel, redo, reset, quit, help;
	private JLabel level;

	private static JLabel moves;

	private JLabel best;

	private JLabel levelText;

	private JLabel movesText;
	
    private static Image redCar;
    private static int colorHorizontalTruck;
	private static int colorVerticalTruck;
	private static int colorHorizontalCar;
	private static int colorVerticalCar;
	private static ArrayList<Image> verticalTrucks;
	private static ArrayList<Image> horizontalTrucks;
	private static ArrayList<Image> verticalCars;
	private static ArrayList<Image> horizontalCars;
	private static String path;
	private static int nbCoup;

	public Game(JFrame frame, String p) {
		mainFrame = frame;
		path      = p;
		nbCoup    = 0;
		
		initGraphic();
		createModel();
		createView();
		placeComponent();
		createController();
	}
	
	private void createModel() {
		model = new StdBoard();
	}

	private void createView() {
		board = new PanelGame();

		Dimension buttonSize = new Dimension(250, 50);

		cancel = new JButton("Annuler");
		cancel.setPreferredSize(buttonSize);
		
		redo = new JButton("Refaire");
		redo.setPreferredSize(buttonSize);

		reset = new JButton("Réinitialiser");
		reset.setPreferredSize(buttonSize);

		quit = new JButton("Quitter");
		quit.setPreferredSize(buttonSize);

		help = new JButton("Aide");
		help.setPreferredSize(buttonSize);

		level = new JLabel(getLevelName());
		level.setFont(new Font("Arial", Font.PLAIN, 30));
		
		moves = new JLabel("" + nbCoup);
		moves.setFont(new Font("Arial", Font.PLAIN, 30));
		
		best = new JLabel("");
		best.setFont(new Font("Arial", Font.PLAIN, 30));

		levelText = new JLabel("Niveau");
		levelText.setFont(new Font("Arial", Font.PLAIN, 30));
		movesText = new JLabel("Déplacement");
		movesText.setFont(new Font("Arial", Font.PLAIN, 30));
	}

	private void placeComponent() {
		mainFrame.add(board);

		JPanel p = new JPanel(new GridBagLayout());
		{
			GridBagConstraints gbc = new GridBagConstraints();
			Insets insetConfig1 = new Insets(50, 0, 10, 10);
			Insets insetConfig2 = new Insets(0, 0, 0, 0);

			gbc.insets = insetConfig1;
			gbc.gridx = 0;
			p.add(levelText, gbc);

			gbc.insets = insetConfig2;
			gbc.gridy = 1;
			p.add(level, gbc);

			gbc.insets = insetConfig1;
			gbc.gridy = 2;
			p.add(movesText, gbc);

			gbc.insets = insetConfig2;
			gbc.gridy = 3;
			p.add(moves, gbc);

			gbc.insets = insetConfig2;
			gbc.gridy = 5;
			p.add(best, gbc);

			gbc.insets = insetConfig1;
			gbc.gridy = 6;
			p.add(help, gbc);
		}
		mainFrame.add(p, BorderLayout.EAST);

		p = new JPanel(new GridBagLayout());
		{
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(10, 10, 10, 10);

			p.add(cancel, gbc);
			p.add(redo, gbc);
			p.add(reset, gbc);
			p.add(quit, gbc);
		}
		mainFrame.add(p, BorderLayout.SOUTH);
	}

	private void createController() {
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				java.awt.EventQueue.invokeLater(new Runnable() {
		            public void run() {
		                loadVehicles(new File(path));
		            }
		        });
				nbCoup = 0;
				moves.setText("" + nbCoup);
			}
		});
		
		redo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (model.nbOfPossibleRedo() > 0) {
					model.redo();
					loadVehicles();
					nbCoup ++;
					moves.setText("" + nbCoup);
				}
			}
			
		});
		
		quit.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.getContentPane().removeAll();
				
				mainMenu = new MainMenu( mainFrame );
				mainFrame.setContentPane( mainMenu.getJFrame().getContentPane() );
				mainFrame.repaint();
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (model.nbOfPossibleUndo() > 0) {
					model.undo();
					loadVehicles();
					nbCoup --;
					moves.setText("" + nbCoup);
				}
			}
		});
		
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.solve().get(0).act(model);
				loadVehicles();
				play();
				
				cancel.setEnabled(false);
				redo.setEnabled(false);
			}
			
		});
	}

	public JFrame getJFrame() {
		return mainFrame;
	}
	
	// OUTILS
	private void initGraphic() {
		// création de la voiture rouge
	    redCar = Toolkit.getDefaultToolkit().createImage("images/redCar.png");
	    
        // création des ArrayList<Image>
	    verticalTrucks   = new ArrayList<Image>();
	    horizontalTrucks = new ArrayList<Image>();
	    verticalCars     = new ArrayList<Image>();
	    horizontalCars   = new ArrayList<Image>();
	    
	    // verticalTrucks 
	    verticalTrucks.add(Toolkit.getDefaultToolkit().createImage("images/yellowTruckVertical.png"));
	    verticalTrucks.add(Toolkit.getDefaultToolkit().createImage("images/blueTruckVertical.png"));
	    verticalTrucks.add(Toolkit.getDefaultToolkit().createImage("images/purpleTruckVertical.png"));
	    
	    // horizontalTruks
	    horizontalTrucks.add(Toolkit.getDefaultToolkit().createImage("images/yellowTruckHorizontal.png"));
	    horizontalTrucks.add(Toolkit.getDefaultToolkit().createImage("images/blueTruckHorizontal.png"));
	    horizontalTrucks.add(Toolkit.getDefaultToolkit().createImage("images/purpleTruckHorizontal.png"));
	    
	    // verticalCars
	    verticalCars.add(Toolkit.getDefaultToolkit().createImage("images/orangeCarVertical.png"));
	    verticalCars.add(Toolkit.getDefaultToolkit().createImage("images/blueCarVertical.png"));
	    verticalCars.add(Toolkit.getDefaultToolkit().createImage("images/greenCarVertical.png"));
	    
	    // horizontalCars
	    horizontalCars.add(Toolkit.getDefaultToolkit().createImage("images/orangeCarHorizontal.png"));
	    horizontalCars.add(Toolkit.getDefaultToolkit().createImage("images/blueCarHorizontal.png"));
	    horizontalCars.add(Toolkit.getDefaultToolkit().createImage("images/greenCarHorizontal.png"));
        
        colorHorizontalTruck = 1;
	    colorVerticalTruck   = 3;
	    colorHorizontalCar   = 1;
	    colorVerticalCar     = 3;

    	java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                loadVehicles(new File(path));
            }
        });
	}
	
	private String getLevelName() {
		String str = "";
		
		String[] split = path.split("/");
		
		String tmp = split[2]; // le nom du niveau avec .lvl
		for (int i = 0; i < tmp.length() && tmp.charAt(i) != '.'; i++) {
			str += tmp.charAt(i);
		}
		
		return str;
	}
	
	public static void play() {
		nbCoup ++;
		moves.setText("" + nbCoup);
		
		if (model.hasWon()) {
			new Victory(mainFrame, path).display();
		}
		
	}
	
	public static void loadVehicles() {
		board.removeAll();
		
		colorHorizontalTruck = 1;
	    colorVerticalTruck   = 3;
	    colorHorizontalCar   = 1;
	    colorVerticalCar     = 3;
	    
		Set<Vehicle> set = model.getAllVehicles();
        for (Vehicle v : set) {
        	Coord c = model.getCoord(v);
        	if (v.getType() == Type.CAR) {
        		if (v.getDirection() == Direction.HORIZONTAL) {
        			addNewVehicle(getHorizontalCar(), v, 139, 67, c);
        		} else {
        			addNewVehicle(getVerticalCar(), v, 67, 139, c);
        		}
        	} else if (v.getType() == Type.TRUCK) {
        		if (v.getDirection() == Direction.HORIZONTAL) {
        			addNewVehicle(getHorizontalTruck(), v, 207, 67, c);
        		} else {
        			addNewVehicle(getVerticalTruck(), v, 67, 207, c);
        		}
        	} else {
        		addNewVehicle(redCar, v, 139, 67, c);
        	}
        }
        
        board.repaint();
	}
	
	public static void loadVehicles(File f) {
        board.removeAll();
        
        colorHorizontalTruck = 1;
	    colorVerticalTruck   = 3;
	    colorHorizontalCar   = 1;
	    colorVerticalCar     = 3;
       
        Card card = new StdCard(f);
        try {
			card.load();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BadSyntaxException e) {
			e.printStackTrace();
		}
        
        model.placeVehicles(card);
        
        Set<Vehicle> set = model.getAllVehicles();
        for (Vehicle v : set) {
        	Coord c = model.getCoord(v);
        	if (v.getType() == Type.CAR) {
        		if (v.getDirection() == Direction.HORIZONTAL) {
        			addNewVehicle(getHorizontalCar(), v, 139, 67, c);
        		} else {
        			addNewVehicle(getVerticalCar(), v, 67, 139, c);
        		}
        	} else if (v.getType() == Type.TRUCK) {
        		if (v.getDirection() == Direction.HORIZONTAL) {
        			addNewVehicle(getHorizontalTruck(), v, 207, 67, c);
        		} else {
        			addNewVehicle(getVerticalTruck(), v, 67, 207, c);
        		}
        	} else {
        		addNewVehicle(redCar, v, 139, 67, c);
        	}
        }
        
        board.repaint();
    }
	
	public static void addNewVehicle(Image img, Vehicle veh, int width, int height, Coord coord) {    	
    	// Créer un DraggableImageComponent et ajoute son image
        DraggableImageComponentGame comp = new DraggableImageComponentGame(model, veh, coord);
        board.add(comp); // Ajoute ce Component sur la frame
        comp.setImage(img); // Modifie l'image
        comp.setOverbearing(true); // Avec un click, ce component est prioritaire sur les autres

        comp.setSize(width, height);
        
        board.repaint();
    }
    
    public static int getRandom(int range) {
        int r = (int) (Math.random() * range) - range;
        return r;
    }
    
    private static Image getVerticalTruck() {
		int nb = colorVerticalTruck%3;
		colorVerticalTruck ++;
		
		return verticalTrucks.get(nb);
	}
	
	private static Image getHorizontalTruck() {
		int nb = colorHorizontalTruck%3;
		colorHorizontalTruck ++;
		
		return horizontalTrucks.get(nb);
	}
	
	private static Image getVerticalCar() {
		int nb = colorVerticalCar%3;
		colorVerticalCar ++;
		
		return verticalCars.get(nb);
	}
	
	private static Image getHorizontalCar() {
		int nb = colorHorizontalCar%3;
		colorHorizontalCar ++;
		
		return horizontalCars.get(nb);
	}
}
