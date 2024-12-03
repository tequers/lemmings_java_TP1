package tp1.logic;

import tp1.exceptions.GameLoadException;
import tp1.exceptions.GameModelException;
import tp1.exceptions.OffBoardException;
import tp1.logic.lemmingRoles.LemmingRole;

public interface GameModel {
	
	public void update();
	public void reset(int nLevel) throws GameLoadException;
	public void reset() throws GameLoadException;
	public void exit();
	public void load(String fileName) throws GameLoadException;
	public void save(String fileName) throws GameModelException;
	
	public boolean isFinished();
	public boolean setRole(LemmingRole role, Position pos) throws OffBoardException;
	public boolean isLevelValid(int nLevel);
	
	public int getLevel();
	
}
