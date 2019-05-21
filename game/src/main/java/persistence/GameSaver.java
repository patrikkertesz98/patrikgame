package persistence;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import model.*;

/**
 * This class handles saving a game state.
 */
public class GameSaver {
	public static JsonObject saveGame(GameModel game) throws Exception {

		JsonObject gameObject = new JsonObject();

		// player
		JsonObject playerObject = new JsonObject();
		playerObject.addProperty("row", game.getPlayer().getRow());
		playerObject.addProperty("column", game.getPlayer().getColumn());
		gameObject.add("player", playerObject);

		// goal
		JsonObject goalObject = new JsonObject();
		goalObject.addProperty("row", game.getGoal().getRow());
		goalObject.addProperty("column", game.getGoal().getColumn());
		gameObject.add("goal", goalObject);

		// step, rows, columns
		gameObject.addProperty("step", game.getStep());
		gameObject.addProperty("rows", game.getRows());
		gameObject.addProperty("columns", game.getColumns());

		// dots
		JsonArray dotsArray = new JsonArray();
		for (Location dot : game.getDots()) {
			JsonObject dotObject = new JsonObject();
			dotObject.addProperty("row", dot.getRow());
			dotObject.addProperty("column", dot.getColumn());
			dotsArray.add(dotObject);
		}

		gameObject.add("dots", dotsArray);

		// holes
		JsonArray holesArray = new JsonArray();
		for (Location hole : game.getHoles()) {
			JsonObject holeObject = new JsonObject();
			holeObject.addProperty("row", hole.getRow());
			holeObject.addProperty("column", hole.getColumn());
			dotsArray.add(holeObject);
		}

		gameObject.add("holes", holesArray);

		// creating checksum
		String checksum = crypto.HashCreator.sha256(gameObject.toString());
		
		JsonObject saveObject = new JsonObject();
		saveObject.add("game", gameObject);
		saveObject.addProperty("checksum", checksum);

		return saveObject;
	}

	public static void main(String[] args) throws Exception {
		GameModel game = new GameModel(10, 10, 15, 10);
		JsonObject gameObject = saveGame(game);
		System.out.println(crypto.HashCreator.sha256(gameObject.get("game").toString()));
		System.out.println(gameObject.get("checksum").getAsString());
		System.out.println(crypto.HashCreator.sha256(gameObject.get("game").toString()).equals(gameObject.get("checksum").getAsString()));
		System.out.println(gameObject);
//		GameModel newGame = GameLoader.loadGame(gameObject);

	}

}
