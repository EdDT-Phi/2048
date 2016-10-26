import java.awt.event.*;
import java.util.*;

public class Game2048 {

	private static int[][] board;
	private static Random rand = new Random(System.currentTimeMillis());
		
	public Game2048(){
		newGame();
	}
	

	public static void newGame() {
		board = new int[4][4];
		placeRandom();
	}

	public static boolean movesPossible() {
		return movesPossible(board);
	}

	public static boolean movesPossible(int[][] g) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (g[i][j] == 0)
					return true;
				if (i > 0 && g[i-1][j] == g[i][j])
					return true;
				if (j > 0 && g[i][j-1] == g[i][j])
					return true;
			}
		}
		return false;
	}

	public static int countBlank(int[][] g) {
		int count = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if(g[i][j] == 0)
					count++;
			}
		}
		return count;
	}

	public static void print(){
		print(getBoard());
	}

	public static void print(int[] row){
		for (int i: row){
			if(i == 0)
				System.out.print("* ");
			else
				System.out.print(i + " ");
		}
		System.out.println();
	}

	public static void print(int[][] g){
		for(int[] row: g){
			print(row);
		}
	}

	public static boolean isEqual(int[] a, int[] b){
		for (int i = 0; i < 4; i++){
			if(a[i] != b[i])
				return false;
		}
		return true;
	}

	public static boolean isEqual(int[][] a, int[][] b){
		for (int i = 0; i < 4; i++){
			if(!isEqual(a[i], b[i]))
				return false;
		}
		return true;
	}

	public static int getMax(int[][] g){
		int max = 0;
		for(int[] row : g){
			for(int i: row){
				max = Math.max(max, i);
			}
		}
		return max;
	}

	// return true if move was succesful
	public static boolean moveDown(){
		int[][] temp = testDown(board);
		return genericMove(temp);
	}

	public static int[][] testDown(int[][] g){
		int[][] res =  new int[4][4];
		// for every column
		for (int j = 0; j < 4; j++) {
			int place = 3;
			for(int i = 3; i >= 0; i--){
				// move down, or combine
				if(g[i][j] != 0){
					if(place < 3 && res[place+1][j] == g[i][j])
						res[place+1][j] += g[i][j];
					else
						res[place--][j] = g[i][j];
				}
			}
		}
		return res;
	}

	// return true if move was succesful
	public static boolean moveUp(){
		int[][] temp = testUp(board);
		return genericMove(temp);
	}

	public static int[][] testUp(int[][] g){
		int[][] res =  new int[4][4];
		// for every column
		for (int j = 0; j < 4; j++) {
			int place = 0;
			for(int i = 0; i < 4; i++){
				// move up, or combine
				if(g[i][j] != 0){
					if(place > 0 && res[place-1][j] == g[i][j])
						res[place-1][j] += g[i][j];
					else
						res[place++][j] = g[i][j];
				}
			}
		}
		return res;
	}

	// return true if move was succesful
	public static boolean moveRight(){
		int[][] temp = testRight(board);
		return genericMove(temp);
	}

	public static int[][] testRight(int[][] g){
		int[][] res =  new int[4][4];
		// for every row
		for (int i = 0; i < 4; i++) {
			int place = 3;
			for(int j = 3; j >= 0; j--){
				// move right, or combine
				if(g[i][j] != 0){
					if(place < 3 && res[i][place+1] == g[i][j])
						res[i][place+1] += g[i][j];
					else
						res[i][place--] = g[i][j];
				}
			}
		}
		return res;
	}

	// return true if move was succesful
	public static boolean moveLeft(){
		int[][] temp = testLeft(board);
		return genericMove(temp);
	}

	public static int[][] testLeft(int[][] g){
		int[][] res =  new int[4][4];
		// for every row
		for (int i = 0; i < 4; i++) {
			int place = 0;
			for(int j = 0; j < 4; j++){
				// move left, or combine
				if(g[i][j] != 0){
					if(place > 0 && res[i][place-1] == g[i][j])
						res[i][place-1] += g[i][j];
					else
						res[i][place++] = g[i][j];
				}
			}
		}
		return res;
	}

	public static boolean genericMove(int[][] temp) {
		boolean moved = !isEqual(board, temp);
		if(moved){
			board = temp;
			placeRandom();
		}
		return moved;
	}

	// places the block in a random location (inefficient, I know -_-)
	public static void placeRandom(){
		int value = rand.nextInt(10);
		if(value == 0)
			value = 4;
		else
			value = 2;
		

		while(true) {
			int test1 = rand.nextInt(4);
			int test2 = rand.nextInt(4);

			if(board[test1][test2] == 0){
				board[test1][test2] = value;
				return;
			}
		}
	}

	// copies over the board, to prevent modification
	public static int[][] getBoard(){
		int[][] res = new int[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				res[i][j] = board[i][j];
			}
		}
		return res;
	}
}