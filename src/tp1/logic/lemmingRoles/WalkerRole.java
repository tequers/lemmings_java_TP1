package tp1.logic.lemmingRoles;

import tp1.logic.Direction;
import tp1.logic.gameobjects.Lemming;
import tp1.view.Messages;

public class WalkerRole extends AbstractRole {

	private static final String NAME = Messages.WALKER_ROL_NAME;
	private static final String HELP = Messages.WALKER_ROL_HELP;
	private static final String SYMBOL = Messages.WALKER_ROL_SYMBOL;
	private static final String ICON_RIGHT = Messages.LEMMING_RIGHT;
	private static final String ICON_LEFT = Messages.LEMMING_LEFT;
	private static final String HELP_NAME = Messages.WALKER_ROL_HELP_NAME;

	public WalkerRole() {
		super(NAME, HELP, ICON_RIGHT, SYMBOL, HELP_NAME);
	}

	// LemmingRole methods
	@Override
	public void start(Lemming lemming) {
	}

	@Override
	public void play(Lemming lemming) {
		lemming.walkOrFall();
	}

	@Override
	public String getIcon(Lemming lemming) {
		String icon = "";
		if (lemming.isAlive()) {
			if (lemming.getDir() == Direction.RIGHT) {
				icon = ICON_RIGHT;
			} else {
				icon = ICON_LEFT;
			}
		}
		return icon;
	}

	// Other methods
	@Override
	public String toString() {
		return getName();
	}

}