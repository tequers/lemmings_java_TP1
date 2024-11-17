package tp1.logic.lemmingRoles;

import tp1.logic.Direction;
import tp1.logic.Position;
import tp1.logic.gameobjects.ExitDoor;
import tp1.logic.gameobjects.GameItem;
import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.Lemming;
import tp1.logic.gameobjects.Wall;
import tp1.view.Messages;

public class DownCaverRole extends AbstractRole {
	
	private static final String NAME = Messages.DOWN_CAVER_ROL_NAME;
	private static final String HELP = Messages.DOWN_CAVER_ROL_HELP;
	private static final String ICON = Messages.LEMMING_DOWN_CAVER;
	private static final String SYMBOL = Messages.DOWN_CAVER_ROL_SYMBOL;
	private static final String HELP_NAME = Messages.DOWN_CAVER_ROL_HELP_NAME;
	
	private boolean hasCaved;
	
	public DownCaverRole() {
		super(NAME,HELP,ICON,SYMBOL,HELP_NAME);
	}
	
	public void start( Lemming lemming ) { this.hasCaved = false;};
	
	public void cave(Lemming lemming) {
		this.hasCaved = false;
		if (lemming.getGame().receiveInteractionsFrom(lemming)) {
			lemming.setPos(lemming.nextPos(Direction.DOWN)); //Se podría trasladar a interactWith
			this.hasCaved = true;
		}
	}
	
    public void play( Lemming lemming ) {
    	this.cave(lemming);
    	if (!this.hasCaved) lemming.disableRole();
    }
  
	@Override
	public boolean interactWith(Wall wall, Lemming lemming) {
		if (wall.isInPosition(lemming.nextPos(Direction.DOWN))) {
			wall.setLife(false);
			return true;
		}
		return false;
	}
	
}
