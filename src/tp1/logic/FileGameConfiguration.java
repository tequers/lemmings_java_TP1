package tp1.logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import tp1.exceptions.GameLoadException;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.GameObjectFactory;

public class FileGameConfiguration implements GameConfiguration{
	

	private GameObjectContainer container;
	private int nCycle;
	private int nLemmingsExit;
	private int nLemmingsToWin;
	private int nLemmingsInBoard;
	private int nLemmingsDead;
	
	public FileGameConfiguration(String fileName, GameWorld game)
			throws GameLoadException {
		  // Validate the input file name
	    if (fileName == null || fileName.isEmpty()) {
	        throw new GameLoadException("File name cannot be null or empty.");
	    }

	    // Attempt to read from the file and process it
	    try (BufferedReader inChars = new BufferedReader(new FileReader(fileName))) {

	        String line =inChars.readLine()  ;
	        String[] words = line.trim().split("\\s+");

	        if (words.length == 5) {
	        	this.nCycle = Integer.valueOf(words[0]);
	        	this.nLemmingsInBoard = Integer.valueOf(words[1]);
	        	this.nLemmingsDead = Integer.valueOf(words[2]);
	        	this.nLemmingsExit = Integer.valueOf(words[3]);
	        	this.nLemmingsToWin = Integer.valueOf(words[4]);
	        	
	        	
	        	GameObjectContainer goc = new GameObjectContainer();
	        	 while ((line = inChars.readLine()) != null) {
	        		 GameObject gObj = GameObjectFactory.parse(line, game);
	        		 goc.add(gObj);
	 	        }
	        	this.container = goc;
	        }
	        
	    } catch (IOException e) {
	    	System.out.println(e.getMessage());
	        throw new GameLoadException("Error while loading file: " + e.getMessage(), e);
	    } catch (NumberFormatException e) {
	    	 throw  new GameLoadException("Error first line");    //TODO: RELLENAR
        } catch (ObjectParseException ope) {
        	 throw  new GameLoadException("Error al parsear un objeto");   //TODO: RELLENAR
        } catch (OffBoardException obe) {
        	 throw  new GameLoadException("Error pos fuera del mapa");   //TODO: RELLENAR
        }
		
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
			GameObjectContainer goc = new GameObjectContainer();
			goc = this.container;
			return goc;
		   }
}



