
import java.util.Arrays;
import java.util.Random;

/**
 * @author 		Saif Mahmud
 * @version     Aug. 7, 2020
 * @purpose		Make text-based connect 4 games with the use of JAVA Interface
 */
public class PlayerAI implements Player {
	
	private int size; // the size of the board
	private GameLogic gl;
	private static Random rand;
	private Status[][] board;
	private static int mustPlayCol = -1; //which column it should make move;
	public PlayerAI() {
		rand = new Random();
	}
	@Override
	/**
     * lastMove - called to indicate the last move of the opponent.
     * @param lastCol - column where the human played.
     */
	public void lastMove(int lastCol) {
		
		//check if someone won, or a draw.
		statusCheck(); 
		
		//The first move is by AI
		if(lastCol == -1) 
		{
			int num = rand.nextInt(size);
			board[drop(num)][num] = Status.TWO; //first move
			gl.setAnswer(num);
		} else {
			aiMove(lastCol);
		}
	}
	/**
     * aiMove - called to determine and make the move of the AI.
     * @param col - column where the human played.
     */
	private void aiMove(int col) 
	{	
		statusCheck();
		//first save the human move
		int gotRow = drop(col);
		board[gotRow][col] = Status.ONE;
		statusCheck();
		
		// now defend
		if(checkVertical(Status.ONE, 3)) {
			makeMove();
		} else if(checkHorizontal(Status.ONE, 3)){
			makeMove();
		} else if(checkDiagonal(Status.ONE, 3)) {
			makeMove();
		}
		
		// go offensive
		else if(checkVertical(Status.TWO, 3)){ 
			makeMove();
		} else if(checkHorizontal(Status.TWO, 3)){
			makeMove();
		} else if(checkDiagonal(Status.TWO, 3)) {
			makeMove();
		}
		
		// play as it wants
		else {
			int newCol = -1;
			int newRow = -1;
			
			 while (newRow < 0 || newCol < 0 || board[newRow][newCol] != Status.NEITHER)
			{
				newCol = rand.nextInt(size);
				newRow = drop(newCol);
			}
			board[newRow][newCol] = Status.TWO;
			int column = newCol+1;
			statusCheck();
			gl.setAnswer(newCol);
		}
	}
	/**
     * checkVertical - check for desired(param 2) numbers of pieces togather vertically.
     * @param s - Status type - which player's is to check
     * @param neededStreak - how many streak of pieces needed to be togather.
     * @return true - if they are found.
     */
	private boolean checkVertical(Status s, int neededStreak) 
	{
		boolean out = false;
		boolean streakFound = false;	
		for(int col = 0; col < size && !streakFound; col++)
		{
			for(int row = 0; row < size && !streakFound; row++)
			{				
				if(neededStreak == 3 && row + 2 < size && row - 1 > -1)
				{
					if(board[row][col] == s &&
					   board[row + 1][col] == s &&
					   board[row + 2][col] == s && 
					   board[row - 1][col] == Status.NEITHER)
					{
						streakFound = true;
					}
				} else if(neededStreak == 4 && row + 3 < size){
					if(board[row][col] == s &&
					   board[row+1][col] == s &&
					   board[row+2][col] == s &&
					   board[row+3][col] == s) 
					{
						streakFound = true;
					}
				}
			}
			if(streakFound && board[0][col] == Status.NEITHER) // means the row has empty spot
			{
				mustPlayCol = col;
				out = true;
			} else {
				streakFound = false;
				out = false;
			}
		}
		return out;
	}
	/**
     * checkVertical - check for desired(param 2) numbers of pieces togather horizontally.
     * @param s - Status type - which player's is to check
     * @param neededStreak - how many streak of pieces needed to be togather.
     * @return true - if they are found.
     */
	private boolean checkHorizontal(Status s, int neededStreak) {
		boolean streakFound = false;
		
		for(int row = 0; row < size && !streakFound; row++)
		{
			for(int col = 0; col < size && !streakFound; col++)
			{				
				if(neededStreak == 3 && col + 2 < size )
				{
					if(board[row][col] == s &&
					   board[row][col+1] == s &&
					   board[row][col+2] == s) {
						if(col + 3 < size && board[row][col+3] == Status.NEITHER) {
							streakFound = true;
							mustPlayCol = col + 3;
						} else if(col - 1 > -1 && board[row][col-1] == Status.NEITHER) {
							streakFound = true;
							mustPlayCol = col - 1;	
						}
					}
				} else if(neededStreak == 4 && col + 3 < size){
					if(board[row][col] == s &&
					   board[row][col+1] == s &&
					   board[row][col+2] == s &&
					   board[row][col+3] == s) 
					{
						streakFound = true;
					}
				}
			}
		}
		return streakFound;
	}
	/**
     * checkVertical - check for desired(param 2) numbers of pieces 
     * togather diagonally from both direction.
     * @param s - Status type - which player's is to check
     * @param neededStreak - how many streak of pieces needed to be togather.
     * @return true - if they are found.
     */
	private boolean checkDiagonal(Status s, int neededStreak) {
		boolean streakFound = false;
		for(int i = 0; i < size && !streakFound; i++)
		{
			if(neededStreak == 3 && i + 2 < size)
			{
				if(board[i][i] == s &&
						board[i+1][i+1] == s &&
						board[i+2][i+2] == s) {
					if(i + 3 > size && board[i+3][i+3] == Status.NEITHER)
					{
						streakFound = true;
						mustPlayCol = i + 3;
					} else if(i - 1 > -1 && board[i-1][i-1] == Status.NEITHER) {
						streakFound = true;
						mustPlayCol = i - 1;
					}
				}
			} 
		}
		if(!streakFound && neededStreak == 4)
		{
			//check from top left
			for(int i = 0; i < size && !streakFound; i++)
			{
				for (int j = 0; j < size && !streakFound; j++)
				{
					if(i < size-3 && j + 3 < size &&
							board[i][j] == s &&
							board[i+1][j+1] == s &&
							board[i+2][j+2] == s &&
							board[i+3][j+3] == s)
					{
						streakFound = true;
					}
				}
			}
			//check from top right if top left not found
			if(!streakFound)
			{
				for(int i = 0; i < size && !streakFound; i++)
				{
					for (int j = 3; j < size && !streakFound; j++)
					{
						if(i < size-3 && board[i][j] == s &&
								board[i+1][j-1] == s &&
								board[i+2][j-2] == s &&
								board[i+3][j-3] == s)
						{
							System.out.println("Got Top Right");
							streakFound = true;
						}
					}
				}
			}
		}
		return streakFound;
	}
	/**
     * makeMove - make the AI move on the static variable mustPlayCol column.
     */
	private void makeMove() 
	{
		int col = mustPlayCol;
		mustPlayCol = -1;
		if(col != -1)
		{
			board[drop(col)][col] = Status.TWO;
			int column = col+1;
			statusCheck();
			gl.setAnswer(col);
		}
	}
	/**
     * drop - find out the row of the parameter col which is playable 
     * @param col - column number
     * @return - the row number which is available to play.
     */
	private int drop(int col) {
        int posn = 0;
        while (posn < board.length && board[posn][col] == Status.NEITHER) {
            posn ++;
        }
        return posn-1;
    }
	@Override
	public void gameOver(Status winner) {
		System.out.println("Game over!");
        if (winner == Status.NEITHER) {
            System.out.println("Game is a draw.");
        } else if (winner == Status.ONE) {
            System.out.println("You win.");
        } else {
            System.out.println("Computer wins.");
        }
	}
	@Override
	/**
     * setInfo - letting the AI know the logic to be executed and the board size.
     * then initiating the board.
     * @param size - board size
     */
	public void setInfo(int size, GameLogic gl) 
	{
		this.gl = gl;
		this.size = size;
		board = new Status[size][size];
        for (Status[] s : board) {
            Arrays.fill(s, Status.NEITHER);
        }
	}
	/**
     * statusCheck - find out if someone won the game, a draw or should continue
     */
	private void statusCheck() 
	{
		boolean continueGame = false;
		for(int i = 0; i < size && !continueGame; i++)
		{
			if(board[0][i] == Status.NEITHER)
			{
				continueGame = true;
			}
		}
		if(!continueGame)
		{
			gameOver(Status.NEITHER);
			System.exit(0);
		} else if(checkVertical(Status.ONE, 4) || 
				  checkHorizontal(Status.ONE, 4) || 
				  checkDiagonal(Status.ONE, 4)) 
				{
					gameOver(Status.ONE);
					System.exit(0);
		} else if(checkVertical(Status.TWO, 4) || 
				  checkHorizontal(Status.TWO, 4) || 
				  checkDiagonal(Status.TWO, 4)) 
				{
					gameOver(Status.TWO);
					System.exit(0);
		}
	}
}// class