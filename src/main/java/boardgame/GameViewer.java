package boardgame;

import boardgame.GameModel;

public class GameViewer {

	private char empty = ' ', player = 'X', goal = 'C', dot = 'O', hole = '\\';
	private int width = 1;

	/**
	 * Clears screen by printing 50 empty lines.
	 */
	private void clearScreen() {
		for (int i = 0; i < 50; i++)
			System.out.println();
	}

	/**
	 * Prints start message.
	 *
	 * @param message the {@code string} which needs to be printed.
	 */
	public void displayMsg(String message) {
		System.out.println(">>>>> " + message + " <<<<<");
	}

	/**
	 * Fills the cells with symbols.
	 *
	 * @param chr the {@code character} of the symbol.
	 * @param width the {@code number} of times it needs to be printed in the cell.
	 * @return returns the cell.
	 */
	private StringBuilder getFill(char chr, int width) {
		StringBuilder sb = new StringBuilder();
		sb.append('|');

		for (int i = 0; i < width; i++)
			sb.append(chr);

		sb.append('|');
		return sb;
	}

	public void printGame(GameModel game) {
		clearScreen();
		for (int i = 0; i < game.getRows(); i++) {
			for (int j = 0; j < game.getColumns(); j++) {
				Location current = new Location(i, j);
				StringBuilder fill;
				if (game.getDots().contains(current))
					fill = getFill(dot, width);
				else if (game.getHoles().contains(current))
					fill = getFill(hole, width);
				else if (game.getGoal().equals(current))
					fill = getFill(goal, width);
				else
					fill = getFill(empty, width);

				if (game.getPlayer().equals(current))
					fill.setCharAt(fill.length() / 2, player);

				System.out.print(fill.toString());

			}
			System.out.println();
		}
	}
}
