package tp1.logic.gameobjects;

import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class ExitDoor extends GameObject {
	
	public ExitDoor(GameWorld game, Position pos) {
		super(game, pos);
	}
	
	
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
	
}
