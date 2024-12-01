package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.GameWorld;

import tp1.logic.Position;
import tp1.view.Messages;

public class ExitDoor extends GameObject {
	
	private final static String name = Messages.EXIT_DOOR_NAME;
	private  final static String shortcut = Messages.EXIT_DOOR_SHORTCUT;
	

	public ExitDoor(GameWorld game, Position pos) {
		super(game, pos, name, shortcut);
	}
	
	//GameObject methods
	@Override
	public void update() {};
	
	@Override
	public String getIcon() {
		return Messages.EXIT_DOOR;
	}
	
	@Override
	public boolean isSolid() {
		return false;
	}
	
	//GameItem methods
	@Override
	public boolean receiveInteraction(GameItem other) {
		return other.interactWith(this);
	}
	
	@Override
	public GameObject parse(String line, GameWorld game) 
			throws ObjectParseException, OffBoardException {
		
	
		String[] words = line.trim().split("\\s+");
		Position pos = super.checkPositionFrom(words[0], line);
		if (super.checkObjectNameFrom(words[1])) {
			return new ExitDoor(game, pos);
		}
	
		return null;
		
	}
}
