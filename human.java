import java.util.*;

public class Human{


	public static void main(String args[]){
		
		Game2048 game = new Game2048();
		Scanner sc = new Scanner(System.in);
		boolean moved = true;

		while(game.movesPossible()){
			if(moved)
				game.print();
			switch(sc.next()){
				case "down":
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
					game.testDown(game.getBoard());
					break;
				case "exit":
					return;
				default:
				//do nothing
					moved = false;
			}
		}

		System.out.println("Game over");
	}
}
	
	