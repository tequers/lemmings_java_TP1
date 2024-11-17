package tp1.control.commands;

public abstract class NoParamsCommand extends Command {

	public NoParamsCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
	}

	@Override
	public Command parse(String[] commandWords) {
		if (commandWords.length == 1 && (this.matchCommandName(commandWords[0]))) {
				return this;
		}
		return null;
	}
	
}
