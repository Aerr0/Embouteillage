package cmd;

import model.Board;
import model.Coord;
import model.Vehicle;
import util.Contract;

public class Move extends AbstractCommand {

	// ATTRIBUTS

	private Vehicle vehicle;
	private Coord newCoord;
	private Coord oldCoord;

	// CONSTRUCTEUR

	/**
	 * Création d'une commande GoForward.
	 */
	public Move(Vehicle vehicle, Coord newCoord, Coord oldCoord) {
		super();
		Contract.checkCondition(vehicle != null && newCoord != null && oldCoord != null);
		this.vehicle = vehicle;
		this.newCoord = newCoord;
		this.oldCoord = oldCoord;
	}

	// REQUETES

	public Vehicle getVehicle() {
		return vehicle;
	}

	public Coord getNewCoord() {
		return newCoord;
	}

	public Coord getOldCoord() {
		return oldCoord;
	}

	@Override
	public boolean canDo(Board b) {
		return super.canDo(b) && b.canMoveTo(vehicle, newCoord);
	}

	@Override
	public boolean canUndo(Board b) {
		return super.canUndo(b) && b.canMoveTo(vehicle, oldCoord);
	}

	public String toString() {
		return "" + getVehicle().getId() + getNewCoord();
	}

	// COMMANDES

	@Override
	public void doIt(Board b) {
		Contract.checkCondition(canDo(b));
		b.moveAux(vehicle, newCoord);
	}

	@Override
	public void undoIt(Board b) {
		Contract.checkCondition(canUndo(b));
		b.moveAux(vehicle, oldCoord);
	}
}
