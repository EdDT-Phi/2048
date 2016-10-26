public class test2{
	public static void main(String[] args) {

		// tests goodness of grids

		int[][] grid = {{0,0,0,0},
						{2,8,4,0},
						{2,16,8,4},
						{128,64,64,16}};
		System.out.println(AI.goodness(grid));
	}
}