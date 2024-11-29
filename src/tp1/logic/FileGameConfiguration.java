package tp1.logic;

import tp1.exceptions.GameLoadException;

public class FileGameConfiguration implements GameConfiguration{
	

	private GameObjectContainer container;
	private int nCycle;
	private int nLemmingsExit;
	private int nLemmingsToWin;
	private int nLemmingsInBoard;
	private int nLemmingsDead;
	private int nLevel;
	
	public FileGameConfiguration(String fileName, GameWorld game)
			throws GameLoadException {
		
	}
	   //
	   @Override
		public int getCycle() {
			return this.nCycle;
		}

		@Override
		public int numLemmingsInBoard() { 
			return this.nLemmingsInBoard;
		}

		@Override
		public int numLemmingsDead() {
			return this.nLemmingsDead;
		}

		@Override  
		public int numLemingsExit() {
			return this.nLemmingsExit;
		}

		@Override
		public int numLemmingToWin() {
			return this.nLemmingsToWin;
		}

	// game objects
		public GameObjectContainer getGameObjects() 
		   {
			   return new GameObjectContainer();//TODO: completar
		   }
		}
		   //load



