package tp1.logic.lemmingRoles;

import tp1.logic.gameobjects.ExitDoor;
import tp1.logic.gameobjects.GameItem;
import tp1.logic.gameobjects.Lemming;
import tp1.logic.gameobjects.Wall;
import tp1.view.Messages;

public abstract class AbstractRole implements LemmingRole{
	
	private final String NAME ;
	private  final String HELP  ;
	private  final String ICON  ;
	private  final String SYMBOL  ;
	private  final String HELP_NAME ;
	
	public AbstractRole(String name, String help, String icon, String symbol,
			String help_name) {
		this.NAME = name;
		this.HELP = help;
		this.ICON = icon;
		this.SYMBOL = symbol;
		this.HELP_NAME = help_name;;
	}
	
    public String getIcon( Lemming lemming ) {
    	return this.ICON;
    }
    
	public String getName() {
		return this.NAME;
	}
	
	public String getSymbol() {
		return this.SYMBOL;
	}
	public String getHelp() {
		return this.HELP;
	}
	
	public String getHelpName() {
		return this.HELP_NAME;
	}
	
	public boolean receiveInteraction(GameItem other, Lemming lemming) {
		return false;
	}

	public boolean interactWith(Lemming receiver, Lemming lemming) {
		return false;
	}
	public boolean interactWith(Wall wall, Lemming lemming) {
		return false;
	}
	public boolean interactWith(ExitDoor door, Lemming lemming) {
		return false;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		return this.getClass() == obj.getClass();
	}
	
	public LemmingRole parse(String input) {
		if (this.matchCommandName(input)) {
			return this;
		} 
		return null;
	};
	
	public  String helpText(){
		return Messages.LINE_TAB.formatted(
				"\t  " + Messages.COMMAND_HELP_TEXT.formatted(this.getHelpName(), this.getHelp()));
	}
	
	private boolean matchCommandName(String input) {
		return this.getSymbol().equalsIgnoreCase(input) || 
				this.getName().equalsIgnoreCase(input);
	}
}
