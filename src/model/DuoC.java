package model;

import java.util.List;
import java.util.ArrayList;

import cmd.Command;

import util.Contract;

public class DuoC implements Cloneable {
	
	// ATTRIBUTS
	
	private List<Command> list;
	
	private Board board;
	
	// CONSTRUCTEURS
	
	public DuoC(List<Command> l, Board b) {
		Contract.checkCondition(l != null && b != null, "Erreur sur DuoC !");
		list = l;
		board = b;
	}
	
	// REQUETES
	
	public Board getBoard() {
		return board;
	}
	
	public List<Command> getCommandList() {
		return list;
	}
	
	public boolean equals(Object o) {
		return getBoard().equals(((DuoC) o).getBoard());
	}
	
	public int hashCode() {
		return this.getBoard().hashCode();
	}
	
	// COMMANDES
	
	public void addBoard(Board b) {
		Contract.checkCondition(b != null, "Erreur sur l'ajout du plateau !");
		this.board = b;
	}
	
	public void addCommand(Command c) {
		Contract.checkCondition(c != null, "Erreur sur l'ajout d'une commande !");
		list.add(c);
	}
	public void addCommandList(List<Command> l) {
		Contract.checkCondition(l != null, "Erreur sur l'ajout d'une commande !");
		this.list = l;
	}
	
	public Object clone() {
		DuoC clone = null;
		  try {
			  clone = (DuoC) super.clone();
			  clone.addBoard((StdBoard) getBoard().clone());
			  clone.addCommandList( (ArrayList<Command>) ((ArrayList<Command>) getCommandList()).clone());
		  } catch (CloneNotSupportedException e) {
			  throw new InternalError("echec clonage");
		  }
		return clone;
	}
	
}