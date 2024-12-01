package tp1.logic;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import tp1.exceptions.GameLoadException;
import tp1.exceptions.GameModelException;
import tp1.exceptions.OffBoardException;
import tp1.logic.gameobjects.ExitDoor;

import tp1.logic.gameobjects.GameItem;
import tp1.logic.gameobjects.Lemming;
import tp1.logic.gameobjects.MetalWall;
import tp1.logic.gameobjects.Wall;
import tp1.logic.lemmingRoles.LemmingRole;
import tp1.logic.lemmingRoles.ParachuterRole;
import tp1.logic.lemmingRoles.WalkerRole;
import tp1.view.Messages;

public class Game implements GameStatus, GameModel, GameWorld {

	public static final int DIM_X = 10;
	public static final int DIM_Y = 10;
	private final int maxLevel = 2;

	private GameObjectContainer container;
	private int nCycle;
	private int nLemmingsExit;
	private int nLemmingsToWin;
	private int nLemmingsInBoard;
	private int nLemmingsDead;
	private int nLevel;

	private boolean fin;
	private String nameConfig;

	public Game(int nLevel) {
		this.nLevel = nLevel;
		init(nLevel);
		this.fin = false;

	}

//load 


	public void load(String fileName) throws GameLoadException {
		FileGameConfiguration fnc = new FileGameConfiguration(fileName, this);
		this.nCycle = fnc.getCycle();
		this.nLemmingsExit = fnc.numLemingsExit();
		this.nLemmingsToWin = fnc.numLemmingToWin();
		this.nLemmingsInBoard = fnc.numLemmingsInBoard();
		this.nLemmingsDead = fnc.numLemmingsDead();
		this.container = fnc.getGameObjects();
		this.nameConfig = fileName;
	}

	// TODO: load
// GameStatus methods
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
	public int numLemmingsExit() {
		return this.nLemmingsExit;
	}

	@Override
	public int numLemmingsToWin() {
		return this.nLemmingsToWin;
	}

	@Override
	public String positionToString(int col, int row) {

		String str = "";
		if (container.getCont() > 0) {
			str = container.positionToString(col, row);
		}
		return str;
	}

	@Override
	public boolean playerWins() {
		return this.nLemmingsInBoard == 0 && this.nLemmingsExit >= this.nLemmingsToWin;
	}

	@Override
	public boolean playerLooses() {
		return !playerWins() && this.nLemmingsInBoard == 0;
	}

// GameModel methods
	@Override
	public void update() {
		this.nCycle++;
		this.container.update();
	}

	@Override
	public void reset(int nLevel) throws GameLoadException {
		if (this.nameConfig != null) { // TODO: se podría poner como un try
			this.load(this.nameConfig);
		} else
			init(nLevel);
	}

	@Override
	public void exit() {
		this.fin = true;
	}

	@Override
	public boolean isFinished() {
		return playerWins() || playerLooses() || fin;
	}

	@Override
	public boolean setRole(LemmingRole role, Position pos) throws OffBoardException {

		if (GameWorld.dentroDelMapa(pos)) {
			return this.container.setRole(pos, role);
		}

		throw new OffBoardException(Messages.POSITION_IS_OFF_BOARD.formatted(pos.getRow(), pos.getCol()));

	}

	@Override
	public boolean isLevelValid(int nLevel) {
		return nLevel >= 0 && nLevel <= this.maxLevel;
	}

	@Override
	public int getLevel() {
		return this.nLevel;
	}

// GameWorld methods (callbacks)
	@Override
	public void lemmingArrived() {
		this.nLemmingsInBoard--;
		this.nLemmingsExit++;
	}

	@Override
	public void addDeadLemming() {
		this.nLemmingsInBoard--;
		this.nLemmingsDead++;
	}

	@Override
	public boolean isInAir(Position pos) {
		return (!this.isSolid(new Position(pos.getCol(), pos.getRow() + 1)));
	}

	@Override
	public boolean isSolid(Position pos) {
		return GameWorld.dentroDelMapa(pos) && this.container.isSolid(pos);
	}

	@Override
	public boolean receiveInteractionsFrom(GameItem obj) {
		return this.container.receiveInteractionsFrom(obj);
	}

	// Initialization of levels
	public void init(int nLevel) {
		// INICIALIZACIÓN VARIABLES
		this.nCycle = 0;
		this.nLevel = nLevel;
		this.nLemmingsExit = 0;
		this.nLemmingsToWin = 2;
		this.nLemmingsDead = 0;
		// AÑADIR CONTAINER
		container = new GameObjectContainer();
		// AÑADIR EXIT DOOR
		container.add(new ExitDoor(this, new Position(4, 5)));
		// AÑADIR LEMMINGS
		initLemmings();
		// AÑADIR WALLS
		initWalls();
		// INICIALIZAR LEMMINGS IN BOARD
		this.nLemmingsInBoard = container.getLemmingsInBoard();
	}

	private void initWalls() {
		container.add(new Wall(this, new Position(2, 4)));
		container.add(new Wall(this, new Position(3, 4)));
		container.add(new Wall(this, new Position(4, 4)));
		container.add(new Wall(this, new Position(0, 9)));
		container.add(new Wall(this, new Position(1, 9)));
		container.add(new Wall(this, new Position(8, 9)));
		container.add(new Wall(this, new Position(9, 9)));
		container.add(new Wall(this, new Position(8, 1)));
		container.add(new Wall(this, new Position(9, 1)));
		container.add(new Wall(this, new Position(8, 8)));
		container.add(new Wall(this, new Position(4, 6)));
		container.add(new Wall(this, new Position(5, 6)));
		container.add(new Wall(this, new Position(6, 6)));

		if (this.nLevel == 2) {
			container.add(new Wall(this, new Position(3, 5)));
			container.add(new MetalWall(this, new Position(3, 6)));
			container.add(new Wall(this, new Position(7, 5)));
			container.add(new Wall(this, new Position(7, 6)));
		} else {
			container.add(new Wall(this, new Position(7, 6)));
			container.add(new Wall(this, new Position(7, 5)));
		}
	}

	private void initLemmings() {
		container.add(new Lemming(this, new Position(2, 3), new WalkerRole()));
		container.add(new Lemming(this, new Position(9, 0), new WalkerRole()));
		container.add(new Lemming(this, new Position(0, 8), new WalkerRole()));
		container.setlemmingsInBoard(3);

		if (nLevel == 2) {
			container.add(new Lemming(this, new Position(3, 3), new WalkerRole()));
			container.add(new Lemming(this, new Position(6, 0), new WalkerRole()));
			container.add(new Lemming(this, new Position(6, 0), new ParachuterRole()));
			container.setlemmingsInBoard(6);
		} else {
			container.add(new Wall(this, new Position(7, 6)));
			container.add(new Wall(this, new Position(7, 5)));
			if (nLevel == 1) {
				container.add(new Lemming(this, new Position(3, 3), new WalkerRole()));
				container.setlemmingsInBoard(4);
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(this.nCycle + " ");
		str.append(this.nLemmingsInBoard + " ");
		str.append(this.nLemmingsDead + " ");
		str.append(this.nLemmingsExit + " ");
		str.append(this.nLemmingsToWin + "\n");
		str.append(this.container.toString());
		return str.toString();
	}

	public void save(String fileName) throws GameModelException {
		try (BufferedWriter outChars = new BufferedWriter(new FileWriter(fileName))) {
			outChars.write(this.toString());

		} catch (IOException e) {
			throw new GameModelException(); // TODO
		}

	}

}
