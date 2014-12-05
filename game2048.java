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

	public static boolean movesPossible(int[][] g) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (g[i][j] == 0)
					return true;
				if (i > 0 && g[i-1][j] == g[i][j])
					return true;
				if (j > 0 && g[i][j-1] == g[i][j])
					return true;
				if (i < 3 && g[i+1][j] == g[i][j])
					return true;
				if (j < 3 && g[i][j+1] == g[i][j])
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
		for (int[] row : grid) {
			for (int i: row){
				if(i == 0)
					System.out.print("* ");
				else
					System.out.print(i + " ");
			}
			System.out.println();
		}
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
			for (int j = 0; j < 4; j++){
				if(a[i][j] != b[i][j])
					return false;
			}
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
		int[][] temp = testDown(grid);
		boolean moved = !isEqual(grid, temp);
		if(moved){
			grid = temp;
			placeRandom();
		}
		return moved;
	}

	public static int[][] testDown(int[][] g){
		int[][] res =  new int[4][4];
		for (int j = 0; j < 4; j++) {
			int[] column = new int[4];
			
			int temp = 0;
			for(int i = 3; i >= 0; i--){
				if(g[i][j] != 0){
					if(temp > 0 && g[i][j] == column[temp - 1]){
						column[temp - 1] *= 2; 
					} else{
						column[temp++] = g[i][j];
					}
				}
			}

			temp = 3;
			for(int[] row: res){
				row[j] = column[temp];
				temp--;
			}
		}
		return res;
	}

	// return true if move was succesful
	public static boolean moveUp(){
		int[][] temp = testUp(grid);
		boolean moved = !isEqual(grid, temp);
		if(moved){
			grid = temp;
			placeRandom();
		}
		return moved;
	}

	public static int[][] testUp(int[][] g){
		int[][] res = new int[4][4];
		for (int j = 0; j < 4; j++) {
			int[] column = new int[4];

			int temp = 0;
			for(int i = 0; i < 4; i++){
				if(g[i][j] != 0){
					if(temp > 0 && g[i][j] == column[temp - 1]){
						column[temp - 1] *= 2; 
					} else{
						column[temp++] = g[i][j];
					}
				}
			}

			temp = 0;
			for(int[] row: res) {
				row[j] = column[temp];
				temp++;
			}
		}
		return res;
	}

	// return true if move was succesful
	public static boolean moveRight(){
		int[][] temp = testRight(grid);
		boolean moved = !isEqual(grid, temp);
		if(moved){
			grid = temp;
			placeRandom();
		}
		return moved;
	}

	public static int[][] testRight(int[][] g){
		int[][] res = new int[4][4];
		for (int i = 0; i < 4; i++) {
			int[] column = new int[4];

			int temp = 3;
			for(int j = 3; j >= 0; j--){
				if(g[i][j] != 0){
					if(temp < 3 && g[i][j] == column[temp + 1]){
						column[temp + 1] *= 2; 
					} else{
						column[temp--] = g[i][j];
					}
				}
			}

			res[i] = column;
		}
		return res;
	}

	// return true if move was succesful
	public static boolean moveLeft(){
		int[][] temp = testLeft(grid);
		boolean moved = !isEqual(grid, temp);
		if(moved){
			grid = temp;
			placeRandom();
		}
		return moved;
	}

	public static int[][] testLeft(int[][] g){
		int[][] res = new int[4][4];
		for (int i = 0; i < 4; i++) {
			int[] column = new int[4];
			int temp = 0;
			for(int j = 0; j < 4; j++){
				if(g[i][j] != 0){
					if(temp > 0 && g[i][j] == column[temp - 1]){
						column[temp - 1] *= 2; 
					} else{
						column[temp++] = g[i][j];
					}
				}
			}

			res[i] = column;
		}
		return res;
	}

	// places the block in a random location (inefficient, I know -_-)
	public static void placeRandom(){
		int value = rand.nextInt(10);
		if(value == 0)
			value = 4;
		else
			value = 2;
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

	public static int[][] getGrid(){
		int[][] res = new int[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				res[i][j] = grid[i][j];
			}
		}
		return res;
	}
}