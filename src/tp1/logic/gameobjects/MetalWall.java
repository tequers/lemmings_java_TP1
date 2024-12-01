package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class MetalWall extends GameObject{
	

	private final static String name = Messages.METAL_WALL_NAME;
	private final static String shortcut = Messages.METAL_WALL_SHORTCUT;
	
	public MetalWall(GameWorld game, Position pos) {
		super(game,pos, name, shortcut);
	}

	//GameObject methods
	@Override
	 public String getIcon( ) {
		return Messages.METALWALL;
	 }
	
	//GameItem methods
	@Override
	public boolean receiveInteraction(GameItem other) {
		return false;
	}
	
	//GameObject methods
	@Override
	public boolean isSolid() {
		return true;
	}
	
	@Override
	public void update() {};
	
	@Override
	public GameObject parse(String line, GameWorld game) 
			throws ObjectParseException, OffBoardException {
		
	
		String[] words = line.trim().split("\\s+");
		Position pos = super.checkPositionFrom(words[0], line); 
		if (super.checkObjectNameFrom(words[1])) {
			return new MetalWall(game, pos);
		}
	
		return null;
		
	}
	
}
