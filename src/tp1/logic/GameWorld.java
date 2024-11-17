package tp1.logic;

import tp1.logic.gameobjects.GameItem;


public interface GameWorld {
	
	public void lemmingArrived();
	public void addDeadLemming() ;
	
	public boolean isInAir(Position pos);
	public boolean isSolid(Position pos);	
	public boolean dentroDelMapa(Position pos);
	public boolean receiveInteractionsFrom(GameItem obj);
	
}
