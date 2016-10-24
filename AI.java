public class AI {


    static game2048 game = new game2048();
    static final int moves_ahead = 5;
    static final double[] weights = { 4, 3, 0.4, 1.7};
    static final int num_games = 10000;
    static final int MOVE_NOT_POSSIBLE = -100000;

    public static void main(String[] args) {
        int max_achieved = 0;
        // Would be great if this was the actual min
        int min_achieved = 2048; 
        int games_won = 0;
        int[] distribution = new int[6];
        for (int i = 0; i < num_games; i++) {

            while (nextMove()) {
            }

            int round_max = game.getMax(game.getGrid());
            distribution[Math.max(0, log(round_max)-8)]++;
            if(round_max >= 2048)    games_won++;
            max_achieved = Math.max(max_achieved, round_max);
            min_achieved = Math.min(min_achieved, round_max);
            game.newGame();

            if (i % 250 == 0) {
                System.out.println(i);
            }
        }

        System.out.println("Moves: " + moves_ahead);
        System.out.println("Weights: " + weights);
        System.out.println("Max: " + max_achieved);
        System.out.println("Min: " + min_achieved);
        System.out.println("Won: " + games_won * 100 / 1000 + "%");
        System.out.println("Distribution:");
        System.out.println("\t8192 - " + distribution[5]);
        System.out.println("\t4096 - " + distribution[4]);
        System.out.println("\t2048 - " + distribution[3]);
        System.out.println("\t1024 - " + distribution[2]);
        System.out.println("\t512 - " + distribution[1]);
        System.out.println("\tother - " + distribution[0]);
        System.out.println();
    }

    public static boolean nextMove() {
        String direction = findBest(game.getGrid(), moves);
        switch (direction) {
            case "down": return game.moveDown();
            case "right": return game.moveRight();
            case "left": return game.moveLeft();
            case "up": return game.moveUp();
        }
        if(DEBUG){
            game.print();
            System.out.println(direction);
        }

        return false;
    }

    public static String findBest(int[][] grid, int i) {
        int down = findMove(game.testDown(grid), i, grid, "Down");
        int left = findMove(game.testLeft(grid), i, grid, "Left");
        int right = findMove(game.testRight(grid), i, grid, "Right");

        if (DEBUG) {
            System.out.println("Down: " + down);
            System.out.println("Right: " + right);
            System.out.println("Left: " + left);
        }

        if (down != MOVE_NOT_POSSIBLE && down >= left && down >= right)
            return "down";
        if (right != MOVE_NOT_POSSIBLE && right > left)
            return "right";
        if (left != MOVE_NOT_POSSIBLE)
            return "left";

        // Ideally, never move up
        return "up";
    }

    public static int findMove(int[][] grid, int moves_left, int[][] oldGrid) {
        if (moves_left == 0)
            return goodness(grid);
        // Move not possible
        if (game.isEqual(grid, oldGrid) || !game.movesPossible(grid))
            return (moves_left == moves) ? MOVE_NOT_POSSIBLE : 0;

        // Get the best possible outcome, might want to exchange for
        // sum of goodness of possible boards
        int max_goodnes = Math.max( findMove(game.testDown(grid), moves_left - 1, grid),
                                    findMove(game.testLeft(grid), moves_left - 1, grid));
        max_goodnes = Math.max(findMove(game.testRight(grid), moves_left - 1, grid), max_goodnes);
        return  (int) (goodness(grid) + max_goodnes / weights[3]);
    }

    // More goodness is better
    public static int goodness(int[][] grid) {
        return (getScore(grid)
                + scoreCorner(grid));
    }

    public static int getScore(int[][] g) {
        boolean foundCulprit = false;
        int sum = 0;
        double mult = 1;
        for (int i = 0; i < 4; i++) {
            int blankBoxes = 0;
            for (int j = 0; j < 4; j++) {

                // check for big tiles
                sum += g[i][j] / Math.abs(i - 4);

                // check for high above low
                if (i > 0 && g[i][j] < g[i - 1][j]) {
                    sum -= weights[0];
                }

                // count blank tiles
                if (g[i][j] == 0) {
                    blankBoxes ++;
                }
            }

            // check for only up probability
            if (foundCulprit && blankBoxes != 0) {
                mult = weights[1]; // better score
            } else if (!foundCulprit && blankBoxes < 4) {
                if (blankBoxes == 1) {
                    foundCulprit = true;
                } else {
                    mult = weights[1]; // better score
                }
            }
        }
        return (int) (sum * mult);
    }

    public static int scoreLow(int[][] g) {
        int sum = 0;
        for (int i = 1; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (g[i][j] < g[i - 1][j]) {
                    sum -= 4;
                }
            }
        }
        return sum;
    }

    public static int scoreCorner(int[][] g) {
    	return (int)	(Math.max(rowScoreL(3, g), rowScoreR(3, g)) * weights[2]); //+
    			//Math.max(rowScoreL(2, g), rowScoreR(2, g)));
    }

    //public static int scoreCorner(int[][] g) {
/*        if (g[3][0] > g[3][3])
            return g[3][0] - g[3][3] + (int)(g[3][1] / 1.5);
        return g[3][3] - g[3][0] + (int)(g[3][2] / 1.5);
    }*/

    public static int rowScoreL(int i, int[][] g){
    	int sum = g[i][0];
        for(int j = 0; j < 2; j ++) {
        	if (g[i][j]/2 == g[i][j+1]) {
        		sum += g[i][j] * weights[2];
        	} else {
        		sum += g[i][j];
        	}
        }
        return sum;
    }

    public static int rowScoreR(int i, int[][] g){
    	int sum = g[i][3];
        for(int j = 3; j > 0; j --) {
        	if (g[i][j]/2 == g[i][j-1]) {
        		sum += g[i][j] * 2;
        	} else {
        		sum += g[i][j];
        	}
        }
        return sum;
    }

    public static int log(int num){
        if(i <= 2) return 1;
        return 1 + log(num/2);
    }
}