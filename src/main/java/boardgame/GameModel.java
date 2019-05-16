package boardgame;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class GameModel {
	private Integer rows, columns, step;
	private Location player, goal;
	private List<Location> dots, holes;
	private GameViewer gameViewer;

	public Integer getStep() {
		return step;
	}

	public GameModel(){

	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public void setColumns(Integer columns) {
		this.columns = columns;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public void setPlayer(Location player) {
		this.player = player;
	}

	public void setGoal(Location goal) {
		this.goal = goal;
	}

	public void setDots(List<Location> dots) {
		this.dots = dots;
	}

	public void setHoles(List<Location> holes) {
		this.holes = holes;
	}

	/**
	 * Constructor of GameModel
	 *
	 * @param rows player provided {@code number} of rows.
	 * @param columns player provided {@code number} of columns.
	 * @param numOfDots the {@code number} of dots on the grid.
	 * @param numOfHoles the {@code number} of holes on the grid.
	 */
	public GameModel(Integer rows, Integer columns, Integer numOfDots, Integer numOfHoles) {
		this.rows = rows;
		this.columns = columns;
		this.step = 2;
		this.player = new Location(0, 0);
		this.goal = new Location(rows - 1, columns - 1);
		this.dots = new ArrayList<Location>();
		this.holes = new ArrayList<Location>();

		int i = 0;
		while (i < numOfDots) {
			Location l = Location.createRandomLocation(rows, columns);
			if (locationIsFree(l)) {
				dots.add(l);
				i++;
			}
		}

		i = 0;
		while (i < numOfHoles) {
			Location l = Location.createRandomLocation(rows, columns);
			if (locationIsFree(l)) {
				holes.add(l);
				i++;
			}
		}

	}
	
	public void setGameViewer(GameViewer gameViewer) {
		this.gameViewer = gameViewer;
	}

	public Integer getRows() {
		return rows;
	}

	public Integer getColumns() {
		return columns;
	}

	public Location getPlayer() {
		return player;
	}

	public Location getGoal() {
		return goal;
	}

	public List<Location> getDots() {
		return dots;
	}

	public List<Location> getHoles() {
		return holes;
	}

	/**
	 * Handles player movement and redrawing of play area.
	 *
	 * @param way the direction the player wishes to move in.
	 * @return returns a boolean about the win state
	 * @throws MoveException throws MoveException if invalidState occurs.
	 */
	public boolean move(Way way) throws MoveException {
		movePlayer(way);
		checkStep();
		gameViewer.printGame(this);
		return checkWin();
	}

	/**
	 * Moves player in a direction based on player input.
	 *
	 * @param way the direction the player wants to move in.
	 * @throws MoveException throws MoveException if invalidState occurs.
	 */
	private void movePlayer(Way way) throws MoveException {
		Location prevLoc = player;

		if (way == Way.UP)
			player = new Location(player.getRow() - step, player.getColumn());
		else if (way == Way.DOWN)
			player = new Location(player.getRow() + step, player.getColumn());
		else if (way == Way.RIGHT)
			player = new Location(player.getRow(), player.getColumn() + step);
		else if (way == Way.LEFT)
			player = new Location(player.getRow(), player.getColumn() - step);

		if (invalidState()) {
			player = prevLoc;
			throw new MoveException("Invalid movement.");
		}

	}

	/**
	 * Handles the number switch of steps if the player steps on a dot.
	 */
	private void checkStep() {
		if (dots.contains(player))
			if (step.equals(2))
				step = 3;
			else
				step = 2;
	}

	/**
	 * Checks if the player has won the game.
	 *
	 * @return returns {@code true} if the player's location equals the goal's location, returns {@code false} if not.
	 */
	private boolean checkWin() {
		return player.equals(goal);
	}

	/**
	 * Checks if the player is in a valid location.
	 *
	 * @return returns {@code true} if the player is out of bounds or steps on a hole, returns {@code false} if all of these are false.
	 */
	private boolean invalidState() {
		return holes.contains(player) || player.getRow() < 0 || player.getRow() >= rows || player.getColumn() < 0
				|| player.getColumn() >= columns;

	}

	/**
	 * Checks if a location is free to place anything in it.
	 *
	 * @param l the location
	 * @return returns {@code true} if location is not contained by player, goal, dots, holes. Returns {@code false} if anyone of these is true.
	 */
	private boolean locationIsFree(Location l) {
		return !(l.equals(player) || l.equals(goal) || dots.contains(l) || holes.contains(l));
	}
}
