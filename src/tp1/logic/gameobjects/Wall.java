package tp1.logic.gameobjects;

import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Wall extends GameObject {
	
	public Wall(GameWorld game, Position pos) {
		super(game, pos);
	}
	
	@Override
	public boolean isSolid() {
		return true;
	}
	
	public void update() {};
	
	public String getIcon() {
		return Messages.WALL;
	}
	
	@Override
	public boolean receiveInteraction(GameItem other) {
		return other.interactWith(this);
	}
	
}
 