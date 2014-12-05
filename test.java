import java.util.*;

public class test{
	static game2048 game = new game2048();
	static Random rand = new Random(System.currentTimeMillis());

	public static void main(String[] args) {
		

		Scanner sc = new Scanner(System.in);

		while(game.movesPossible(game.getGrid())) {

			System.out.println("Goodness: " + AI.goodness(game.getGrid()));
			game.print();
			System.out.println();
			int[][] down = game.testDown(game.getGrid());
			int[][] right = game.testRight(game.getGrid());
			int[][] left = game.testLeft(game.getGrid());

			int downb = AI.scoreBlank(down);
			int rightb = AI.scoreBlank(right);
			int leftb = AI.scoreBlank(left);

			int downs = AI.getScore(down);
			int rights = AI.getScore(right);
			int lefts = AI.getScore(left);

			int downg = AI.goodness(down);
			int rightg = AI.goodness(right);
			int leftg = AI.goodness(left);


			if(game.isEqual(game.getGrid(), down) || !game.movesPossible(down))
				downg = 0;
			if(game.isEqual(game.getGrid(), right) || !game.movesPossible(right))
				rightg = 0;
			if(game.isEqual(game.getGrid(), left) || !game.movesPossible(left))
				leftg = 0;

			System.out.println("Goodness Down:\t" + downg + ",\t" + downb + ",\t" + downs);
			//System.out.println();
			System.out.println("Goodness Right:\t" + rightg + ",\t" + rightb + ",\t" + rights);
			//game.print(right);
			//System.out.println();
			System.out.println("Goodness Left:\t" + leftg + ",\t" + leftb + ",\t" + lefts);

			//game.print(left);
			//System.out.println();
			System.out.println();

			if(downg + leftg + rightg == 0)
				game.moveUp();
			else if(downg >= leftg && downg >= rightg)
				game.moveDown();
			else if(rightg > leftg)
				game.moveRight();
			else
				game.moveLeft();

			sc.next();
		}
	}
}