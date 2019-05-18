package gametests;

import static org.junit.jupiter.api.Assertions.*;

import boardgame.GameViewer;
import boardgame.Location;
import boardgame.Way;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import boardgame.GameModel;

public class GameModelTest {

	private static GameModel game;
	private static GameViewer gameviewer;

	@BeforeAll
	static void initGame() {
		game = new GameModel(10, 10, 15, 10);
		gameviewer = new GameViewer();
		game.setGameViewer(gameviewer);
		// turning off display for tests
		gameviewer.setDisplay(false);
	}

	@Test
	void testGetRows() {
		assertEquals(game.getRows(), 10);
	}

	@Test
	void testGetColumns() {
		assertEquals(game.getColumns(), 10);
	}

	@Test
	void testGetDots() {
		assertEquals(game.getDots().size(), 15);
	}

	@Test
	void testGetHoles() {
		assertEquals(game.getHoles().size(), 10);
	}

	@Test
	void testPlayer() {
		game.setPlayer(new Location(0, 0));
		assertEquals(game.getPlayer(), new Location(0, 0));

	}

	@Test
	void testStepDown() {
		game = new GameModel(10, 10, 0, 0);
		game.setPlayer(new Location(5, 5));

		try {
			game.move(Way.DOWN);
		} catch (Exception e) {
			game.setGameViewer(null);

		}

		assertEquals(game.getPlayer(), new Location(7, 5));

	}

	@Test
	void testStepUp() {
		game = new GameModel(10, 10, 0, 0);
		game.setPlayer(new Location(5, 5));
		game.setGameViewer(gameviewer);

		try {
			game.move(Way.UP);
		} catch (Exception e) {
			game.setGameViewer(null);

		}

		assertEquals(game.getPlayer(), new Location(3, 5));

	}

	@Test
	void testStepUpLeft() {
		game = new GameModel(10, 10, 0, 0);
		game.setPlayer(new Location(5, 5));
		game.setGameViewer(gameviewer);

		try {
			game.move(Way.LEFT);
		} catch (Exception e) {
			game.setGameViewer(null);

		}

		assertEquals(game.getPlayer(), new Location(5, 3));

	}

	@Test
	void testStepRight() {
		game = new GameModel(10, 10, 0, 0);
		game.setPlayer(new Location(5, 5));
		game.setGameViewer(gameviewer);

		try {
			game.move(Way.RIGHT);
		} catch (Exception e) {
			game.setGameViewer(null);

		}

		assertEquals(game.getPlayer(), new Location(5, 7));

	}

}
