package tp1.logic;


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
