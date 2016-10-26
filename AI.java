public class AI {


    static Game2048 game = new Game2048();
    static final int MOVES_AHEAD = 5;
    static final double[] weights = {4, 3, 0.4, 1.7};
    static final int NUM_GAMES = 10000;
    static final int MOVE_NOT_POSSIBLE = -100000;
    static final boolean DEBUG = false; 

    public static void main(String[] args) {
        int max_achieved = 0;
        // Would be great if this was the actual min
        int min_achieved = 2048; 
        int games_won = 0;
        int[] distribution = new int[6];
        for (int i = 0; i < NUM_GAMES; i++) {
            game.newGame();

            // returns false when a move can't be made
            while (nextMove()) {
            }

            int round_max = game.getMax(game.getBoard());
            distribution[Math.max(0, log(round_max)-8)]++;
            if(round_max >= 2048)    games_won++;
            max_achieved = Math.max(max_achieved, round_max);
            min_achieved = Math.min(min_achieved, round_max);

            if (i % (NUM_GAMES/10) == 0) {
                System.out.println((i * 100) / NUM_GAMES + "%");
            }
        }

        System.out.println("Sight: " + MOVES_AHEAD);
        System.out.println("Weights: " + weights);
        System.out.println("Max: " + max_achieved);
        System.out.println("Min: " + min_achieved);
        System.out.println("Won: " + games_won * 100 / NUM_GAMES + "%");
        System.out.println("Distribution:");
        System.out.println("\t8192 - "  + distribution[5]);
        System.out.println("\t4096 - "  + distribution[4]);
        System.out.println("\t2048 - "  + distribution[3]);
        System.out.println("\t1024 - "  + distribution[2]);
        System.out.println("\t512 - "   + distribution[1]);
        System.out.println("\tother - " + distribution[0]);
    }

    public static boolean nextMove() {
        String direction = findBest(game.getBoard(), MOVES_AHEAD);
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

    public static String findBest(int[][] board, int i) {
        int down = findMove(game.testDown(board), i, board);
        int left = findMove(game.testLeft(board), i, board);
        int right = findMove(game.testRight(board), i, board);

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

    public static int findMove(int[][] board, int moves_left, int[][] old_board) {
        if (moves_left == 0)
            return goodness(board);
        // Move not possible
        if (game.isEqual(board, old_board) || !game.movesPossible(board))
            return (moves_left == MOVES_AHEAD) ? MOVE_NOT_POSSIBLE : 0;

        // Get the best possible outcome, might want to exchange for
        // sum of goodness of possible boards
        int max_goodnes = Math.max( findMove(game.testDown(board), moves_left - 1, board),
                                    findMove(game.testLeft(board), moves_left - 1, board));
        max_goodnes = Math.max(findMove(game.testRight(board), moves_left - 1, board), max_goodnes);
        return  (int) (goodness(board) + max_goodnes / weights[3]);
    }

    // More goodness is better, currently two measures of goodness
    public static int goodness(int[][] board) {
        return (getScore(board)
                + scoreCorner(board));
    }

    // General score assesment. Combines several other assesments
    public static int getScore(int[][] board) {
        
        // only one blank tile means random could prevent moving left and right
        boolean one_blank_tile = false;

        int sum = 0;
        double mult = 1;
        for (int i = 0; i < 4; i++) {
            int blank_tiles = 0;
            for (int j = 0; j < 4; j++) {

                // check for big tiles, lower is better
                sum += board[i][j] / (4 - i);

                // check for high above low
                if (i > 0 && board[i][j] < board[i - 1][j]) {
                    sum -= weights[0];
                }

                // count blank tiles
                if (board[i][j] == 0) {
                    blank_tiles ++;
                }
            }

            // check for only up probability
            if (one_blank_tile && blank_tiles != 0) {
                mult = weights[1];
            } else if (!one_blank_tile && blank_tiles < 4) {
                if (blank_tiles == 1) {
                    one_blank_tile = true;
                } else {
                    mult = weights[1];
                }
            }
        }
        return (int) (sum * mult);
    }

    // This reduces score if there is a tile lower than the tile above it
    // public static int scoreLow(int[][] g) {
    //     int sum = 0;
    //     for (int i = 1; i < 4; i++) {
    //         for (int j = 0; j < 4; j++) {
    //             if (board[i][j] < board[i - 1][j]) {
    //                 sum -= 4;
    //             }
    //         }
    //     }
    //     return sum;
    // }

    // Tests that the game has maintained higher tiles in either corner
    public static int scoreCorner(int[][] board) {
    	return (int)	(Math.max(rowScoreL(3, board), rowScoreR(3, board)) * weights[2]);
    }

    // public static int scoreCorner(int[][] board) {
    //     if (board[3][0] > board[3][3])
    //         return board[3][0] - board[3][3] + (int)(board[3][1] / 1.5);
    //     return board[3][3] - board[3][0] + (int)(board[3][2] / 1.5);
    // }

    // Score of the ith row, from left to right
    public static int rowScoreL(int i, int[][] board){
    	int sum = board[i][0];
        for(int j = 0; j < 2; j ++) {
        	if (board[i][j]/2 == board[i][j+1]) {
        		sum += board[i][j] * weights[2];
        	} else {
        		sum += board[i][j];
        	}
        }
        return sum;
    }

    // Score of the ith row, from right to left
    public static int rowScoreR(int i, int[][] board){
    	int sum = board[i][3];
        for(int j = 3; j > 0; j --) {
        	if (board[i][j]/2 == board[i][j-1]) {
        		sum += board[i][j] * 2;
        	} else {
        		sum += board[i][j];
        	}
        }
        return sum;
    }

    public static int log(int num){
        if(num <= 2) return 1;
        return 1 + log(num/2);
    }
}