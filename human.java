import java.util.*;

public class human{

	static Scanner sc = new Scanner(System.in);

	public static void main(String args[]){
		boolean moved = true;

		game2048 game = new game2048();

		while(true){
			if(moved)
				game.print();
			switch(sc.next()){
				case "down": // down
					moved = game.moveDown();
					break;
				case "up":
					moved = game.moveUp();
					break;
				case "right":
					moved = game.moveRight();
					break;
				case "left":
					moved = game.moveLeft();
					break;
				case "testDown":
					game.testDown(game.getGrid());
					break;
				default:
				//do nothing
					moved = false;
					break;
			}
		}
	}
}
	
	