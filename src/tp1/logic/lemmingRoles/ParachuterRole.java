package tp1.logic.lemmingRoles;

import tp1.logic.gameobjects.Lemming;
import tp1.view.Messages;

public class ParachuterRole extends AbstractRole {
	
	private static final String NAME = Messages.PARACHUTER_ROL_NAME;
	private static final String HELP = Messages.PARACHUTER_ROL_HELP;
	private static final String ICON = Messages.LEMMING_PARACHUTE;
	private static final String SYMBOL = Messages.PARACHUTER_ROL_SYMBOL;
	private static final String HELP_NAME = Messages.PARACHUTER_ROL_HELP_NAME;
	
	public ParachuterRole() {
		super(NAME,HELP,ICON,SYMBOL,HELP_NAME);
	}
	
	//LemmingRole methods
	@Override
	public void start( Lemming lemming ) {
		lemming.setCurrentFall(0);
	}
	
	@Override
    public void play( Lemming lemming ) {
    	if (lemming.isInAir()) {
    		lemming.fall();
    	} else lemming.disableRole();
    }
    
}
