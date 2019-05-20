package persistence;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import crypto.HashCreator;
import model.*;


public class GameLoader {

	public static GameModel loadGame(JsonObject saveObject) throws Exception {

		try {
			if (!(HashCreator.sha256(saveObject.get("game").toString())
					.equals(saveObject.get("checksum").getAsString())))
				throw new LoadException("Save file is corrupted.");
		} catch (Exception e) {
			throw e;
		}
		
		
		
		JsonObject gameObject = saveObject.get("game").getAsJsonObject();
		GameModel game = new GameModel();

		// rows, columns, step
		game.setRows(gameObject.get("rows").getAsInt());
		game.setColumns(gameObject.get("columns").getAsInt());
		game.setStep(gameObject.get("step").getAsInt());

		// player
		Integer row = gameObject.get("player").getAsJsonObject().get("row").getAsInt();
		Integer column = gameObject.get("player").getAsJsonObject().get("column").getAsInt();
		game.setPlayer(new Location(row, column));

		// goal
		row = gameObject.get("goal").getAsJsonObject().get("row").getAsInt();
		column = gameObject.get("goal").getAsJsonObject().get("column").getAsInt();
		game.setGoal(new Location(row, column));

		// dots
		JsonArray dotsArray = gameObject.get("dots").getAsJsonArray();
		List<Location> dots = new ArrayList<Location>();
		for (JsonElement dot : dotsArray) {
			row = dot.getAsJsonObject().get("row").getAsInt();
			column = dot.getAsJsonObject().get("column").getAsInt();
			dots.add(new Location(row, column));
		}

		game.setDots(dots);

		// holes
		JsonArray holesArray = gameObject.get("holes").getAsJsonArray();
		List<Location> holes = new ArrayList<Location>();
		for (JsonElement hole : holesArray) {
			row = hole.getAsJsonObject().get("row").getAsInt();
			column = hole.getAsJsonObject().get("column").getAsInt();
			holes.add(new Location(row, column));
		}

		game.setHoles(holes);

		return game;
	}

}
