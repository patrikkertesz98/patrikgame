package gametests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import boardgame.GameModel;

class GameModelTest {
	
	private static GameModel game;
	
	@BeforeAll
	static void initGame() {
		game = new GameModel(10, 10, 15, 10);
	}

	@Test
	void testGetRows() {
		assertEquals(game.getRows(), 10);
	}
	
	@Test
	void testGetColumns() {
		assertEquals(game.getColumns(), 10);
	}


}
