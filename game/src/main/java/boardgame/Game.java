package boardgame;

import java.util.Scanner;

public class Game {
	public static void main(String[] args) {
		GameViewer gv = new GameViewer();
		GameController gc = new GameController();

		gc.setGv(gv);

		Scanner sc = new Scanner(System.in);
		gv.displayMsg("Welcome to the game! Press 'start <yourName>' to play!");

		while (sc.hasNextLine()) {
			gc.processInput(sc.nextLine());

			if (gc.getExitflag())
				break;
		}

		sc.close();
		
	}
}
