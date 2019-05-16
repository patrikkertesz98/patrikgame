package boardgame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import persistence.GameLoader;
import persistence.GameSaver;

public class GameController {

	private Map<String, Way> validMoves;
	private Set<String> validCommands;
	private GameModel game;
	private GameViewer gv;
	private boolean exitflag;

	/**
	 * Constructor of validMoves, validCommands
	 */
	public GameController() {
		this.validMoves = new HashMap<String, Way>();
		this.validCommands = new HashSet<String>();
		this.exitflag = false;

		validMoves.put("w", Way.UP);
		validMoves.put("a", Way.LEFT);
		validMoves.put("s", Way.DOWN);
		validMoves.put("d", Way.RIGHT);

		validMoves.put("up", Way.UP);
		validMoves.put("left", Way.LEFT);
		validMoves.put("down", Way.DOWN);
		validMoves.put("right", Way.RIGHT);

		validCommands.add("reset");
		validCommands.add("exit");

	}

	/**
	 * Sets game view with the GameViewer class's constructor.
	 * 
	 * @param gv is the GameViewer objectt
	 */
	public void setGv(GameViewer gv) {
		this.gv = gv;
	}

	/**
	 * Processes user input
	 *
	 * @param input the user input {@code string}
	 */
	public void processInput(String input) {
		String cleanedInput = input.toLowerCase();

		if (!(validMoves.keySet().contains(cleanedInput) || validCommands.contains(cleanedInput))) {
			String[] command = cleanedInput.split("\\s+");
			if (command[0].equals("start") && command.length == 5) {
				try {
					int rows = Integer.parseInt(command[1]), columns = Integer.parseInt(command[2]),
							dots = Integer.parseInt(command[3]), holes = Integer.parseInt(command[4]);

					if ((rows < 5 || columns < 5) || ((holes + dots > (rows * columns) / 2))) {
						gv.displayMsg("Invalid command!");
						return;
					} else {
						this.game = new GameModel(rows, columns, dots, holes);
						gv.printGame(game);
						game.setGameViewer(gv);
						return;
					}

				} catch (Exception e) {
					gv.displayMsg("Invalid command!");
					return;
				}

			} else if (command[0].equals("save")) {

				if (game == null) {
					gv.displayMsg("Nothing to save.");
					return;
				}

				try {
					JsonObject save = GameSaver.saveGame(game);
					BufferedWriter bf = new BufferedWriter(new FileWriter(command[1]));
					bf.write(save.toString());
					bf.close();
					gv.displayMsg("Game saved.");
					return;
				} catch (Exception e) {
					gv.displayMsg("Could not save game. " + e.getMessage());
					return;
				}

			} else if (command[0].contentEquals("load")) {
				try {
					File f = new File(command[1]);
					BufferedReader br = new BufferedReader(new FileReader(f));
					String savefile = "";
					String line;
					while ((line = br.readLine()) != null)
						savefile += line;

					JsonObject gameJson = new JsonParser().parse(savefile).getAsJsonObject();

					game = GameLoader.loadGame(gameJson);
					game.setGameViewer(gv);
					gv.printGame(game);
					gv.displayMsg("Game loaded");
					return;
				} catch (Exception e) {
					gv.displayMsg("Could not load game. " + e.getMessage());
					return;
				}
			} else {

				gv.displayMsg("Invalid command!");
				return;
			}
		}

		if (validCommands.contains(cleanedInput)) {
			if (cleanedInput.equals("reset")) {
				this.game = null;
				gv.displayMsg("Game ended. Press 'start' again!");
			} else if (cleanedInput.equals("exit")) {
				exitflag = true;
				return;
			}

		} else if (validMoves.keySet().contains(cleanedInput)) {
			if (game == null) {
				gv.displayMsg("Initialize game first!");
				return;
			}
			try {
				boolean win = game.move(validMoves.get(cleanedInput));

				if (win) {
					gv.displayMsg("Win!");
					exitflag = true;
					return;
				}

			} catch (MoveException e) {
				gv.displayMsg(e.getMessage());
			}
		} else {
			gv.displayMsg("Invalid command!");
		}

	}

	public boolean getExitflag() {
		return exitflag;
	}

}
