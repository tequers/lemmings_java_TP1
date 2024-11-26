package tp1.control.commands;

import tp1.exceptions.CommandParseException;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class ResetCommand extends Command{

    private static final String NAME = Messages.COMMAND_RESET_NAME;
    private static final String SHORTCUT = Messages.COMMAND_RESET_SHORTCUT;
    private static final String DETAILS = Messages.COMMAND_RESET_DETAILS;
    private static final String HELP = Messages.COMMAND_RESET_HELP;
    private String nLevel;
    
	public ResetCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}
	
	
	@Override
	public void execute(GameModel game, GameView view) {
		if (this.nLevel == null) {
			game.reset(game.getLevel());
			view.showGame();
		} else if (game.isLevelValid(Integer.valueOf(this.nLevel))) {
			game.reset(Integer.valueOf(this.nLevel));
			view.showGame();
		} else view.showError(Messages.INVALID_LEVEL_NUMBER );
	}
	
	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		if (this.matchCommandName(commandWords[0])) {
			if (commandWords.length == 2 || commandWords.length == 1) {
				if (commandWords.length ==2) this.nLevel = commandWords[1];
				
				return this;
			} 
			throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
 
		} 
		return null;
	}
	
}
