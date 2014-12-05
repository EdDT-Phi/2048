public class AI{


	static game2048 game = new game2048();

	public static void main(String[] args) {
		int max = 0;
		int min = 1000000;
		int sum = 0;

		boolean test = true;

		for (int i = 0; i < 100; i++){
			boolean moved;
			while(game.movesPossible(game.getGrid())) {
				String direction = findBest(game.getGrid());
				switch(direction){
					case "down": // down
					moved = game.moveDown();
						break;
					case "right":
						moved = game.moveRight();
						break;
					case "left":
						moved = game.moveLeft();
						break;
					default:
						moved = game.moveUp();
				}
			}

			max = Math.max(max, game.getMax());
			min = Math.min(min, game.getMax());
			sum += game.getMax();
			game.newGame();
		}
		System.out.println("Max: " + max);
		System.out.println("Min: " + min);
		System.out.println("Average: " + sum / 100);
	}

	public static String findBest(int[][] grid){
		int[][] down = game.testDown(grid);
		int[][] right = game.testRight(grid);
		int[][] left = game.testLeft(grid);
		int down1 = findMove(down, 2);
		int right1 = findMove(right, 2);
		int left1 = findMove(left, 2);

		if(game.isEqual(grid, down))
			down1 = 0;
		if(game.isEqual(grid, right))
			right1 = 0;
		if(game.isEqual(grid, left))
			left1 = 0;

		if(down1 + right1 + left1 == 0)
			return ":(";
		if(down1 >= right1 && down1 >= left1)
			return "down";
		if(right1 >= left1)
			return "right";
		return "left";
	}

	public static int findMove(int[][] grid, int i){
		if(i == 0)
			return game.countBlank(grid);
		if(!game.movesPossible(grid))
			return 0;
		return game.countBlank(grid) + Math.max(Math.max(findMove(game.testDown(grid), i-1), findMove(game.testLeft(grid), i-1)), findMove(game.testDown(grid), i-1));
	}
}