package tp1.logic.gameobjects;

import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Wall extends GameObject {
	
	private final static String name = Messages.WALL;
	private final static String shortcut = Messages.WALL_SHORTCUT;
	
	public Wall(GameWorld game, Position pos) {
		super(game, pos, name, shortcut);
	}
	
	//GameObject methods
	@Override
	public boolean isSolid() {
		return true;
	}
	
	@Override
	public void update() {};
	
	@Override
	public String getIcon() {
		return Messages.WALL;
	}
	
	//GameItem methods
	@Override
	public boolean receiveInteraction(GameItem other) {
		return other.interactWith(this);
	}
	
}
 