package tp1.control.commands;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.OffBoardException;
import tp1.exceptions.RoleParseException;
import tp1.logic.Game;
import tp1.logic.GameModel;
import tp1.logic.Position;
import tp1.logic.lemmingRoles.LemmingRole;
import tp1.logic.lemmingRoles.LemmingRoleFactory;
import tp1.view.GameView;
import tp1.view.Messages;

public class SetRoleCommand extends Command{
	
	 private static final String NAME = Messages.COMMAND_SET_ROLE_COMMAND_NAME;
	 private static final String SHORTCUT = Messages.COMMAND_SET_ROLE_COMMAND_SHORTCUT;
	 private static final String DETAILS = Messages.COMMAND_SET_ROLE_COMMAND_DETAILS;
	 private static final String HELP = Messages.COMMAND_SET_ROLE_COMMAND_HELP;
	    
	//
	private Position pos;
	private LemmingRole role;
	//
	public SetRoleCommand() {
		super(NAME,SHORTCUT,DETAILS,HELP);
	}
	
	// Command methods
	@Override
	public void execute(GameModel game, GameView view) throws CommandExecuteException {
	    
		/*String row = this.roleInput[2];
	    int col = Integer.valueOf(this.roleInput[3]);
	    Position pos = positIn(row, col); // Devuelve null si no existe la posición, y pos(row, col) si existe
	    String name = "";*/
		String name = "";
	   
	    try {
	    	 name = role.getName(); //TODO: revisar
	        // Comprobamos si se pudo asignar el rol a un lemming en la posición
	        if (game.setRole(role, pos)) {
	            game.update();
	            view.showGame();
	        } 
	        else {
	        throw new CommandExecuteException( //TODO: cambiar
		            "No lemming in position " +
		            Messages.POSITION.formatted(pos.getRow(), pos.getCol()) +
		            " admits role " + name
		        );                            
	        }
	    }  catch (OffBoardException obe) {
	    	//view.showError(obe.getMessage());
	        throw new CommandExecuteException(
	            Messages.COMMAND_EXECUTE_PROBLEM, obe
	        );
	    }
	}

	
	@Override
	public String helpText(){
		StringBuilder output = new StringBuilder();
		output.append(super.helpText());
		output.append(LemmingRoleFactory.helpText());
		return output.toString();
	}
	
	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		int col = -1;
		try {
			if (this.matchCommandName(commandWords[0])) {

				// this.roleInput = commandWords;
			 //TODO: CASO Ammdd 8
				if (commandWords.length == 4) {
					String row = commandWords[2];
					if (row.length() == 1) {
						col = Integer.valueOf(commandWords[3]); 
						this.pos = new Position(col - 1, letterToIndex(row.charAt(0)));
						this.role = LemmingRoleFactory.parse(commandWords[1]); // Obtenemos el rol
						return this;
						
					}
					throw new CommandParseException(Messages.INVALID_POSITION.formatted
							(Messages.POSITION.formatted(commandWords[2], commandWords[3])));
				}
				throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
				// if (row.length() != 1) throw new NumberFormatException();
				// Position pos = posIn(row, col); //Puede
			}
		} catch (NumberFormatException e) {
			throw new CommandParseException(Messages.INVALID_POSITION.formatted
					(Messages.POSITION.formatted(commandWords[2], commandWords[3])));
		} catch (RoleParseException rpe) {
			// Rol desconocido
			// view.showError(rpe.getMessage());
			throw new CommandParseException(Messages.UNKNOWN_ROLE.formatted(commandWords[1], rpe));
		}
		return null;
	}
	
	private int letterToIndex(char letter) {
		return letter - 'A'; // A -> 0, B -> 1 ...
	}

	
}
