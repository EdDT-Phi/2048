public class AI {


    static game2048 game = new game2048();
    static int moves = 5;
    static double[] weights = { 4, 3, 0.4, 1.7};

    public static void main(String[] args) {
        //for (weights[0] = 0; weights[0] < 100; weights[0] += 10) {
        //for(int times = 0; times < 3; times++){
            int max = 0;
            int min = 1000000;
            int win = 0;
            int[] distro = new int[6];
            for (int i = 0; i < 1000; i++) {
                boolean moved = false;

                while (nextMove()) {
                }

                int thismax = game.getMax(game.getGrid());

                if (thismax == 8192) {
                    distro[5]++;
                    win++;
                } else if (thismax == 4096) {
                    distro[4]++;
                    win++;
                } else if (thismax == 2048) {
                    distro[3]++;
                    win++;
                } else if (thismax == 1024) {
                    distro[2]++;
                } else if (thismax == 512) {
                    distro[1]++;
                } else {
                    distro[0]++;
                }

                max = Math.max(max, thismax);
                min = Math.min(min, thismax);
                game.newGame();

                if (i % 300 == 0) {
                    System.out.println(i);
                }

            }
            //System.out.println("Moves: " + moves);
            System.out.println("Weight: " + weights[0]);
            System.out.println("Max: " + max);
            System.out.println("Won: " + win * 100 / 1000 + "%");
            System.out.println("Distro:");
            //System.out.println("\t8192 - " + distro[5]);
            System.out.println("\t4096 - " + distro[4]);
            System.out.println("\t2048 - " + distro[3]);
            System.out.println("\t1024 - " + distro[2]);
            System.out.println("\t512 - " + distro[1]);
            System.out.println("\tother - " + distro[0]);
            System.out.println();
        }
    //}

    public static boolean nextMove() {
        String direction = findBest(game.getGrid(), moves, false);
        switch (direction) {
        case "down": // down
            return game.moveDown();
        case "right":
            return game.moveRight();
        case "left":
           return game.moveLeft();
        case "up":
            return game.moveUp();
        }
        game.print();
        System.out.println(direction);
        return false;
    }

    public static String findBest(int[][] grid, int i, boolean debug) {
        int down = findMove(game.testDown(grid), i, grid, "Down", debug);
        int left = findMove(game.testLeft(grid), i, grid, "Left", debug);
        int right = findMove(game.testRight(grid), i, grid, "Right", debug);

        if (debug) {
            System.out.println("Down: " + down);
            System.out.println("Right: " + right);
            System.out.println("Left: " + left);
        }

        if (down != -3000 && down >= left && down >= right)
            return "down";
        if (right != -3000 && right > left)
            return "right";
        if (left != -3000)
            return "left";
        return "up";
    }

    public static int findMove(int[][] grid, int i, int[][] oldGrid, String s, boolean debug) {
        if (debug)
            System.out.println("Debug here");
        if (game.isEqual(grid, oldGrid) || !game.movesPossible(grid))
            return (i == moves) ? -3000 : 0;
        if (i == 0) {
            return goodness(grid);
        }
        return  (int) (goodness(grid) + (Math.max(Math.max(
                                             findMove(game.testDown(grid), i - 1, grid, s + ", Down", debug),
                                             findMove(game.testLeft(grid), i - 1, grid, s + ", Left", debug)),
                                         findMove(game.testRight(grid), i - 1, grid, s + ", Right", debug))) / weights[3]);
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
}