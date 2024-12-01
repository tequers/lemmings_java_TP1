package tp1.logic.lemmingRoles;

import java.util.Arrays;

import java.util.List;

import tp1.exceptions.RoleParseException;
import tp1.view.Messages;

public class LemmingRoleFactory {

	private static final List<LemmingRole> availableRoles = Arrays.asList(
			new DownCaverRole(),
			new ParachuterRole(),
			new WalkerRole()
			);

	public static LemmingRole parse(String input) throws RoleParseException {
		for (LemmingRole rol : availableRoles) {
			if (rol.parse(input) != null) {
				return rol;
			}
		}
		throw new RoleParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
	}

	public static String helpText() {
		StringBuilder roles = new StringBuilder();
		for (LemmingRole rol : availableRoles) {
			roles.append(rol.helpText());
		}
		return roles.toString();
	}

}
