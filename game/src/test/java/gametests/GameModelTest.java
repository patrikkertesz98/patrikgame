package gametests;

import static org.junit.jupiter.api.Assertions.*;

import boardgame.GameViewer;
import model.*;
import org.junit.jupiter.api.Test;

public class GameModelTest {

	private static GameModel game;
	private static GameViewer gameviewer;


	@Test
	void testGetRows() {
		game = new GameModel(10, 10, 15, 10);
		gameviewer = new GameViewer();
		gameviewer.setDisplay(false);

		assertEquals(game.getRows(), 10);
	}

	@Test
	void testGetColumns() {
		game = new GameModel(10, 10, 15, 10);
		gameviewer = new GameViewer();
		gameviewer.setDisplay(false);

		assertEquals(game.getColumns(), 10);
	}

	@Test
	void testGetDots() {

		game = new GameModel(10, 10, 15, 10);
		gameviewer = new GameViewer();
		gameviewer.setDisplay(false);
		assertEquals(game.getDots().size(), 15);
	}

	@Test
	void testGetHoles() {
		game = new GameModel(10, 10, 15, 10);
		gameviewer = new GameViewer();
		gameviewer.setDisplay(false);
		assertEquals(game.getHoles().size(), 10);
	}

	@Test
	void testPlayer() {
		game.setPlayer(new Location(0, 0));
		assertEquals(game.getPlayer(), new Location(0, 0));

	}



	@Test
	void testStepUp() {
		game = new GameModel(10, 10, 0, 0);
		game.setPlayer(new Location(5, 5));

		try {
			game.move(Way.UP);
		} catch (Exception e) {
			fail();

		}

		assertEquals(game.getPlayer(), new Location(3, 5));

	}

	@Test
	void testStepUpLeft() {
		game = new GameModel(10, 10, 0, 0);
		game.setPlayer(new Location(5, 5));
		try {
			game.move(Way.LEFT);
		} catch (Exception e) {
			fail();

		}

		assertEquals(game.getPlayer(), new Location(5, 3));

	}

	@Test
	void testStepRight() {
		game = new GameModel(10, 10, 0, 0);
		game.setPlayer(new Location(5, 5));

		try {
			game.move(Way.RIGHT);
		} catch (Exception e) {
			fail();

		}

		assertEquals(game.getPlayer(), new Location(5, 7));

	}

	@Test
	void testStepDown() {
		game = new GameModel(10, 10, 0, 0);
		game.setPlayer(new Location(5, 5));

		try {
			game.move(Way.DOWN);
		} catch (Exception e) {
			fail();

		}



		assertEquals(game.getPlayer(), new Location(7, 5));

	}

}