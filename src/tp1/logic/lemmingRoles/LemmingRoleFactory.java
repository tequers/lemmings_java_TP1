package tp1.logic.lemmingRoles;

import java.util.Arrays;
import java.util.List;

import tp1.control.commands.Command;
import tp1.view.GameView;

public class LemmingRoleFactory {
	
	private static final List<LemmingRole> availableRoles = Arrays.asList(
			new DownCaverRole(),
			new ParachuterRole(),
			new WalkerRole()
	);
	
	public static LemmingRole parse(String input) {
		for (LemmingRole rol: availableRoles) {
			if (rol.parse(input) != null) {
				return rol;
			}
		}
		return null;
	}
	
	public static String helpText() {
		StringBuilder roles = new StringBuilder();
		for (LemmingRole rol: availableRoles) {
			roles.append(rol.helpText());
		}
		return roles.toString(); 
	}
	
}
