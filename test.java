import java.util.*;

public class test{
	static game2048 game = new game2048();
	static Random rand = new Random(System.currentTimeMillis());
    static LinkedList<int[][]> moves = new LinkedList<int[][]>();

	public static void main(String[] args) {
		

		Scanner sc = new Scanner(System.in);

		while(game.movesPossible(game.getGrid())) {
			if(moves.size() > 1000)
				moves.removeFirst();
			AI.nextMove();
			moves.addLast(game.getGrid());
		}

		for(int[][] g: moves){
			game.print(g);
/*			System.out.println("Down: " + AI.goodness(game.testDown(g)));
			System.out.println("Right: " + AI.goodness(game.testRight(g)));
			System.out.println("Left: " + AI.goodness(game.testLeft(g)));*/
			System.out.println();
		}
	}
}