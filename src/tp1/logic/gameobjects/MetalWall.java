package tp1.logic.gameobjects;

import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class MetalWall extends Wall{
	
	public MetalWall(GameWorld game, Position pos) {
		super(game,pos);
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
	
	
}
