package tp1.control.commands;

import tp1.exceptions.CommandExecuteException;
import tp1.exceptions.CommandParseException;
import tp1.exceptions.GameModelException;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class SaveCommand extends Command{
	
	private static final String NAME = Messages.COMMAND_SAVE_COMMAND_NAME;
	private static final String SHORTCUT = Messages.COMMAND_SAVE_COMMAND_SHORTCUT;
	private static final String DETAILS = Messages.COMMAND_SAVE_COMMAND_DETAILS;
	private static final String HELP = Messages.COMMAND_SAVE_COMMAND_HELP;
	
	private String fileName;
	
	public SaveCommand() {
		super(NAME,SHORTCUT,DETAILS,HELP);
	}
	
	@Override
	public  void execute(GameModel game, GameView view) 
			throws CommandExecuteException {
		try {
			game.save(fileName);
			game.update();
			view.showGame();
		} catch (GameModelException e) {
			throw new CommandExecuteException(); //TODO: 
		}
	}
	
	@Override
	public Command parse(String[] commandWords) 
			throws CommandParseException {
		if(commandWords.length == 2 &&
				matchCommandName(commandWords[0])) {
			this.fileName = commandWords[1];
			return this;
		}
		return null;
	}

}
