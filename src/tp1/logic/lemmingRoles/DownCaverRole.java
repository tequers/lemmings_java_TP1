package tp1.logic.lemmingRoles;

import tp1.logic.Direction;

import tp1.logic.gameobjects.Lemming;
import tp1.logic.gameobjects.Wall;
import tp1.view.Messages;

public class DownCaverRole extends AbstractRole {

	private static final String NAME = Messages.DOWN_CAVER_ROL_NAME;
	private static final String HELP = Messages.DOWN_CAVER_ROL_HELP;
	private static final String ICON = Messages.LEMMING_DOWN_CAVER;
	private static final String SYMBOL = Messages.DOWN_CAVER_ROL_SYMBOL;
	private static final String HELP_NAME = Messages.DOWN_CAVER_ROL_HELP_NAME;

	public DownCaverRole() {
		super(NAME, HELP, ICON, SYMBOL, HELP_NAME);
	}
	
	// LemmingRole methods
	@Override
	public void start(Lemming lemming) {
	}

	@Override
	public void play(Lemming lemming) {
	
		if (lemming.getIsInteracting()) {
			lemming.fall();
		} else
			lemming.disableRole();
	}

	@Override
	public boolean interactWith(Wall wall, Lemming lemming) {
		if (wall.isInPosition(lemming.nextPos(Direction.DOWN))) {
			wall.setLife(false);
			return true;
		} else
			return false;
	}
	
	
}
