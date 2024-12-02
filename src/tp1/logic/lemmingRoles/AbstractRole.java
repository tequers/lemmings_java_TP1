package tp1.logic.lemmingRoles;

import tp1.logic.gameobjects.ExitDoor;
import tp1.logic.gameobjects.GameItem;
import tp1.logic.gameobjects.Lemming;
import tp1.logic.gameobjects.Wall;
import tp1.view.Messages;

public abstract class AbstractRole implements LemmingRole {

	private final String NAME;
	private final String HELP;
	private final String ICON;
	private final String SYMBOL;
	private final String HELP_NAME;
	//
	private boolean isInteracting;
	//
	public AbstractRole(String name, String help, 
			String icon, String symbol, String help_name) {
		this.NAME = name;
		this.HELP = help;
		this.ICON = icon;
		this.SYMBOL = symbol;
		this.HELP_NAME = help_name;
		this.isInteracting = false;
	}

	// LemmingRole methods
	@Override
	public String getIcon(Lemming lemming) {
		return this.ICON;
	}

	@Override
	public String getName() {
		return this.NAME;
	}

	@Override
	public String getSymbol() {
		return this.SYMBOL;
	}

	@Override
	public String getHelp() {
		return this.HELP;
	}

	@Override
	public String getHelpName() {
		return this.HELP_NAME;
	}

	@Override
	public String helpText() {
		return Messages.LINE_TAB
				.formatted("\t  " + Messages.COMMAND_HELP_TEXT.formatted(this.getHelpName(), this.getHelp()));
	}

	@Override
	public boolean receiveInteraction(GameItem other, Lemming lemming) {
		return false;
	}

	@Override
	public boolean interactWith(Lemming receiver, Lemming lemming) {
		return false;
	}

	@Override
	public boolean interactWith(Wall wall, Lemming lemming) {
		return false;
	}

	@Override
	public boolean interactWith(ExitDoor door, Lemming lemming) {
		return false;
	}

	@Override
	public LemmingRole parse(String input) {
		if (this.matchCommandName(input)) {
			return this;
		}
		return null;
	};

	// Other methods
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		return this.getClass() == obj.getClass();
	}

	private boolean matchCommandName(String input) {
		return this.getSymbol().equalsIgnoreCase(input) || this.getName().equalsIgnoreCase(input);
	}
	
	@Override
	public String toString() {
		return this.NAME;
	}
	
	public  void setIsInteracting(boolean isInteracting) {
		this.isInteracting = isInteracting;
	}
	
	public boolean getIsInteracting() {
		return this.isInteracting;
	}
}
