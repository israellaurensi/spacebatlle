import java.util.Random;

import java.util.ArrayList;

public class AlienGenerator extends Thread {

	private ArrayList aliens;
	private Board board;

	public AlienGenerator(Board b, ArrayList a) {
		aliens = a;
		board = b;
	}

	public void run() {
		while(true) {
			Random geradorRandom = new Random();
			int level = 1;

			if (board.getScore() > 300) { level = 5; }
			else if (board.getScore() > 200) { level = 4; }
			else if (board.getScore() > 100) { level = 3; }
			else if (board.getScore() > 50) { level = 2; }

			try { sleep(geradorRandom.nextInt(1000/level)); } catch(InterruptedException e) {  }
			aliens.add(new Alien(590, (geradorRandom.nextInt(420) + 20), level));
		}
	}

}