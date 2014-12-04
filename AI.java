public class AI{
	public static void main(String[] args) {

		game2048 game = new game2048();
		int max = 0;
		int i = 0;

		while (max < 2048) {
			boolean moved;
			while(game.movesPossible()) {
				moved = game.moveDown();
				if(!moved)
					moved = game.moveRight();
				if(!moved)
					moved = game.moveLeft();
				if(!moved)
					moved = game.moveUp();

				//game.print(); // for debug purposes
			}

			max = Math.max(max, game.getMax());
			game.newGame();
			i++;
		}
		System.out.println("Max: " + max);
		System.out.println("Tries: " + i);
	}
}