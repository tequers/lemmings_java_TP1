package tp1.logic.gameobjects;

import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.logic.lemmingRoles.LemmingRole;

public abstract class GameObject implements GameItem {
	protected Position pos;
	protected boolean isAlive;
	protected GameWorld game;
	
	public GameObject(GameWorld game, Position pos) {
		this.isAlive = true;
		this.pos = pos;
		this.game = game;
	}
	
	public abstract void update();
	public abstract String getIcon();
	public abstract boolean receiveInteraction(GameItem other);
	
	public Position getPos() {
		return this.pos;
	}
	
	public boolean isInPosition(Position p) {
		return p.equals(this.pos);
	}
 	
	public boolean isAlive() {
		return isAlive;
	}
	
	public boolean isSolid() {
		return false;
	}
	
	public void setLife(boolean isALive) {
		this.isAlive = isALive;
	}
	
	public boolean setRole(LemmingRole role) {
		return false;
	}
	
	public boolean interactWith(Lemming lemming) {
		return false;
	}
	
	public boolean interactWith(Wall wall) {
		return false;
	}
	
	public boolean interactWith(ExitDoor door) {
		return false;
	}
	
}
