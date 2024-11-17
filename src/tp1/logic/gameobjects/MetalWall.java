package tp1.logic.gameobjects;

import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class MetalWall extends Wall{
	
	private static final String NAME = Messages.METALWALL;
	
	public MetalWall(GameWorld game, Position pos) {
		super(game,pos);
	}

	@Override
	public boolean receiveInteraction(GameItem other) {
		return false;
	}
	
	@Override
	 public String getIcon( ) {
		return this.NAME;
	 }
	
}
