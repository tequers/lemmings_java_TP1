package tp1.logic;

import tp1.logic.lemmingRoles.LemmingRole;

public interface GameModel {
	
	public void update();
	public void reset(int nLevel);
	public void exit();
	public boolean isFinished();
	public boolean setRole(Position pos, LemmingRole role);
	public boolean isLevelValid(int nLevel);
	public int getLevel();
	
}
