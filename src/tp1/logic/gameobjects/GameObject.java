package tp1.logic.gameobjects;

import tp1.exceptions.ObjectParseException;

import tp1.exceptions.OffBoardException;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.logic.lemmingRoles.LemmingRole;
import tp1.view.Messages;

public abstract class GameObject implements GameItem {
	
	protected Position pos;
	protected boolean isAlive;
	protected GameWorld game;

	protected final String name;
	protected final String shortcut;

	public GameObject(GameWorld game, Position pos, String name, String shortcut) {
		this.isAlive = true;
		this.pos = pos;
		this.game = game;
		this.name = name;
		this.shortcut = shortcut;

	}

	// Abstract methods
	public abstract void update();
	public abstract String getIcon();

	// GameItem methods
	@Override
	public boolean isSolid() {
		return false;
	}

	@Override
	public boolean isAlive() {
		return isAlive;
	}

	@Override
	public boolean isInPosition(Position p) {
		return p.equals(this.pos);
	}

	@Override
	public boolean interactWith(Lemming lemming) {
		return false;
	}

	@Override
	public boolean interactWith(Wall wall) {
		return false;
	}

	@Override
	public boolean interactWith(ExitDoor door) {
		return false;
	}

	// Other methods
	public void setLife(boolean isALive) {
		this.isAlive = isALive;
	}

	@Override
	public boolean setRole(LemmingRole role) {
		return false;
	}

	// 3.0
	public GameObject parse(String line, GameWorld game) throws ObjectParseException, OffBoardException {

		String[] words = line.trim().split("\\s+");
		Position pos = checkPositionFrom(words[0], line);
		if (checkObjectNameFrom(words[1])) {
			return this.copy(game, pos);
		}

		return null;
	}

	public static Position checkPositionFrom(String pos, String line) throws ObjectParseException, OffBoardException {
		String[] p = pos.replace("(", "").replace(")", "").split(",");
		if (p.length == 2) {

			try {
				int col = Integer.valueOf(p[0]);
				int row = Integer.valueOf(p[1]);
				Position posit = new Position(row, col);
				if (GameWorld.dentroDelMapa(posit)) {
					return posit;
				}
				throw new OffBoardException();
			} catch (NumberFormatException e) {
				throw new ObjectParseException(Messages.INVALID_POSITION.formatted(line));
			}
		}
		throw new ObjectParseException(Messages.INVALID_POSITION.formatted(line));
	}

	public boolean checkObjectNameFrom(String nombreObjeto) {
		if (nombreObjeto.equalsIgnoreCase(this.name) || nombreObjeto.equalsIgnoreCase(this.shortcut)) {
			return true;
		}
		return false; 
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("(" + this.pos.getRow() + "," + this.pos.getCol() + ") ");
		str.append(this.name);
		return str.toString();
	}
	
	public abstract GameObject copy();
	public abstract GameObject copy(GameWorld game, Position pos);
}
