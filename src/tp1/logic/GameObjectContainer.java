package tp1.logic;

import java.util.ArrayList;
import java.util.List;

import tp1.logic.gameobjects.GameItem;
import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.Lemming;
import tp1.logic.gameobjects.Wall;
import tp1.logic.lemmingRoles.LemmingRole;

public class GameObjectContainer {
	
	private List<GameObject> objects;
	private int cont; 
	private int nLemmingsInBoard;
	
	public GameObjectContainer() {
		this.objects = new ArrayList<GameObject>();
		this.cont = 0;
		this.nLemmingsInBoard = 0;
	}
	
	// Only one add method (polymorphism)
	public void add(GameObject object) {
		this.cont++;
		objects.add(object);
		
	}
	
	public void update() {
		//Actualizar los elementos del array
		for (GameObject o : this.objects) {
			o.update();
		}
		//Eliminar los elementos del array que correspondan
		for (int i = objects.size() - 1; i >= 0; i--) {
		    if (!objects.get(i).isAlive()) {
		    	this.cont--;
		    	objects.remove(i);
		    }
		}
	}
	
	public String positionToString(int col, int row) {
		String ret = "";
		for (GameObject o : this.objects) {
			if (o.isInPosition(new Position(col,row)))  {
				ret += o.getIcon();
			}
		}
		return ret;
	}
	
	public boolean isSolid(Position pos) {
		int i=0;
		while (i < cont ) { 
			if (objects.get(i).isInPosition(pos) && objects.get(i).isSolid()) {
				return true;
			}
			++i;
		}
		return false;
	}
	
	//Interactions
	public boolean receiveInteractionsFrom(GameItem obj) {
		for (GameItem gi: objects) {
			if (gi.receiveInteraction(obj)) {
				return true;
			}
		}
		return false;
	}
		
	//Getters
	public int getLemmingsInBoard() {
		return this.nLemmingsInBoard;
	}
	
	public int getCont() {
		return this.cont;
	}
	
	//Setters
	public void setlemmingsInBoard(int nLemmings){
		this.nLemmingsInBoard = nLemmings;
	}
	
	//Roles
	public boolean setRole(Position pos, LemmingRole role) { 
		int i=0;
		while (i < cont ) { 
			if (objects.get(i).isInPosition(pos) && 
					objects.get(i).setRole(role)) {
					return true;
			}
			++i;
		}
		return false;
	}
	
}
