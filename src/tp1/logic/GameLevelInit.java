package tp1.logic;

import tp1.logic.gameobjects.ExitDoor;
import tp1.logic.gameobjects.Lemming;
import tp1.logic.gameobjects.MetalWall;
import tp1.logic.gameobjects.Wall;
import tp1.logic.lemmingRoles.ParachuterRole;
import tp1.logic.lemmingRoles.WalkerRole;

public class GameLevelInit implements GameConfiguration {
	
	
	private GameObjectContainer container;
	private int nCycle;
	private int nLemmingsExit;
	private int nLemmingsToWin;
	private int nLemmingsInBoard;
	private int nLemmingsDead;
	private int nLevel;
	
	 public GameLevelInit(int nLevel) {
		 
	 }
	 
	 public GameLevelInit() {
		 
	 }
	 
	 
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
	public GameObjectContainer getGameObjects() {
		GameObjectContainer goc = new GameObjectContainer();
		goc = this.container;
		return goc;
	}
	
/*
	//Initialization of levels
	public void init(int nLevel){
		//INICIALIZACIÓN VARIABLES 
		this.nCycle = 0;
		this.nLevel = nLevel;
		this.nLemmingsExit = 0;
		this.nLemmingsToWin = 2;
		this.nLemmingsDead = 0;
		//AÑADIR CONTAINER
		container = new GameObjectContainer();
		//AÑADIR EXIT DOOR	
		container.add( new ExitDoor(this, new Position(4,5)));
		//AÑADIR LEMMINGS
		initLemmings();
		//AÑADIR WALLS
		initWalls();
		//INICIALIZAR LEMMINGS IN BOARD
		this.nLemmingsInBoard = container.getLemmingsInBoard();
	}
	
	private void initWalls() {
		container.add(new Wall(this,new Position(2,4)));
		container.add(new Wall(this,new Position(3,4)));
		container.add(new Wall(this,new Position(4,4)));
		container.add(new Wall(this,new Position(0,9)));		
		container.add(new Wall(this,new Position(1,9)));		
		container.add(new Wall(this,new Position(8,9)));		
		container.add(new Wall(this,new Position(9,9)));		
		container.add(new Wall(this,new Position(8,1)));		
		container.add(new Wall(this,new Position(9,1)));
		container.add(new Wall(this,new Position(8,8)));		
		container.add(new Wall(this,new Position(4,6)));	
		container.add(new Wall(this,new Position(5,6)));
		container.add(new Wall(this,new Position(6,6)));
		
		if (this.nLevel == 2) {
			container.add(new Wall(this,new Position(3,5)));
			container.add(new MetalWall(this,new Position(3,6)));
			container.add(new Wall(this,new Position(7,5)));
			container.add(new Wall(this,new Position(7,6)));
		} else {
			container.add(new Wall(this,new Position(7,6)));		
			container.add(new Wall(this,new Position(7,5)));	
		}
	}
	
	private void initLemmings() {
		container.add(new Lemming( this, new Position(2,3), new WalkerRole())); 
		container.add(new Lemming( this, new Position(9,0),new WalkerRole())); 
		container.add(new Lemming( this, new Position(0,8),new WalkerRole()));
		container.setlemmingsInBoard(3);
		
		if (nLevel == 2) {
			container.add(new Lemming( this, new Position(3,3), new WalkerRole()));
			container.add(new Lemming( this, new Position(6,0), new WalkerRole()));
			container.add(new Lemming( this, new Position(6,0), new ParachuterRole()));
			container.setlemmingsInBoard(6);
		} else {
			container.add(new Wall(this,new Position(7,6)));		
			container.add(new Wall(this,new Position(7,5)));	
			if (nLevel == 1) {
				container.add(new Lemming( this, new Position(3,3), new WalkerRole()));
				container.setlemmingsInBoard(4);
			}
		}
	}
	*/
}
