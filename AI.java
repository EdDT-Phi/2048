public class AI{


	static game2048 game = new game2048();
	static double weight1 = 1.7; // 1.3 - 3.1

	public static void main(String[] args) {
		//for(weight1 = 0.5; weight1 < 4; weight1 += 0.2)
		for(int moves = 1; moves <= 5; moves ++) {
			int max = 0;
			int min = 1000000;
			int win = 0;
			int[] distro = new int[6];
			for (int i = 0; i < 1000; i++) {
				boolean moved;
				while(game.movesPossible(game.getGrid())) {
					String direction = findBest(game.getGrid(),moves, false);
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

				if(thismax == 8192){
					distro[5]++;
					win++;
				} else if(thismax == 4096){
					distro[4]++;
					win++;
				} else if(thismax == 2048){
					distro[3]++;
					win++;
				} else if(thismax == 1024){
					distro[2]++;
				} else if(thismax == 512){
					distro[1]++;
				} else {
					distro[0]++;
				}

				max = Math.max(max, thismax);
				min = Math.min(min, thismax);
				game.newGame();

				//if(i%300 == 0){
				//	System.out.println(i);
				//}

			}
			System.out.println("Moves: " + moves);
			System.out.println("Weight: " + weight1);
			System.out.println("Max: " + max);
/*			System.out.println("Min: " + min);*/
			System.out.println("Won: " + win * 100 / 1000 + "%");
			System.out.println("Distro:");
			System.out.println("\t8192 - " + distro[5]);
			System.out.println("\t4096 - " + distro[4]);
			System.out.println("\t2048 - " + distro[3]);
			System.out.println("\t1024 - " + distro[2]);
			System.out.println("\t512 - " + distro[1]);
			System.out.println("\tother - " + distro[0]);
			System.out.println();
		}
	}

	public static String findBest(int[][] grid, int i, boolean debug){
		int down = findMove(game.testDown(grid), i, grid, "Down", debug);
		int left = findMove(game.testLeft(grid), i, grid, "Left", debug);
		int right = findMove(game.testRight(grid), i, grid, "Right", debug);

		if(debug){
			System.out.println("Down: " + down);
			System.out.println("Right: " + right);
			System.out.println("Left: " + left);
		}

		if(down + left + right == 0)
			return "up";
		if(down >= left && down >= right)
			return "down";
		if(right > left)
			return "right";
		return "left";
	}

	public static int findMove(int[][] grid, int i, int[][] oldGrid, String s, boolean debug){
		if(debug)
			printGood(s, grid);
		if(game.isEqual(grid, oldGrid) || !game.movesPossible(grid))
			return 0;
		if(i == 0){
			return goodness(grid);
		}
		return 	(int) (goodness(grid) + (Math.max(Math.max(
				findMove(game.testDown(grid), i-1, grid, s + ", Down", debug),
				findMove(game.testLeft(grid), i-1, grid, s + ", Left", debug)),
				findMove(game.testRight(grid), i-1, grid, s + ", Right", debug))) / weight1);						
	}

	// More goodness is better
	public static int goodness(int[][] grid){
		return (getScore(grid)
			+ scoreCorner(grid));
	}

	public static int getScore(int[][] g){
		boolean foundCulprit = false;
		int sum = 0;
		int mult = 1;
		for(int i = 0; i < 4; i++){
			int blankBoxes = 0;
			for(int j = 0; j < 4; j++){
				
				// check for big tiles
				sum += g[i][j] / Math.abs(i-4);

				// check for blank tiles
				if(g[i][j] == 0)
					sum += Math.abs(i - 4) - 2;

				// check for high above low
				if(i >0 && g[i][j] < g[i-1][j]){
					sum -= 4;
				}

				// count blank tiles
				if (g[i][j] == 0) {
					blankBoxes ++;
				}
			}

			// check for only up probability
			if(foundCulprit && blankBoxes !=0){
				mult = 2; // better score
			} else if(!foundCulprit && blankBoxes < 4) {
				if(blankBoxes == 1){
					foundCulprit = true;
				} else {
					mult = 2; // better score
				}
			}
		}
		return sum * mult;
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

	public static int scoreCorner(int[][] g) {
		if(g[3][0] > g[3][3])
			return g[3][0] - g[3][3] + (int)(g[3][1] / 1.5);
		return g[3][3] - g[3][0] + (int)(g[3][2] / 1.5);
	}

	public static int scoreNoUp(int[][] g){
		boolean foundCulprit = false;
		for (int i = 0; i < 4; i++) {
			int blankBoxes = 0;
			for (int j = 0; j < 4; j++) {
				if (g[i][j] == 0) {
					blankBoxes ++;
				}
			}
			if(foundCulprit && blankBoxes !=0){
				return 2; // better score
			} else if(!foundCulprit && blankBoxes < 4) {
				if(blankBoxes == 1){
					foundCulprit = true;
				} else {
					return 2; // better score
				}
			}
		}
		return 1;
	}

	public static void printGood(String s, int[][] g) {
		System.out.println("Goodness "+ s +" Total :" + goodness(g) + ",\tScore: " + getScore(g) + ",\tBlank: " + scoreBlank(g) + ",\tCorner: " + scoreCorner(g) + ",\tLow: " + scoreLow(g));
		game.print(g);
	}
}