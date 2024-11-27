package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.ObjectParserException;
import tp1.exceptions.OffBoardException;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.logic.lemmingRoles.LemmingRole;

public abstract class GameObject implements GameItem {
	protected Position pos;
	protected boolean isAlive;
	protected GameWorld game;
	
	//3.0
	protected final String name;
	protected final String shortcut;
	//
	
	public GameObject(GameWorld game, Position pos, String name, String shortcut) {
		this.isAlive = true;
		this.pos = pos;
		this.game = game;
		this.name = name;
		this.shortcut = shortcut;
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
	public void setLife(boolean isALive) {
		this.isAlive = isALive;
	}
	
	@Override
	public boolean setRole(LemmingRole role) {
		return false;
	}
	
	//3.0
	public GameObject parse(String line, GameWorld game) 
			throws ObjectParseException, OffBoardException {
		
	}
	
	private static Position getPositionFrom(String line) throws ObjectParseException, OffBoardException {...}
	private static String getObjectNameFrom(String line) throws ObjectParseException {}
}
