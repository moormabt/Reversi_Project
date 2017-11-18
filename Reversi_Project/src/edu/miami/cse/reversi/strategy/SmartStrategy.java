package edu.miami.cse.reversi.strategy;

import java.util.ArrayList;
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

	@Override
	public Square chooseSquare(Board board) {
		return chooseOne(board.getCurrentPossibleSquares());
	}

	/**
	 * A simple utility method for selecting a item with alpha-beta algorithm 
	 * from a set.
	 * 
	 * @param itemSet
	 *          The set of items from which to select.
	 * @return A 'best' item to maximize the benefit from the set.
	 */
	public static <T> T chooseOne(Set<T> itemSet) {
		List<T> itemList = new ArrayList<>(itemSet);
		return itemList.get(new Random().nextInt(itemList.size()));
	}

	//alpha beta search (deep) { ........ evaluate(tempBoard)}

	
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

		return count;
	}




}