import java.util.Scanner;

import com.tictactoe.engine.player.ai.MiniMax;
import com.tictactoe.engine.player.ai.MoveStrategy;
import com.tictactoe.engine.board.Board;
import com.tictactoe.engine.board.Move;

public class JTicTacToe {

	private static final int SEARCH_DEPTH = 9;

	public static void main(String[] args) throws Exception {
		int move_index = 0;
		Board board = Board.createStandardBoard();
		Move move = null;
	    Scanner input = new Scanner(System.in);
	    boolean checkGameOverWithDraw = false;
	    int turn = 0;

	    //comment
		do {
//			if ((turn%2==0)) {
			//				System.out.println("make move 0 - 9: \n");
			//	move_index = input.nextInt();
			//try {
			//	move = Move.createMove(board, move_index);
			//	board = move.execute();
			//	System.out.println("your move \n"+board.toString());
			//	
			//} catch (Exception e) {
			//	System.out.println("Invalid input \n");
			//}
			//}
			//else {
				move = getBestMove(board);
				board = move.execute();
				System.out.println("ai move \n"+board.toString());				
//			}
			
			//check draw
			checkGameOverWithDraw = board.checkGameOverWithDraw();
			
			turn++;
			
		} while (!board.getCurrentPlayer().getOpponent().isWin() && !checkGameOverWithDraw);

		if (checkGameOverWithDraw) {
			System.out.println("Draw!");
		}
		else {
			System.out.println(board.getCurrentPlayer().getOpponent().getPieceType().getPieceName() + " Win!");			
		}
	}
	
	private static Move getBestMove(Board board) throws Exception {
        final MoveStrategy strategy = new MiniMax(SEARCH_DEPTH);
        Move bestMove = strategy.execute(board);

		return bestMove;
	}
}
