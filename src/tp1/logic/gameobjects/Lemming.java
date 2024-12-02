package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.exceptions.RoleParseException;
import tp1.logic.Direction;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.logic.lemmingRoles.LemmingRole;
import tp1.logic.lemmingRoles.LemmingRoleFactory;
import tp1.logic.lemmingRoles.WalkerRole;
import tp1.view.Messages;

public class Lemming extends GameObject {

	private LemmingRole role;
	private Direction dir;
	private final int fall = 3;
	private int currentFall;
	private boolean wasFalling;

	private final static String name = Messages.LEMMING_NAME;
	private final static String shortcut = Messages.LEMMING_SHORTCUT;
	private boolean isInteracting;

	public Lemming(GameWorld game, Position pos, LemmingRole role) {
		super(game, pos, name, shortcut);
		this.role = role;
		this.currentFall = 0;
		this.dir = Direction.RIGHT;
		this.wasFalling = false;

	}

	// Movement
	public Position nextPos(Direction dir) {
		return new Position(dir.getX() + this.pos.getCol(), dir.getY() + this.pos.getRow());
	}

	public void walk() {
		Position nextPos = this.nextPos(this.dir);
		if (GameWorld.dentroDelMapa(nextPos)) {
			if (this.game.isSolid(nextPos))
				this.changeDir();
			else
				this.pos = nextPos;
		} else
			this.changeDir();
	}

	public void fall() {
		Position nextPos = this.nextPos(Direction.DOWN);
		if (GameWorld.dentroDelMapa(nextPos)) {
			this.pos = nextPos;
		} else
			this.dies();
	}

	public boolean isInAir() {
		return this.game.isInAir(this.pos);
	}

	public void dies() {
		this.setLife(false);
		this.game.addDeadLemming();
	}

	public void changeDir() {
		if (getDir() == Direction.RIGHT)
			this.dir = Direction.LEFT;
		else if (getDir() == Direction.LEFT)
			this.dir = Direction.RIGHT;
	}

	public boolean surviveFall() {
		return this.currentFall <= fall;
	}

	public void walkOrFall() {

		// Fall
		if (this.isInAir()) {
			this.fall();
			this.currentFall++;
			this.wasFalling = true;
		} else { // Walk
			if (this.wasFalling) {
				this.currentFall++;
			}
			if (this.surviveFall()) {
				this.currentFall = 0;
				this.walk();
			} else
				this.dies();
		}

	}
	

	public void exitsDoor() {
		this.setLife(false);
		this.game.lemmingArrived();
	}
	// Parachuter
	public void setCurrentFall(int n) {
		this.currentFall = n;
	}

	// GameObject methods
	@Override
	public void update() {
		if (isAlive()) {
			if (this.game.receiveInteractionsFrom(this))
				this.isInteracting = true;
			else
				this.isInteracting = false;
			role.play(this);
		}

	}

	@Override
	public String getIcon() {
		return this.role.getIcon(this);
	}

	// Role manipulation methods
	public void disableRole() {
		this.role = new WalkerRole();
		this.role.play(this);
	}

	@Override
	public boolean setRole(LemmingRole role) {
		if (role.equals(this.role))
			return false;
		else {
			this.role = role;
			this.role.start(this);
			return true;
		}
	}

	// Getters
	public Direction getDir() {
		return this.dir;
	}

	public GameWorld getGame() {
		return this.game;
	}

	public boolean getIsInteracting() {
		return this.isInteracting;
	}

	// GameItem methods
	@Override
	public boolean interactWith(Wall obj) {
		return this.role.interactWith(obj, this);
	}

	@Override
	public boolean receiveInteraction(GameItem other) {
		return other.interactWith(this);
	}

	@Override
	public boolean interactWith(ExitDoor obj) {
		if (obj.isInPosition(this.pos)) {
			this.exitsDoor();
			return true;
		}
		return false;
	}

	// 3.0
	@Override
	public GameObject parse(String line, GameWorld game) throws ObjectParseException, OffBoardException {
		String[] words = line.trim().split("\\s+");
		Position pos = super.checkPositionFrom(words[0], line);
		if (super.checkObjectNameFrom(words[1])) {

			Direction dir = Lemming.getLemmingDirectionFrom(words[2], line);
			int height = Lemming.getLemmingHeigthFrom(words[3], line);
			try {
				Lemming lemming = new Lemming(game, pos, LemmingRoleFactory.parse(words[4]));
				lemming.setDir(dir);
				lemming.setCurrentFall(height);
				return lemming;

			} catch (RoleParseException rpe) {
				throw new ObjectParseException(Messages.INVALID_ROLE.formatted(line));
			}
		}
		return null;

	}

	private static Direction getLemmingDirectionFrom(String direction, String line) throws ObjectParseException {
		switch (direction.toUpperCase()) {
		case ("RIGHT"):
			return Direction.RIGHT;
		case ("LEFT"):
			return Direction.RIGHT;
		case ("UP"):
		case ("DOWN"):
			throw new ObjectParseException(Messages.INVALID_LEMMING_DIRECTION.formatted(line));

		}
		throw new ObjectParseException(Messages.UNKNOWN_OBJECT_DIRECTION.formatted(line));

	}

	private static int getLemmingHeigthFrom(String height, String line) throws ObjectParseException {
		try {
			int h = Integer.valueOf(height);
			return h;
		} catch (NumberFormatException e) {
			throw new ObjectParseException(Messages.INVALID_HEIGHT.formatted(line));
		}

	}

	public void setDir(Direction dir) {
		this.dir = dir;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(super.toString() + " ");
		str.append(this.dir.name() + " ");
		str.append(this.currentFall + " ");
		str.append(this.role.toString());
		return str.toString();
	}

}
