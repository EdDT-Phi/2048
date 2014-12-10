import java.util.*;

public class test{
	static game2048 game = new game2048();
	static Random rand = new Random(System.currentTimeMillis());

	public static void main(String[] args) {
		

		Scanner sc = new Scanner(System.in);

		while(game.movesPossible(game.getGrid())) {

			String direction = AI.findBest(game.getGrid(), 4, args[0].equals("true"));
			game.print();
			System.out.println();
			switch (direction){
				case "down": // down
					game.moveDown();
					break;
				case "right":
					game.moveRight();
					break;
				case "left":
					game.moveLeft();
					break;
				case "up":
					game.moveUp();
					break;
			}
			/*try{
				Thread.sleep(500);
			} catch (InterruptedException e){

			}*/
			sc.next();
		}
	}
}