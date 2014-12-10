public class test2{
	public static void main(String[] args) {
		int[][] grid = {{0,0,0,0},
						{2,8,4,0},
						{2,16,8,4},
						{128,32,16,8}};
		System.out.println(AI.scoreNoUp(grid));
	}
}