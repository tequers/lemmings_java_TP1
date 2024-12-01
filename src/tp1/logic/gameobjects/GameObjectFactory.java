package tp1.logic.gameobjects;

import java.util.Arrays;
import java.util.List;


import tp1.exceptions.ObjectParseException;
import tp1.exceptions.OffBoardException;
import tp1.logic.GameWorld;
import tp1.view.Messages;

public class GameObjectFactory {

	private static final List<GameObject> availableGameObjects = Arrays.asList(new Wall(null, null),
			new MetalWall(null, null), new Lemming(null, null, null), new ExitDoor(null, null)

	);

	public static GameObject parse(String line, GameWorld game) throws ObjectParseException, OffBoardException {
		for (GameObject go : availableGameObjects) {
			GameObject gObj = go.parse(line, game); 
			if (gObj != null) {
				return gObj; 
			}
		}
		throw new ObjectParseException(Messages.UNKNOWN_GAME_OBJECT.formatted(line)); // TODO: RELLENAR MENSAJE
	}
}
