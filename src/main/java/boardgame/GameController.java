package boardgame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
	 *@param gv is the GameViewer objectt
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
			String[] start = cleanedInput.split("\\s+");
			if (start[0].equals("start") && start.length == 5) {
				try {
					int rows = Integer.parseInt(start[1]), columns = Integer.parseInt(start[2]),
							dots = Integer.parseInt(start[3]), holes = Integer.parseInt(start[4]);

					if (rows < 5 || columns < 5) {
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
