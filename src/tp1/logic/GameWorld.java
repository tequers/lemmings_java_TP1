package tp1.logic;

import tp1.logic.gameobjects.GameItem;


public interface GameWorld {
	
	public void lemmingArrived();
	public void addDeadLemming() ;
	
	public boolean isInAir(Position pos);
	public boolean isSolid(Position pos);	
	public static boolean dentroDelMapa(Position pos) {
		//TODO: cambiar a estática
		int c = pos.getCol();
		int r = pos.getRow();
		return c < Game.DIM_X && c >= 0 && r < Game.DIM_Y && r >= 0;
	}
	public boolean receiveInteractionsFrom(GameItem obj);
	
}
