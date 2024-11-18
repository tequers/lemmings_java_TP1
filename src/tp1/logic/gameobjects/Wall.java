package tp1.logic.gameobjects;

import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Wall extends GameObject {
	
	public Wall(GameWorld game, Position pos) {
		super(game, pos);
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
 