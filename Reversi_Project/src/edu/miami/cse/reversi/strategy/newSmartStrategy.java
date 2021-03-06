package edu.miami.cse.reversi.strategy;
import java.util.Map;
import java.util.Set;
import edu.miami.cse.reversi.Board;
import edu.miami.cse.reversi.Player;
import edu.miami.cse.reversi.Square;
import edu.miami.cse.reversi.Strategy;

/**
 * A simple strategy that just chooses with Smart Strategy with alpha-beta
 *  from the squares available to the current player.
 */
public class SmartStrategyNew implements Strategy{

	private int[][] valueBook = {	{40, 0,20,16,16,20, 0,40},
									{ 0, 0,16, 8, 8,16, 0, 0},
									{20,16,12, 4, 4,12,16,20},
									{16, 8, 4, 0, 0, 4, 8,16},
									{16, 8, 4, 0, 0, 4, 8,16},
									{20,16,12, 4, 4,12,16,20},
									{ 0, 0,16, 8, 8,16, 0, 0},
									{40, 0,20,16,16,20, 0,40}};
	
	private long startTime;
	
	private Player currentPlayer;
	private Player oppoentPlayer;
	@Override
	public Square chooseSquare(Board board) {
		startTime = System.currentTimeMillis();
		currentPlayer=board.getCurrentPlayer();
		oppoentPlayer=currentPlayer.opponent();
		Square finalResult=alphaBetaSearch(board);
		return finalResult;
	}
	
	//Testing Alpha-Beta Search
		private int min(Board board, int alpha, int beta, int depth){
			if ((System.currentTimeMillis()-startTime) > 950) //0.95 seconds
				depth = 0;
			if(board.isComplete()){
				if(currentPlayer==board.getWinner()){
					return evaluate(board)+200;
				}else if(board.getWinner()==null){
					return evaluate(board);
				}else{
					return evaluate(board)-200;
				}
			}
			if(depth<=0){
				return evaluate(board);
			}
			int minValue=Integer.MAX_VALUE;
			Set<Square> possible=board.getCurrentPossibleSquares();
			if(possible.isEmpty()){
				return max(board.pass(),alpha, beta,depth-1)-80;
			}
			for(Square s:possible){
				Board newB=board.play(s);
				int cur=max(newB,alpha,beta,depth-1);
				
				if(cur<minValue){
					minValue=cur;
				}
				if(minValue<=alpha){
					break;
				}
				if(minValue<beta){
					beta=minValue;
				}
			
			}
			return minValue;
		}
		
		private int max(Board board, int alpha, int beta, int depth){
			if ((System.currentTimeMillis()-startTime) > 950) //0.95 seconds
				depth = 0;
			if(board.isComplete()){
				if(currentPlayer==board.getWinner()){
					return evaluate(board)+200;
				}else if(board.getWinner()==null){
					return evaluate(board);
				}else{
					return evaluate(board)-200;
				}
			}
			if(depth<=0){
				return evaluate(board);
			}
			int maxValue=Integer.MIN_VALUE;
			Set<Square> possible=board.getCurrentPossibleSquares();
			if(possible.isEmpty()){
				return min(board.pass(),alpha, beta,depth-1)-80;
			}
			for(Square s:possible){
				Board newB=board.play(s);
				int cur=min(newB,alpha,beta,depth-1);
				
				if(cur>maxValue){
					maxValue=cur;
				}
				if(maxValue>=beta){
					break;
				}
				if(maxValue>alpha){
					beta=maxValue;
				}
			}
			return maxValue;
		}
		
		private Square alphaBetaSearch(Board board){
			int alpha=Integer.MIN_VALUE;
			int beta=Integer.MAX_VALUE;
			Square result=null;
			int maxV=Integer.MIN_VALUE;
			
			for(Square s:board.getCurrentPossibleSquares()){
				Board newB=board.play(s);
				int value=min(newB,alpha,beta,4);
				if(value>=maxV){
					maxV=value;
					result=s;
				}
			}
			return result;
		}
	
	
	private int evaluate(Board board) {
		
		//Basic easy mode (count difference between number of pieces)
		int count = 0;
		Map<Player, Integer> counts = board.getPlayerSquareCounts();
		count += counts.get(currentPlayer);
		count -= counts.get(oppoentPlayer);
		count=count/2;
		//Improved mid mode (position value) and frontiers
		Map<Square, Player> storeSquareToPlayer = board.getSquareOwners();
		int frontiers = 0;
		for (Square s : storeSquareToPlayer.keySet()) {
			if (storeSquareToPlayer.get(s).equals(currentPlayer)) {					
				count += valueBook[s.getRow()][s.getColumn()];
				//frontiers calculation
				if (storeSquareToPlayer.get(new Square(s.getRow(), s.getColumn()+1))==null
						|| storeSquareToPlayer.get(new Square(s.getRow()+1, s.getColumn()+1))==null
						|| storeSquareToPlayer.get(new Square(s.getRow()+1, s.getColumn()))==null
						|| storeSquareToPlayer.get(new Square(s.getRow()+1, s.getColumn()-1))==null
						|| storeSquareToPlayer.get(new Square(s.getRow(), s.getColumn()-1))==null
						|| storeSquareToPlayer.get(new Square(s.getRow()-1, s.getColumn()-1))==null
						|| storeSquareToPlayer.get(new Square(s.getRow()-1, s.getColumn()))==null
						|| storeSquareToPlayer.get(new Square(s.getRow()-1, s.getColumn()+1))==null)
						{frontiers--;}
			}
			else if(storeSquareToPlayer.get(s).equals(oppoentPlayer)) {
				count -= valueBook[s.getRow()][s.getColumn()];
				//frontiers calculation
				if (storeSquareToPlayer.get(new Square(s.getRow(), s.getColumn()+1))==null
						|| storeSquareToPlayer.get(new Square(s.getRow()+1, s.getColumn()+1))==null
						|| storeSquareToPlayer.get(new Square(s.getRow()+1, s.getColumn()))==null
						|| storeSquareToPlayer.get(new Square(s.getRow()+1, s.getColumn()-1))==null
						|| storeSquareToPlayer.get(new Square(s.getRow(), s.getColumn()-1))==null
						|| storeSquareToPlayer.get(new Square(s.getRow()-1, s.getColumn()-1))==null
						|| storeSquareToPlayer.get(new Square(s.getRow()-1, s.getColumn()))==null
						|| storeSquareToPlayer.get(new Square(s.getRow()-1, s.getColumn()+1))==null)
						{frontiers++;}
			}
		}
		count += 3*frontiers;
		
		//Improved hard mode (relation and mobility)
		//mobility
		Set<Square> possibleSquare=board.getCurrentPossibleSquares();
		for(Square s:possibleSquare){
			count-=valueBook[s.getRow()][s.getColumn()]/2;
		}
		//relation?
		
		return count;
	}

}
