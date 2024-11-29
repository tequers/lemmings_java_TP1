package tp1.logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
		  // Validate the input file name
	    if (fileName == null || fileName.isEmpty()) {
	        throw new GameLoadException("File name cannot be null or empty.");
	    }

	    // Attempt to read from the file and process it
	    try (BufferedReader inChars = new BufferedReader(new FileReader(fileName));
	         BufferedWriter outChars = new BufferedWriter(new FileWriter("output.txt"))) {

	        String linea =inChars.readLine()  ;
	        
	        while ((linea = inChars.readLine()) != null) {
	            outChars.write(linea);
	            outChars.newLine();
	       
	        }
	    } catch (IOException e) {
	        throw new GameLoadException("Error while loading file: " + e.getMessage(), e);
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
			   return new GameObjectContainer();//TODO: completar
		   }
}



