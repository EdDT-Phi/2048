public class AI{
	public static void main(String[] args) {

		game2048 game = new game2048();
		int max = 0;
		int min = 1000000;
		int sum = 0;

		boolean test = true;

		for (int i = 0; i < 100000; i++){
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
			min = Math.min(min, game.getMax());
			sum += game.getMax();
			game.newGame();
		}
		System.out.println("Max: " + max);
		System.out.println("Min: " + min);
		System.out.println("Average: " + sum / 100000);
	}
}