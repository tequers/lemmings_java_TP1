package tp1.control.commands;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.GameLoadException;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class LoadCommand extends Command{
	
	
	private static final String NAME = Messages.COMMAND_LOAD_COMMAND_NAME;
	private static final String SHORTCUT = Messages.COMMAND_LOAD_COMMAND_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_LOAD_COMMAND_DETAILS;
	private static final String HELP = Messages.COMMAND_LOAD_COMMAND_HELP;
	private String fileName;
	
	public LoadCommand() {
		super(NAME,SHORTCUT,DETAILS,HELP);
	}
	
	@Override
	public void execute(GameModel game, GameView view) 
			throws CommandExecuteException {
		try {
			game.load(this.fileName);
			 game.update();
	         view.showGame();
		} catch (GameLoadException gle) {
			throw new CommandExecuteException(Messages.INVALID_FILE_CONFIG.formatted(fileName), gle); //TODO: RELLENAR
		}
	}
	
	@Override
	public Command parse(String[] commandWords) 
			throws CommandParseException {
		if (matchCommandName(commandWords[0])) {
			if (commandWords.length == 2) {
				this.fileName = commandWords[1];
				return this;
			}
			throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
		}
		return null;
	}

}
