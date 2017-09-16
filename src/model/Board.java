package model;

import cmd.Command;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Modélise le plateau de jeu, qui permet de déplacer les véhicules
 * 
 * @inv 0 <= row <= DEFAULT && 0 <= col <= DEFAULT
 * 
 * @cons
 * 
 * 		<pre>
 * $ARGS$ int row, int column, Coord coord
 * $PRE$ 
 *  0 <= row <= DEFAULT
 *  0 <= col <= DEFAULT
 *  exit.equals(EXIT)
 * $POST$ 
 *      getExit().equals(EXIT)
 *      getAllVehicles().isEmpty()
 *       </pre>
 * 
 * @cons
 * 
 * 		<pre>
 * $ARGS$ int row, int col, Coord coord
 * $PRE$ 
 *  0 <= row <= DEFAULT
 *  0 <= col <= DEFAULT
 *  exit.equals(coord)
 * $POST$ 
 *      getExit().equals(coord)
 *      getAllVehicles().isEmpty()
 *       </pre>
 */
public interface Board {

	// CONSTANTES

	/**
	 * Le nombre de lignes et de colonnes par défaut.
	 */
	int DEFAULT = 6;

	/**
	 * les coordonnées pour la sortie par défaut.
	 */
	Coord EXIT = new StdCoord(2, 5);

	// REQUETES

	/**
	 * Indique la sortie de l'embouteillage.
	 */
	Coord getExit();

	/**
	 * Indique si le jeu est gagné c-a-d si la voiture rouge est sur la sortie.
	 */
	boolean hasWon();

	/**
	 * retourne le nombre de lignes sur le plateau.
	 */
	int getRowNb();

	/**
	 * retourne le nombre de colonnes sur le plateau.
	 */
	int getColNb();

	/**
	 * Renvoie l'historique du plateau.
	 */
	History<Command> getHistory();

	/**
	 * Retourne de nombre de annuls possibles.
	 */
	int nbOfPossibleUndo();

	/**
	 * Retourne de nombre de réetablissements possibles.
	 */
	int nbOfPossibleRedo();

	/**
	 * Renvoie l'ensemble de tous les véhicules qui se trouvent sur le plateau.
	 */
	Set<Vehicle> getAllVehicles();

	/**
	 * Renvoie le vehicule dont une partie se trouve à cette coordonnée coord.
	 * 
	 * @pre coord != null
	 */
	Vehicle getVehicle(Coord coord);
	
	/**
	 * Indique le nombre de fichier sans nom
	 * @return nombre de fichier sans nom
	 */
	int getNbFileWithoutName();

	/**
	 * Renvoie les coordonnées associées au véhicule vehicle.
	 * 
	 * @pre vehicle != null getAllVehicles().contains(vehicle)
	 */
	Coord getCoord(Vehicle vehicle);

	/**
	 * Indique s'il y a un véhicule les coordonnées coord est vide.
	 */
	boolean isFree(Coord coord);
	
	/**
	 * Indique si le v�hicule peut �tre d�poser
	 */
	boolean isFree(Vehicle v, Coord coord);
	
	/**
	 * Ajoute un véhicle à la coordonnée donnée
	 */
	void addVehicle(Vehicle veh, Coord coord);
	
	/**
	 * Supprime un véhicle à la coordonnée donnée
	 */
	void removeVehicle(Vehicle veh, Coord coord);
	
	/**
	 * Supprime tout les véhicules sur le plateau mais ne remet pas la voiture rouge à sa position d'origne : Coord(2,0).
	 */
	void clear();

	/**
	 * Indique si le véhicule v peut se déplacer sur cette coordonnée coord.
	 * 
	 * @pre coord != null vehicle != null getAllVehicles().contains(vehicle)
	 * 
	 */
	boolean canMoveTo(Vehicle vehicle, Coord coord);

	/**
	 * Indique si la colonne est valide.
	 */
	boolean isValidCol(int col);

	/**
	 * Indique si la ligne est valide.
	 */
	boolean isValidRow(int row);
	
	/**
	 * Indique la coordonnée du premier véhicule rencontré en reculant
	 */
	Coord getFirstVehicleBefore(Vehicle veh);
	
	/**
	 * Indique la coordonnér du permier véhicule rencontré en avançant
	 */
	Coord getFirstVehicleAfter(Vehicle veh);
	
	/**
	 * Créé un clone du plateau.
	 * Clone le plateau, les maps, mais par les véhicules ni les coordonnées.
	 */
	Object clone();

	/**
	 * Applique la commande et l'enregistre dans l'historique.
	 */
	Board doCommand(Command command);

	/**
	 * Renvoie une liste des coordonnées alignées avec le vehicle passé en
	 * argument
	 */
	List<Coord> AlignedCoordWith(Vehicle vehicle);

	/**
	 * Renvoie une liste de commande que le véhicule est capable de faire.
	 */
	List<Command> mvtList(Vehicle vehicle, List<Coord> coordList3);

	/**
	 * Renvoie un tableau de mouvement si il est possible de terminer cette
	 * carte null sinon. Le tableau indique chaque mouvement qu'il faut faire
	 * les uns à la suite des autres pour finir la carte.
	 */
	List<Command> solve();
	
	/**
	 * Retourne <b>TRUE</b> si toutes coordonnées sur le trajet du véhicule
	 * sont vides, <b>FALSE</b> sinon.
	 */
	boolean checkAllFreeCoords(Vehicle vehicle, Coord coord);

	/**
	 * Retourne la Map des véhicules.
	 * @return
	 */
	Map<Vehicle, Coord> getVehicleMap();

	// COMMANDES

	/**
	 * Déplace le véhicule vechicle vers cette coordonnée coord.
	 * 
	 * @pre vehicle != null coord != null canMoveTo(vehicle, coord)
	 * @post getVehicle(coord) == v getCoord(vehicle) == c
	 */
	void moveTo(Vehicle vehicle, Coord coord);

	/**
	 * Place tous les véhicules de la carte card sur le plateau.
	 * 
	 * @pre getAllVehicles().isEmpty()
	 * @post for all vehicle in Card getCoord(vehicle) != null && ((0,0) <=
	 *       getCoord(vehicle) <= DEFAULT || (0,0) <= getCoord(vehicle) <= MAX)
	 *
	 */
	void placeVehicles(Card card);

	/**
	 * réer une carte.
	 * 
	 * @pre file != null card != null
	 * @post le fichier carte est crée
	 *
	 */
	void writeInFile(File file, Card card);

	/**
	 * Refaire une action.
	 */
	void redo();

	/**
	 * Annuler une action.
	 */
	void undo();

	/**
	 * Fait le déplacement du véhicule dans le modèle.
	 */
	void moveAux(Vehicle vehicle, Coord newCoord);
}