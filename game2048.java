import java.awt.event.*;
import java.util.*;

public class game2048 {

	private static int[][] grid;
	private static Random rand = new Random(System.currentTimeMillis());
		
	public game2048(){
		newGame();
	}
	

	public static void newGame() {
		grid = new int[4][4];
		placeRandom();
	}

	public static boolean movesPossible() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (grid[i][j] == 0)
					return true;
				if (i > 0 && grid[i-1][j] == grid[i][j])
					return true;
				if (j > 0 && grid[i][j-1] == grid[i][j])
					return true;
				if (i < 3 && grid[i+1][j] == grid[i][j])
					return true;
				if (j < 3 && grid[i][j+1] == grid[i][j])
					return true;
			}
		}
		return false;
	}

	public static void print(){
		for (int[] row : grid) {
			for (int i: row){
				if(i == 0)
					System.out.print("* ");
				else
					System.out.print(i + " ");
			}
			System.out.println();
		}
		System.out.println("\n");
	}

	public static void print(int[] row){
		for (int i: row){
			if(i == 0)
				System.out.print("* ");
			else
				System.out.print(i + " ");
		}
		System.out.println("\n");
	}

	public static boolean isEqual(int[] a, int[] b){
		for (int i = 0; i < 4; i++){
			if(a[i] != b[i])
				return false;
		}
		return true;
	}

	public static int getMax(){
		int max = 0;
		for(int[] row : grid){
			for(int i: row){
				max = Math.max(max, i);
			}
		}
		return max;
	}

	// return true if move was succesful
	public static boolean moveDown(){
		boolean moved = false;

		for (int j = 0; j < 4; j++) {
			int[] column = new int[4];
			
			int temp = 0;
			for(int i = 3; i >= 0; i--){
				if(grid[i][j] != 0){
					if(temp > 0 && grid[i][j] == column[temp - 1]){
						column[temp - 1] *= 2; 
					} else{
						column[temp++] = grid[i][j];
					}
				}
			}

			temp = 3;
			for(int[] row: grid){
				if(row[j] != column[temp]){
					row[j] = column[temp];
					moved = true;
				}
				temp--;
			}
		}
		if(moved)
			placeRandom();
		return moved;
	}

	// return true if move was succesful
	public static boolean moveUp(){
		boolean moved = false;

		for (int j = 0; j < 4; j++) {
			int[] column = new int[4];

			int temp = 0;
			for(int i = 0; i < 4; i++){
				if(grid[i][j] != 0){
					if(temp > 0 && grid[i][j] == column[temp - 1]){
						column[temp - 1] *= 2; 
					} else{
						column[temp++] = grid[i][j];
					}
				}
			}

			//print(column);

			temp = 0;
			for(int[] row: grid) {
				if(row[j] != column[temp]){
					row[j] = column[temp];
					moved = true;
				}
				temp++;
			}
		}
		if(moved)
			placeRandom();
		return moved;
	}

	// return true if move was succesful
	public static boolean moveRight(){
		boolean moved = false;

		for (int i = 0; i < 4; i++) {
			int[] column = new int[4];

			int temp = 3;
			for(int j = 3; j >= 0; j--){
				if(grid[i][j] != 0){
					if(temp < 3 && grid[i][j] == column[temp + 1]){
						column[temp + 1] *= 2; 
					} else{
						column[temp--] = grid[i][j];
					}
				}
			}

			if(!isEqual(column, grid[i])){
				moved = true;
				grid[i] = column;
			}
		}
		if(moved)
			placeRandom();
		return moved;
	}

	// return true if move was succesful
	public static boolean moveLeft(){
		boolean moved = false;

		for (int i = 0; i < 4; i++) {
			int[] column = new int[4];

			int temp = 0;
			for(int j = 0; j < 4; j++){
				if(grid[i][j] != 0){
					if(temp > 0 && grid[i][j] == column[temp - 1]){
						column[temp - 1] *= 2; 
					} else{
						column[temp++] = grid[i][j];
					}
				}
			}

			if(!isEqual(column, grid[i])){
				moved = true;
				grid[i] = column;
			}
		}
		if(moved)
			placeRandom();
		return moved;
	}

	// places the block in a random location (inefficient, I know -_-)
	public static void placeRandom(){
		int value = rand.nextInt(2)  * 2 + 2;
		boolean done = false;
		while(!done){
			int test1 = rand.nextInt(4);
			int test2 = rand.nextInt(4);

			if(grid[test1][test2] == 0){
				grid[test1][test2] = value;
				return;
			}
		}
	}
}