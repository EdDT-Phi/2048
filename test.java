import java.util.*;

public class test{
	static Game2048 game = new Game2048();
	static Random rand = new Random(System.currentTimeMillis());
    static LinkedList<int[][]> moves = new LinkedList<int[][]>();

	public static void main(String[] args) {
		

		// used to look at the last 1000 moves that led to a game over
		while(game.movesPossible(game.getBoard())) {
			if(moves.size() > 1000)
				moves.remove();
			AI.nextMove();
			moves.add(game.getBoard());
		}

		// print out
		for(int[][] g: moves){
			game.print(g);
			System.out.println();
		}
	}
}