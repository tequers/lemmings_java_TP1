package tp1.logic;

/**
 * 
 * Immutable class to encapsulate and manipulate positions in the game board
 * 
 */
public class Position {

	private final int col;
	private final int row;

	public Position(int col, int row) {
		this.col = col;
		this.row = row;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public int getRow() {
		return this.row;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		Position pos = (Position) obj;	
		return this.col == pos.getCol() && this.row == pos.getRow();
	}
	
}
