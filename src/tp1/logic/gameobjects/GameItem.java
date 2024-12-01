package tp1.logic.gameobjects;

import tp1.logic.Position;
import tp1.logic.lemmingRoles.LemmingRole;

public interface GameItem {

	public boolean receiveInteraction(GameItem other);
	public boolean interactWith(Lemming lemming);
	public boolean interactWith(Wall wall);
	public boolean interactWith(ExitDoor door);
	public boolean setRole(LemmingRole role);
	public boolean isSolid();
	public boolean isAlive();
	public boolean isInPosition(Position pos);

}
