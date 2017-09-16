package cmd;

import model.Board;
import util.Contract;

public abstract class AbstractCommand implements Command {

	// ATTRIBUTS

	private State state;

	// CONSTRUCTEUR

	protected AbstractCommand() {
		state = State.DO;
	}

	// REQUETES

	@Override
	public State getState() {
		return state;
	}

	@Override
	public boolean canDo(Board b) {
		return state == State.DO;
	}

	@Override
	public boolean canUndo(Board b) {
		return state == State.UNDO;
	}

	// COMMANDES

	@Override
	public void act(Board b) {
		Contract.checkCondition(canDo(b) || canUndo(b), "Action pas possible");
		if (canDo(b)) {
			doIt(b);
			state = State.UNDO;
		} else {
			undoIt(b);
			state = State.DO;
		}
	}

	/**
	 * Cette méthode doit être redéfinie dans les sous-classes, de sorte
	 * qu'elle implante l'action à réaliser pour exécuter la commande. Elle
	 * est appelée par act() et ne doit pas être appelée directement.
	 * 
	 * @pre canDo()
	 * @post La commande a été exécutée
	 */
	protected abstract void doIt(Board b);

	/**
	 * Cette méthode doit être redéfinie dans les sous-classes, de sorte
	 * qu'elle implante l'action à réaliser pour annuler la commande. Si
	 * l'état du texte correspond à celui dans lequel il était après doIt,
	 * alors undoIt rétablit le texte dans l'état où il était avant
	 * l'exécution de doIt. Elle est appelée par act() et ne doit pas être
	 * appelée directement.
	 * 
	 * @pre canUndo()
	 * @post La commande a été annulée
	 */
	protected abstract void undoIt(Board b);

}
