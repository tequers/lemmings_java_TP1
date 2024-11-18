package tp1.control.commands;

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
	    
	private String[] roleInput;
	
	public SetRoleCommand() {
		super(NAME,SHORTCUT,DETAILS,HELP);
	}
	
	//Command methods
	@Override
	public void execute(GameModel game, GameView view) {
		String row = this.roleInput[2];
		int col = Integer.valueOf(this.roleInput[3]);  
		Position pos = posIn(row, col);//Devuelva null si no existe la posición,y pos(row,col) si existe
		
		if (pos != null) { //Comprobamos si la posición es válida
			LemmingRole role = LemmingRoleFactory.parse(this.roleInput[1]) ;
			if (role != null) { //Comprobamos si el rol es valido
				if (game.setRole(pos, role)) { //Comprobamos si se ha podido asginar el role a algún lemming en la pos
						game.update();
						view.showGame();
				}  else view.showError(Messages.SET_ROLE_COMMAND_INCORRECT_PARAMETERS);
				
			} else view.showError(Messages.UNKNOWN_ROLE);
			
		} else view.showError(Messages.SET_ROLE_COMMAND_INCORRECT_PARAMETERS);
	}
	
	@Override
	public String helpText(){
		StringBuilder output = new StringBuilder();
		output.append(super.helpText());
		output.append(LemmingRoleFactory.helpText());
		return output.toString();
	}
	
	@Override
	public  Command parse(String[] commandWords) {
		if (this.matchCommandName(commandWords[0])) {
			this.roleInput = commandWords;
			return this;
		} else return null;
		
	}
	
	//Other methods
	private boolean rowIsValid(String letter) {
		if (letter.length() != 1) {
            return false;
        }
		char ch = letter.toUpperCase().charAt(0);
		return ch >= 'A' && ch <= 'J';
	}
	
	private boolean colIsValid(int col) {
		return col >= 1 && col <= Game.DIM_X;
	}
	
	private int letterToIndex(char letter) {
		return letter - 'A'; // A -> 0, B -> 1 ...
	}
	
	private Position posIn(String row, int col) {
			if (rowIsValid(row) && colIsValid(col)) {
				return new Position(col-1,letterToIndex(row.toUpperCase().charAt(0)));
			}
		return null;
	}
	
	
}
