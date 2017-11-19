package edu.miami.cse.reversi.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import edu.miami.cse.reversi.Board;
import edu.miami.cse.reversi.Player;
import edu.miami.cse.reversi.Square;
import edu.miami.cse.reversi.Strategy;

/**
 * A simple strategy that just chooses with Smart Strategy with alpha-beta
 *  from the squares available to the current player.
 */
public class SmartStrategy implements Strategy{

	private int[][] valueBook = {	{24, 0,20,16,16,20, 0,24},
									{ 0, 0,16, 4, 4,16, 0, 0},
									{20,16,12, 8, 8,12,16,20},
									{16, 4, 8, 0, 0, 8, 4,16},
									{16, 4, 8, 0, 0, 8, 4,16},
									{20,16,12, 8, 8,12,16,20},
									{ 0, 0,16, 4, 4,16, 0, 0},
									{24, 0,20,16,16,20, 0,24}};
	
	@Override
	public Square chooseSquare(Board board) {
		//return chooseOne(board.getCurrentPossibleSquares());
				
		/*
		 * for all possible moves
		 * 		return move with greatest alphabeta()
		 */
		List<Square> children = new ArrayList<>(board.getCurrentPossibleSquares());
		HashMap<Integer, Square> moveValues = new HashMap<Integer, Square>();
		
		int bestScore = Integer.MIN_VALUE;
		int score = Integer.MIN_VALUE;
		for (Square s: children) {
			score = alphabeta(board.play(s), 5, false, Integer.MIN_VALUE, Integer.MAX_VALUE); //set to 5 for now, modify as you will
			moveValues.put(score, s);
			if (score > bestScore)
				bestScore = score;
		}
		return moveValues.get(bestScore);

	}
	
	//temp implementation to push, works like minimax
	private int alphabeta(Board board, int depthControl, boolean isOpponent, int a, int b) { //takes boards of first move and returns value
		if (depthControl<1)
			return evaluate(board);
		
		List<Square> children = new ArrayList<>(board.getCurrentPossibleSquares()); //gets possible moves
		if (children.size()==0) { //if no possible moves
			if (board.isComplete())
				return evaluate(board);
			return alphabeta(board.pass(), (depthControl-1), !isOpponent, a, b);
		}
		
		
		if (!isOpponent) { //if player is our SmartStrategy
			//maximize
			
			int bestScore = Integer.MIN_VALUE;
			int score = Integer.MIN_VALUE;
			for (Square s: children) {
				score = alphabeta(board.play(s), (depthControl-1), !isOpponent, a, b);
				if (score > bestScore)
					bestScore = score;
				a = Math.max(a, score);
				if (b <= a)
					break; //beta cut off
			}
			return bestScore;
		}
		
		
		if (isOpponent) { //if player is opponent
			//minimize
			
			int bestScore = Integer.MAX_VALUE;
			int score = Integer.MAX_VALUE;
			for (Square s: children) {
				score = alphabeta(board.play(s), (depthControl-1), !isOpponent, a, b);
				if (score < bestScore)
					bestScore = score;
				b = Math.min(b, score);
				if (b <= a)
					break; //alpha cut off
			}
			return bestScore;
		}
		
		//do stuff |ignore this|
		//alphabeta(newboard, (depthControl-1));
		
		return -1;
	}
	
	
	private int evaluate(Board board) {
		
		Player currentPlayer = board.getCurrentPlayer();
		Player oppoentPlayer = currentPlayer.opponent();
		
		//when board is finished
		if(board.isComplete()){
			//winner is current player return maxValue
			if(board.getWinner().equals(currentPlayer))
				return Integer.MAX_VALUE;
			//winner is opponent player return minValue
			else if(board.getWinner().equals(oppoentPlayer))
				return Integer.MIN_VALUE;
			//Tie
			else
				return 0;
		}
		
		//Basic easy mode (count difference between number of pieces)
		int count = 0;
		Map<Player, Integer> counts = board.getPlayerSquareCounts();
		count += counts.get(currentPlayer);
		count -= counts.get(oppoentPlayer);

		//Improved mid mode (position value)
		Map<Square, Player> storeSquareToPlayer = board.getSquareOwners();
		for (Square s : storeSquareToPlayer.keySet()) {
			if (storeSquareToPlayer.get(s).equals(currentPlayer))
				count += valueBook[s.getRow()][s.getColumn()];
			else if(storeSquareToPlayer.get(s).equals(oppoentPlayer))
				count -= valueBook[s.getRow()][s.getColumn()];
		}
		
		//Improved hard mode (relation and mobility)
		//mobility
		count += board.getCurrentPossibleSquares().size();
		//relation?
		
		return count;
	}

}