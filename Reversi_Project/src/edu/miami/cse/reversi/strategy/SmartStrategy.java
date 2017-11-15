package edu.miami.cse.reversi.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import edu.miami.cse.reversi.Board;
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

	//
	//int[][] b; //0 is blank space, 1 is player piece, -1 is opponent piece
	private int evaluate(int[][] b) {
		int numberValue = 0;
		int positionValue = 0;
		int relationValue = 0;

		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){

				if(){

				}else if(){

				}



			}

		}




		return numberValue + positionValue + relationValue;
	}

	private boolean isEnd(int[][] b){
		boolean full = true;
		boolean white = true;
		boolean black = true;
		boolean result = true;
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(full && b[i][j] == 0) full = false;
				else if(black && b[i][j] == 1) black = false;
				else if(white && b[i][j] == -1) white = false;

				if(!full && !black && !white){
					result = false;
					break;
				}
			}
			if(!result)
				break;

		}

		return result;
	}





}