package tp1.logic.gameobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.text.Position;

import tp1.control.commands.Command;
import tp1.control.commands.ExitCommand;
import tp1.control.commands.HelpCommand;
import tp1.control.commands.ResetCommand;
import tp1.control.commands.SetRoleCommand;
import tp1.control.commands.UpdateCommand;
import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.GameWorld;
import tp1.logic.lemmingRoles.LemmingRole;

public class GameObjectFactory {
		// a factoría de objetos nunca devuelve el valor null: 
	//o bien tiene éxito al crear el objeto o bien lanza una excepción
	
	//
	//
	private static final List<GameObject> availableGameObjects = Arrays.asList(
			new Wall(null, null),
			new MetalWall(null,null),
			new Lemming(null,null, null),
			new ExitDoor(null, null)
			
	);
			
	public static GameObject parse(String line, GameWorld game)
			throws ObjectParseException, OffBoardException {
		for (GameObject go: availableGameObjects) {
		    // Código para procesar cada clase
			
			GameObject gObj = go.parse(line, game); //
			if (gObj != null) {
			    return gObj; // Si un objeto pudo parsearse, lo retornamos
			}
			
		}
		throw new ObjectParseException(); //TODO: INFO
	}
}
