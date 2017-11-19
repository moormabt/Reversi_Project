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

private int midLevelEva(Board board) {
		Player player = board.getCurrentPlayer();
		Player opponent = player.opponent();

		int totalValueOnBoard = 0;

		Map<Player, Integer> value = board.getPlayerSquareCounts();
		Map<Square, Player> storeSquareToPlayer = board.getSquareOwners();

		for (Square s : storeSquareToPlayer.keySet()) {
			if ((s.getRow() == 0 && s.getColumn() == 0) || (s.getRow() == 0 && s.getColumn() == 7)
					|| (s.getRow() == 7 && s.getColumn() == 0) || (s.getRow() == 7 && s.getColumn() == 7)) {
				if (storeSquareToPlayer.get(s).equals(player)) {
					totalValueOnBoard = totalValueOnBoard + 24;
				}
				if (storeSquareToPlayer.get(s).equals(opponent)) {
					totalValueOnBoard = totalValueOnBoard - 24;
				}
			} else if ((s.getRow() == 0 && s.getColumn() == 2) || (s.getRow() == 0 && s.getColumn() == 5)
					|| (s.getRow() == 2 && s.getColumn() == 0) || (s.getRow() == 2 && s.getColumn() == 7)
					|| (s.getRow() == 5 && s.getColumn() == 0) || (s.getRow() == 5 && s.getColumn() == 7)
					|| (s.getRow() == 7 && s.getColumn() == 2) || (s.getRow() == 7 && s.getColumn() == 5)) {
				if (storeSquareToPlayer.get(s).equals(player)) {
					totalValueOnBoard = totalValueOnBoard + 20;
				}
				if (storeSquareToPlayer.get(s).equals(opponent)) {
					totalValueOnBoard = totalValueOnBoard - 20;
				}
			} else if ((s.getRow() == 0 && s.getColumn() == 3) || (s.getRow() == 0 && s.getColumn() == 4)
					|| (s.getRow() == 1 && s.getColumn() == 2) || (s.getRow() == 1 && s.getColumn() == 5)
					|| (s.getRow() == 2 && s.getColumn() == 1) || (s.getRow() == 2 && s.getColumn() == 6)
					|| (s.getRow() == 3 && s.getColumn() == 0) || (s.getRow() == 3 && s.getColumn() == 7)
					|| (s.getRow() == 4 && s.getColumn() == 0) || (s.getRow() == 4 && s.getColumn() == 7)
					|| (s.getRow() == 5 && s.getColumn() == 1) || (s.getRow() == 5 && s.getColumn() == 6)
					|| (s.getRow() == 6 && s.getColumn() == 2) || (s.getRow() == 6 && s.getColumn() == 5)
					|| (s.getRow() == 7 && s.getColumn() == 3) || (s.getRow() == 7 && s.getColumn() == 4)) {
				if (storeSquareToPlayer.get(s).equals(player)) {
					totalValueOnBoard = totalValueOnBoard + 16;
				}
				if (storeSquareToPlayer.get(s).equals(opponent)) {
					totalValueOnBoard = totalValueOnBoard - 16;
				}
			} else if ((s.getRow() == 2 && s.getColumn() == 2) || (s.getRow() == 2 && s.getColumn() == 5)
					|| (s.getRow() == 5 && s.getColumn() == 2) || (s.getRow() == 5 && s.getColumn() == 5)) {
				if (storeSquareToPlayer.get(s).equals(player)) {
					totalValueOnBoard = totalValueOnBoard + 12;
				}
				if (storeSquareToPlayer.get(s).equals(opponent)) {
					totalValueOnBoard = totalValueOnBoard - 12;
				}
			}else if ((s.getRow() == 2 && s.getColumn() == 3) || (s.getRow() == 2 && s.getColumn() == 4)
					|| (s.getRow() == 3 && s.getColumn() == 2) || (s.getRow() == 3 && s.getColumn() == 5)
					|| (s.getRow() == 4 && s.getColumn() == 2) || (s.getRow() == 4 && s.getColumn() == 5)
					|| (s.getRow() == 5 && s.getColumn() == 3) || (s.getRow() == 5 && s.getColumn() == 4)) {
				if (storeSquareToPlayer.get(s).equals(player)) {
					totalValueOnBoard = totalValueOnBoard + 8;
				}
				if (storeSquareToPlayer.get(s).equals(opponent)) {
					totalValueOnBoard = totalValueOnBoard - 8;
				}
			}else if ((s.getRow() == 1 && s.getColumn() == 3) || (s.getRow() == 1 && s.getColumn() == 4)
					|| (s.getRow() == 3 && s.getColumn() == 1) || (s.getRow() == 3 && s.getColumn() == 6)
					|| (s.getRow() == 4 && s.getColumn() == 1) || (s.getRow() == 4 && s.getColumn() == 6)
					|| (s.getRow() == 6 && s.getColumn() == 3) || (s.getRow() == 6 && s.getColumn() == 4)) {
				if (storeSquareToPlayer.get(s).equals(player)) {
					totalValueOnBoard = totalValueOnBoard + 4;
				}
				if (storeSquareToPlayer.get(s).equals(opponent)) {
					totalValueOnBoard = totalValueOnBoard - 4;
				}
			}

		}
		
		if(totalValueOnBoard==0){
			totalValueOnBoard=evaluate(board);
		}

		return totalValueOnBoard;
	}


}
