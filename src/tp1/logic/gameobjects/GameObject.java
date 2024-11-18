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
	
	//Abstract methods
	public abstract void update();
	public abstract String getIcon();
	
	//GameItem methods
	@Override
	public boolean isSolid() {
		return false;
	}
	
	@Override
	public boolean isAlive() {
		return isAlive;
	}
	
	@Override
	public boolean isInPosition(Position p) {
		return p.equals(this.pos);
	}
	
	@Override
	public boolean interactWith(Lemming lemming) {
		return false;
	}
	
	@Override
	public boolean interactWith(Wall wall) {
		return false;
	}
	
	@Override
	public boolean interactWith(ExitDoor door) {
		return false;
	}
	
	//Other methods
	//TODO: comprobar si hay algún método que hay que meter en el GameItem
	/*public Position getPos() {
		return this.pos;
	}*/
	
	public void setLife(boolean isALive) {
		this.isAlive = isALive;
	}
	
	public boolean setRole(LemmingRole role) {
		return false;
	}
}
