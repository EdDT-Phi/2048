public class AI{


	static game2048 game = new game2048();

	public static void main(String[] args) {
		int max = 0;
		int min = 1000000;
		int sum = 0;
		int won = 0;

		for (int i = 0; i < 100; i++){
			boolean moved;
			while(game.movesPossible(game.getGrid())) {
				String direction = findBest(game.getGrid(), 2);
				switch (direction){
					case "down": // down
					moved = game.moveDown();
						break;
					case "right":
						moved = game.moveRight();
						break;
					case "left":
						moved = game.moveLeft();
						break;
					case "up":
						moved = game.moveUp();
						break;
				}

				//game.print();
			}
			int thismax = game.getMax(game.getGrid());
			if(thismax >= 2048){
				won++;
			}
			max = Math.max(max, thismax);
			min = Math.min(min, thismax);
			sum += thismax;
			game.newGame();

		}
		System.out.println("Max: " + max);
		System.out.println("Min: " + min);
		System.out.println("Average: " + sum / 100);
		System.out.println("Won: " + won);
	}

	public static String findBest(int[][] grid, int i){
		int down = findMove(game.testDown(grid), i, grid);
		int right = findMove(game.testRight(grid), i, grid);
		int left = findMove(game.testLeft(grid), i, grid);
		//int up = (int) Math.ceil(findMove(game.testUp(grid), i, grid) / 5.0);

		if(down + left + right == 0)
			return "up";
		if(down >= left && down >= right)
			return "down";
		if(right > left)
			return "right";
		return "left";
	}

	public static int findMove(int[][] grid, int i, int[][] oldGrid){
		if(game.isEqual(grid, oldGrid) || !game.movesPossible(grid))
			return 0;
		if(i == 0)
			return goodness(grid);
		return Math.max(Math.max(	findMove(game.testDown(grid), i-1, grid),
															findMove(game.testLeft(grid), i-1, grid)),
															findMove(game.testRight(grid), i-1, grid));
															
	}

	// More goodness is better
	public static int goodness(int[][] grid){
		return scoreBlank(grid) + getScore(grid) + scoreLow(grid);
	}

	public static int getScore(int[][] g){
		int sum = 0;
		for(int i = 0; i < 4; i++){
			for(int x: g[i]){
				sum += x / Math.abs(i-6);
			}
		}
		return sum;
	}

	public static int scoreBlank(int[][] g) {
		int sum = 0;
		for(int i = 0; i < 4; i++) {
			for(int x: g[i]) {
				if(x == 0)
					sum += Math.abs(i - 4) - 2;
			}
		}
		return sum;
	}

	public static int scoreLow(int[][] g) {
		int sum = 0;
		for(int i = 1; i < 4; i++){
			for(int j = 0; j < 4; j++) {
				if(g[i][j] < g[i-1][j]){
					sum -= 4;
				}
			}
		}
		return sum;
	}
}