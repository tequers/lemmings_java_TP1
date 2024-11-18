package tp1.logic.gameobjects;

import tp1.logic.Direction;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.logic.lemmingRoles.LemmingRole;
import tp1.logic.lemmingRoles.WalkerRole;

public class Lemming extends GameObject {

	private LemmingRole role;
	private Direction dir;
	private final int fall = 3;
	private int currentFall;
	private boolean wasFalling;
	
	public Lemming(GameWorld game, Position pos, LemmingRole role) {
		super(game, pos);
		this.role = role;
		this.currentFall = 0;
		this.dir = Direction.RIGHT;
		this.wasFalling = false;
	}
	
	//Movement
	public Position nextPos(Direction dir) {
		return new Position(dir.getX() + this.pos.getCol(), 
				dir.getY()+this.pos.getRow());
	}
	
	public void walk() {
		Position nextPos = this.nextPos(this.dir);
		if (this.game.dentroDelMapa(nextPos)) {
			if (this.game.isSolid(nextPos)) this.changeDir();
			else this.pos = nextPos;	
		} else  this.changeDir();
	}
	
	public void fall() {
		Position nextPos= this.nextPos(Direction.DOWN);
		if (this.game.dentroDelMapa(nextPos)) {
			this.pos = nextPos;
		} else this.dies();
				
	}
	
	public boolean isInAir() {
		return this.game.isInAir(this.pos);
	}
	
	public void dies() {
		this.setLife(false);
		this.game.addDeadLemming();
	}
	
	public void changeDir() {
		if (getDir() == Direction.RIGHT) this.dir = Direction.LEFT;
		else if (getDir()==Direction.LEFT) this.dir = Direction.RIGHT;
	}
	
	public boolean surviveFall() {
    	return this.currentFall <= fall;
    }
	
	public void walkOrFall() {
		if (this.game.receiveInteractionsFrom(this)) {
			this.setLife(false);
			this.game.lemmingArrived();
		}
		else { //Fall
			if (this.isInAir()) {
				this.fall();
				this.currentFall++;
				this.wasFalling = true;
			} else { //Walk
				if (this.wasFalling){
					this.currentFall++;
				}
				if (this.surviveFall()) {
					this.currentFall = 0;
					this.walk();
				} else this.dies();
			}
		}
	}
	
	//Parachuter
	public void setCurrentFall(int n) {
		this.currentFall = n;
	}
	
	//GameObject
	@Override
	public void update() {
		if (isAlive()) 
			role.play(this);
	}
	
	@Override
	public String getIcon() {
		return this.role.getIcon(this);
	}
	
	// TODO you should write a toString method to return the string that represents the object status
	// @Override
	// public String toString()
	
	//Role
	public void disableRole() {
		this.role = new WalkerRole();
		this.role.play(this);
	}
	
	@Override
	public boolean setRole(LemmingRole role) {
		if (role.equals(this.role)) return false;
		else {
			
			this.role= role;
			this.role.start(this);
			return true;
		}
	}
	
	//Getters
	public Direction getDir() {
		return this.dir;
	}
	
	public GameWorld getGame() {
		return this.game;
	}
	//Setters
	public void setPos(Position pos) {
		this.pos = pos;
	}
	
	//GameItem Interactions
	@Override
	public boolean interactWith(Wall obj) {
		return this.role.interactWith(obj, this);
	}
	
	@Override
	public boolean receiveInteraction(GameItem other) {
		return other.interactWith(this);
	}
	
	@Override
    public boolean interactWith(ExitDoor obj){
    	if (this.isInPosition(obj.getPos())) {
    		return true;
    	} else return false;
    	
    }
    
}
