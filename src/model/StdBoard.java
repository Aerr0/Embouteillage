package model;

import cmd.Command;
import cmd.Move;
import cmd.MoveHelp;
import util.Contract;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StdBoard implements Board, Cloneable {

	// ATTRIBUTS

	/**
	 * Le nombre de lignes sur le plateau.
	 */
	private int rowNb;

	/**
	 * Le nombre de colonnes sur le plateau.
	 */
	private int columnNb;

	/**
	 * La coordonnée de la sortie.
	 */
	private Coord exit;

	/**
	 * La historique.
	 */
	private History<Command> history;

	/**
	 * Nombre des annuls possibles.
	 */
	private int nbOfPossibleUndo;

	/**
	 * Nombre des réetablissement possibles.
	 */
	private int nbOfPossibleRedo;

	/**
	 * maps de tous les véhicules et ses coordonnées dans le plateau.
	 */
	private Map<Vehicle, Coord> vehicleMap;
	private Map<Coord, Vehicle> coordMap;

	// CONSTRUCTEURS

	/**
	 * Créer un plateau de jeu par défaut.
	 */
	public StdBoard() {
		columnNb = DEFAULT;
		rowNb = DEFAULT;
		exit = EXIT;
		history = new StdHistory<Command>();
		vehicleMap = new MaHashMap<Vehicle, Coord>();
		coordMap = new MaHashMap<Coord, Vehicle>();
		nbOfPossibleUndo = 0;
		nbOfPossibleRedo = 0;
	}

	public Coord getExit() {
		return exit;
	}

	public boolean hasWon() {
		if (coordMap.get(new StdCoord(2, 5)) == null) {
			return false;
		}
		if (coordMap.get(new StdCoord(2, 5)).isRed()) {
			return true;
		}
		return false;
	}

	public int getRowNb() {
		return rowNb;
	}

	public int getColNb() {
		return columnNb;
	}

	public History<Command> getHistory() {
		return history;
	}

	public Set<Vehicle> getAllVehicles() {
		return vehicleMap.keySet();
	}

	public Vehicle getVehicle(Coord coord) {
		return coordMap.get(coord);
	}

	public int nbOfPossibleUndo() {
		return nbOfPossibleUndo;
	}

	public int nbOfPossibleRedo() {
		return nbOfPossibleRedo;
	}

	public Coord getCoord(Vehicle vehicle) {
		return vehicleMap.get(vehicle);
	}

	public boolean isFree(Coord coord) {
		return coordMap.get(coord) == null;
	}
	
	public int getNbFileWithoutName() {
		int i = 1;
		while (new File("niveaux/perso/" + i + ".lvl").exists()) {
			i ++;
		}
		i ++;
		return i;
	}

	public boolean isFree(Vehicle v, Coord coord) {
		Coord c;
		int row = coord.getRow();
		int col = coord.getCol();

		if (v.getDirection() == Direction.HORIZONTAL) {
			for (int i = 0; i < v.getSize(); i++) {
				c = new StdCoord(row, col + i);
				if (!isFree(c) && coordMap.get(c) != v) {
					return false;
				}
			}
		} else { // véhicule vertical
			for (int i = 0; i < v.getSize(); i++) {
				c = new StdCoord(row + i, col);
				if (!isFree(c) && coordMap.get(c) != v) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean canMoveTo(Vehicle vehicle, Coord coord) {
		Contract.checkCondition(vehicle != null, "Ce véhicule n'existe pas");
		Contract.checkCondition(coord != null, "Cette coordonnée est nulle");

		boolean t = false;

		if (vehicle.getDirection() == Direction.HORIZONTAL) {
			t = coord.getCol() <= 5 - (vehicle.getSize() - 1);
		} else {
			t = coord.getRow() <= 5 - (vehicle.getSize() - 1);
		}

		return coord.isAlignedWith(vehicleMap.get(vehicle), vehicle.getDirection()) && isFree(vehicle, coord)
				&& checkAllFreeCoords(vehicle, coord) && t;
	}

	public boolean isValidCol(int col) {
		return 0 <= col && col <= getColNb();
	}

	public boolean isValidRow(int row) {
		return 0 <= row && row <= getRowNb();
	}

	public Coord getFirstVehicleBefore(Vehicle veh) {
		Coord c = getCoord(veh);

		if (veh.getDirection() == Direction.HORIZONTAL) {
			int x = c.getCol() - 1;
			if (x <= 0 && canMoveTo(veh, new StdCoord(c.getRow(), 0))) {
				return new StdCoord(c.getRow(), 0);
			}
			while (x > 0 && canMoveTo(veh, new StdCoord(c.getRow(), x))) {
				x--;
			}
			if (!canMoveTo(veh, new StdCoord(c.getRow(), 0))) {
				x++;
			}
			return new StdCoord(c.getRow(), x);
		} else {
			int y = c.getRow() - 1;
			if (y <= 0 && canMoveTo(veh, new StdCoord(0, c.getCol()))) {
				return new StdCoord(0, c.getCol());
			}
			while (y > 0 && canMoveTo(veh, new StdCoord(y, c.getCol()))) {
				y--;
			}
			if (!canMoveTo(veh, new StdCoord(0, c.getCol()))) {
				y++;
			}
			return new StdCoord(y, c.getCol());
		}
	}

	public Coord getFirstVehicleAfter(Vehicle veh) {
		Coord c = getCoord(veh);

		if (veh.getDirection() == Direction.HORIZONTAL) {
			int x = c.getCol() + 1;
			if (x >= 5 - (veh.getSize() - 1)) {
				return new StdCoord(c.getRow(), 5 - (veh.getSize() - 1));
			}
			while (x < 6 - (veh.getSize() - 1) && canMoveTo(veh, new StdCoord(c.getRow(), x))) {
				x++;
			}
			x--;
			return new StdCoord(c.getRow(), x);
		} else {
			int y = c.getRow() + 1;
			if (y >= 5 - (veh.getSize() - 1)) {
				return new StdCoord(5 - (veh.getSize() - 1), c.getCol());
			}
			while (y < 6 - (veh.getSize() - 1) && canMoveTo(veh, new StdCoord(y, c.getCol()))) {
				y++;
			}
			y--;
			return new StdCoord(y, c.getCol());
		}
	}

	public String toString() {
		String str = "";
		char[][] tab = new char[6][6];

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				if (coordMap.get(new StdCoord(i, j)) == null) {
					tab[i][j] = ' ';
				} else {
					tab[i][j] = '*';
				}
			}
		}

		for (int i = 0; i < 6; i++) {
			str += "|";
			for (int j = 0; j < 6; j++) {
				str += tab[i][j] + "|";
			}
			str += "\n";
		}
		return str;
	}

	public int hashCode() {
		int compteur = 0;
		Vehicle v;
		for (Coord c : getCoordMap().keySet()) {
			v = getVehicle(c);
			if (v != null) {
				compteur += v.getId() * c.getCol() * c.getRow();
			}
		}
		return compteur;
	}

	public boolean equals(Object o) {
		if ((o != null) && (o.getClass() == this.getClass())) {
			StdBoard obj = (StdBoard) o;
			return this.getVehicleMap().equals(obj.getVehicleMap());
		}
		return false;
	}

	private Map<Coord, Vehicle> getCoordMap() {
		return this.coordMap;
	}

	public Map<Vehicle, Coord> getVehicleMap() {
		return this.vehicleMap;
	}

	// COMMANDES
	
	public void addVehicle(Vehicle veh, Coord coord) {
		Contract.checkCondition(veh != null);
		Contract.checkCondition(coord.isValidCol(coord.getCol()) && coord.isValidRow(coord.getRow()));
		
		List<Coord> coords = new ArrayList<Coord>();
		
		switch (veh.getType()) {
			case CAR:
				if (veh.getDirection() == Direction.HORIZONTAL) {
					coords.add(coord);
					coords.add(new StdCoord(coord.getRow(), coord.getCol()+1));
				} else {
					coords.add(coord);
					coords.add(new StdCoord(coord.getRow()+1, coord.getCol()));
				}
				break;
			case TRUCK:
				if (veh.getDirection() == Direction.HORIZONTAL) {
					coords.add(coord);
					coords.add(new StdCoord(coord.getRow(), coord.getCol()+1));
					coords.add(new StdCoord(coord.getRow(), coord.getCol()+2));
				} else {
					coords.add(coord);
					coords.add(new StdCoord(coord.getRow()+1, coord.getCol()));
					coords.add(new StdCoord(coord.getRow()+2, coord.getCol()));
				}
				break;
			default:
				coords.add(coord);
				coords.add(new StdCoord(coord.getRow(), coord.getCol()+1));
				break;
		}
		
		vehicleMap.put(veh, coord);
		
		for (Coord c : coords) {
			coordMap.put(c, veh);
		}
	}
	
	public void removeVehicle(Vehicle veh, Coord coord) {
		Contract.checkCondition(veh != null);
		Contract.checkCondition(coord.isValidCol(coord.getCol()) && coord.isValidRow(coord.getRow()));
		
		List<Coord> coordTmp = new ArrayList<Coord>();
		coordTmp = getKeys(veh);
		
		// Suppression des coordonnées
		for (int i = 0; i < coordTmp.size(); i++ ) {
			coordMap.remove(coordTmp.get(i));
		}
		
		// Suppression du véhicule
		vehicleMap.remove(veh);
	}

	private List<Coord> getKeys(Vehicle v) {
		List<Coord> keys = new ArrayList<Coord>();
		for (Coord key : coordMap.keySet()) {
			if (coordMap.get(key).equals(v)) {
				keys.add(key);
			}
		}
		return keys;
	}

	public void clear() {
		vehicleMap.clear();
		coordMap.clear();
	}

	public void moveTo(Vehicle veh, Coord coord) {
		Contract.checkCondition(canMoveTo(veh, coord), "le vehicule ne peut pas y aller !!!");
		Contract.checkCondition(isFree(veh, coord));
		Contract.checkCondition(vehicleMap.get(veh) != null, "ce vehicule n'existe pas");
		
		nbOfPossibleUndo ++;
		Command cmd = new Move(veh, coord, vehicleMap.get(veh));
		history.add(cmd);
		cmd.act(this);
	}

	public void moveAux(Vehicle veh, Coord coord) {
		List<Coord> newCoords = new ArrayList<Coord>();

		newCoords.add(coord);
		if (veh.getDirection() == Direction.HORIZONTAL) {
			newCoords.add(new StdCoord(coord.getRow(), coord.getCol() + 1));
			if (veh.getSize() == 3) {
				newCoords.add(new StdCoord(coord.getRow(), coord.getCol() + 2));
			}
		} else {
			newCoords.add(new StdCoord(coord.getRow() + 1, coord.getCol()));
			if (veh.getSize() == 3) {
				newCoords.add(new StdCoord(coord.getRow() + 2, coord.getCol()));
			}

		}

		Coord old = vehicleMap.get(veh);
		List<Coord> oldCoords = new ArrayList<Coord>();
		oldCoords.add(old);
		if (veh.getDirection() == Direction.HORIZONTAL) {
			oldCoords.add(new StdCoord(old.getRow(), old.getCol() + 1));
			if (veh.getSize() == 3) {
				oldCoords.add(new StdCoord(old.getRow(), old.getCol() + 2));
			}
		} else {
			oldCoords.add(new StdCoord(old.getRow() + 1, old.getCol()));
			if (veh.getSize() == 3) {
				oldCoords.add(new StdCoord(old.getRow() + 2, old.getCol()));
			}
		}

		vehicleMap.remove(veh);
		vehicleMap.put(veh, newCoords.get(0));

		for (int i = 0; i < oldCoords.size(); i++) {
			coordMap.remove(oldCoords.get(i));
		}

		for (int i = 0; i < newCoords.size(); i++) {
			coordMap.put(newCoords.get(i), veh);
		}
	}

	public void placeVehicles(Card card) {
		Contract.checkCondition(card != null);
		vehicleMap.clear();
		coordMap.clear();

		for (Vehicle veh : card.getPlaces().keySet()) {
			vehicleMap.put(veh, card.getPlaces().get(veh));
		}

		List<Coord> coords;
		for (Vehicle vehicle : vehicleMap.keySet()) {
			coords = new ArrayList<Coord>();

			Coord c = vehicleMap.get(vehicle);
			coords.add(c);
			if (vehicle.getDirection() == Direction.HORIZONTAL) { // véhicule
																	// horizontal
				coords.add(new StdCoord(c.getRow(), c.getCol() + 1));
				if (vehicle.getSize() == 3) {
					coords.add(new StdCoord(c.getRow(), c.getCol() + 2));
				}
			} else { // véhicule vertical
				coords.add(new StdCoord(c.getRow() + 1, c.getCol()));
				if (vehicle.getSize() == 3) {
					coords.add(new StdCoord(c.getRow() + 2, c.getCol()));
				}
			}

			for (Coord coord : coords) {
				coordMap.put(coord, vehicle);
			}
		}

	}

	public void writeInFile(File file, Card card) {
		Contract.checkCondition(file != null && card != null);
		card.setFile(file);
		try {
			card.save(vehicleMap);
		} catch (IOException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
	}

	public void redo() {
		Contract.checkCondition(nbOfPossibleRedo > 0);
		nbOfPossibleUndo += 1;
		nbOfPossibleRedo -= 1;
		history.goForward();
		history.getCurrentElement().act(this);
	}

	public void undo() {
		Contract.checkCondition(nbOfPossibleUndo > 0);
		nbOfPossibleUndo -= 1;
		nbOfPossibleRedo += 1;
		history.getCurrentElement().act(this);
		history.goBackward();
	}

	public Object clone() {
		StdBoard clone = null;
		try {
			clone = (StdBoard) super.clone();
			clone.vehicleMap = (MaHashMap<Vehicle, Coord>) ((MaHashMap<Vehicle, Coord>) this.vehicleMap).clone();
			clone.coordMap = (MaHashMap<Coord, Vehicle>) ((MaHashMap<Coord, Vehicle>) this.coordMap).clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError("echec clonage");
		}
		return clone;
	}

	public Board doCommand(Command c) {
		c.act(this);
		return this;
	}

	// OUTILS
	/*
	 * 
	 */
	public List<Command> solve() {
		List<DuoC> list = new ArrayList<DuoC>();
		// on ajoute le premier plateau dans les configurations réalisables
		DuoC dc = new DuoC(new ArrayList<Command>(), (StdBoard) this.clone());
		list.add(0, dc);

		Board board;
		Set<Vehicle> vehicleSet;
		DuoC d;
		DuoC duoC;

		// Liste de coordonnées, liste de commandes
		List<Coord> coordList;
		List<Command> mvtList;

		for (int i = 0; i < 200000; i++) {
			try {
				duoC = list.get(i);
			} catch (IndexOutOfBoundsException e) {
				return null;
			}
			// ICI, FAIRE UN TRY CATCH
			// QUI RENVOIE NULL
			// EN CAS DE INDEXOUTOFBOUNDEXCEPTION
			// duoC = list.get(i);
			board = duoC.getBoard();
			vehicleSet = board.getAllVehicles();
			for (Vehicle v : vehicleSet) {
				coordList = board.AlignedCoordWith(v);
				mvtList = board.mvtList(v, coordList);
				for (Command c : mvtList) {
					d = (DuoC) duoC.clone();
					d.addCommand(c);
					d.addBoard(d.getBoard().doCommand(c));
					if (d.getBoard().hasWon()) {
						return d.getCommandList();
					}
					if (!(list.contains(d))) {
						list.add(list.size(), d);
					}
				}
			}
		}
		return null;
	}

	/*
	 * Renvoie une liste des coordonnées alignées avec le vehicle passé en
	 * argument
	 */
	public List<Coord> AlignedCoordWith(Vehicle v) {
		Contract.checkCondition(v != null, "Vehicule invalide !");
		Coord oldCoord = getCoord(v);
		List<Coord> newList = new ArrayList<Coord>();
		if (v.getDirection() == Direction.HORIZONTAL) {
			int row = oldCoord.getRow();
			for (int i = 0; i < getColNb(); i++) {
				newList.add(new StdCoord(row, i));
			}
			// modifier la suite en fixant la colonne
		} else if (v.getDirection() == Direction.VERTICAL) {
			int col = oldCoord.getCol();
			for (int i = 0; i < getRowNb(); i++) {
				newList.add(new StdCoord(i, col));
			}
		} else {

		}
		return newList;
	}

	/*
	 * Renvoie une liste de commande que le véhicule est capable de faire
	 */
	public List<Command> mvtList(Vehicle v, List<Coord> l) {
		Contract.checkCondition(v != null && l != null, "Vehicule ou liste de coordonnées invalides !");
		// ici, il faut, grâce à la liste de coordonnées que on a passé en
		// parametre,
		// regarder chacun des mouvements possibles jusqu'a ces coordonnées
		List<Command> mvtList = new ArrayList<Command>();
		for (Coord c : l) {
			if (canMoveTo(v, c)) {
				mvtList.add(new MoveHelp(v, c, getCoord(v)));
			}
		}
		return mvtList;
	}

	public boolean checkAllFreeCoords(Vehicle vehicle, Coord coord) {
		if (vehicle.getDirection() == Direction.HORIZONTAL) {
			Coord c = vehicleMap.get(vehicle);
			int col = c.getCol() + (vehicle.getSize() - 1);
			if (col <= coord.getCol()) {
				for (int i = col + 1; i <= coord.getCol(); i++) {
					Coord next = new StdCoord(c.getRow(), i);
					if (!isFree(next)) {
						return false;
					}
				}
			} else {
				for (int i = col - vehicle.getSize(); i >= coord.getCol(); i--) {
					Coord next = new StdCoord(c.getRow(), i);
					if (!isFree(next)) {
						return false;
					}
				}
			}
		} else {
			Coord c = vehicleMap.get(vehicle);
			int row = c.getRow() + (vehicle.getSize() - 1);
			if (row <= coord.getRow()) {
				for (int i = (row + 1); i <= coord.getRow(); i++) {
					Coord next = new StdCoord(i, c.getCol());
					if (!isFree(next)) {
						return false;
					}
				}
			} else {
				for (int i = row - vehicle.getSize(); i >= coord.getRow(); i--) {
					Coord next = new StdCoord(i, c.getCol());
					if (!isFree(next)) {
						return false;
					}
				}
			}
		}

		return true;
	}
}