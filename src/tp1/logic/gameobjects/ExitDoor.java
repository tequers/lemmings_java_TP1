package tp1.logic.gameobjects;

import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class ExitDoor extends GameObject {
	
	public ExitDoor(GameWorld game, Position pos) {
		super(game, pos);
	}
	
	public void update() {};
	
	public String getIcon() {
		return Messages.EXIT_DOOR;
	}
	
	@Override
	public boolean isSolid() {
		return false;
	}
	
	public boolean receiveInteraction(GameItem other) {
		return other.interactWith(this);
	}
	
}
