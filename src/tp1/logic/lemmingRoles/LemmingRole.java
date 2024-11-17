package tp1.logic.lemmingRoles;

import tp1.logic.gameobjects.ExitDoor;
import tp1.logic.gameobjects.GameItem;
import tp1.logic.gameobjects.Lemming;
import tp1.logic.gameobjects.Wall;
import tp1.view.Messages;

public interface LemmingRole {
	
		public void start( Lemming lemming );
	    public void play( Lemming lemming );
	    
	    public String getIcon( Lemming lemming );
		public String getName();
		public String getSymbol();
		public String getHelp();
		public String getHelpName();
		
		public static LemmingRole parse(String input, LemmingRole rol) {
			if (LemmingRole.matchCommandName(input, rol)) {
				return rol;
			} 
			return null;
		};
		
		public static String helpText(LemmingRole rol){
			return Messages.LINE_TAB.formatted(
					"\t  " + Messages.COMMAND_HELP_TEXT.formatted(rol.getHelpName(), rol.getHelp()));
		}
		
		public static boolean matchCommandName(String input, LemmingRole role) {
			return role.getSymbol().equalsIgnoreCase(input) || 
					role.getName().equalsIgnoreCase(input);
		}
		
		public boolean receiveInteraction(GameItem other, Lemming lemming);
		public boolean interactWith(Lemming receiver, Lemming lemming);
		public boolean interactWith(Wall wall, Lemming lemming);
		public boolean interactWith(ExitDoor door, Lemming lemming);
		
}
 