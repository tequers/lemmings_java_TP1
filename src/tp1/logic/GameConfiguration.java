package tp1.logic;

import tp1.exceptions.GameLoadException;

public interface GameConfiguration {
	// game status
	   public int getCycle();
	   public int numLemmingsInBoard();
	   public int numLemmingsDead();
	   public int numLemingsExit();
	   public int numLemmingToWin();
	   // game objects
	   public GameObjectContainer getGameObjects();
}
