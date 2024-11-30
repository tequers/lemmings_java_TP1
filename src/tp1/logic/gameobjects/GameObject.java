package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;

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
	protected final  String shortcut;
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

	// 3.0
	public abstract GameObject parse(String line, GameWorld game) 
			throws ObjectParseException, OffBoardException ;
	
	
	//TODO: cambiar las llamadas a estáticas en las sublcases
	public static Position checkPositionFrom(String pos) throws ObjectParseException, OffBoardException {
		String[] p = pos.replace("(", "").replace(")", "").split(",");
		if (p.length == 2) {
			
			try {
				int col =  Integer.valueOf(p[0]);
				int row =  Integer.valueOf(p[1]);
				Position posit = new Position(row,col); //TODO: revisar que excepciones puede lanzar
				if (GameWorld.dentroDelMapa(posit)) {
					return posit;
				}
			} catch(NumberFormatException e) {
				throw new ObjectParseException();
			}
				throw new OffBoardException(); //TODO: RELLENAR MENSAJE
		}
		
		throw new ObjectParseException(); //TODO: RELLENAR MENSAJE
	}
	
	
	public boolean checkObjectNameFrom(String nombreObjeto)  {
		if (nombreObjeto.equalsIgnoreCase(this.name) ||
				nombreObjeto.equalsIgnoreCase(this.shortcut) ) {
			return  true;
		}
		
		return false; //TODO: RELLENAR INFO
		
	}
	

}
