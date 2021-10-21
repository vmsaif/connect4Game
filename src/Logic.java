
import java.util.Random;

/**
 * @author 		Saif Mahmud
 * @id          7808507
 * @version     Aug. 7, 2020
 * @instructor	Ali Neshati
 * @assignment	03
 * @purpose		Make text-based connect 4 games with the use of JAVA Interface
 */
public class Logic implements GameLogic {

	private int boardSize;
	private Player ai;
	private Human h;
	private Random rand = new Random();
	/**
	 * Logic - constructor 
	 * @param boardSize - the size of the board.
	 */
	public Logic(int boardSize)
	{
		this.boardSize = boardSize;
		ai = new PlayerAI();
		ai.setInfo(boardSize, this);
		h = new HumanPlayer(boardSize, this);
		play();
	}
	/**
	 * play - start the game.
	 */
	private void play() 
	{	
		if(firstMoveMaker() == Status.ONE) // human plays first
		{
			System.out.println("First Move: Player 1");
			h.setAnswer(-1);;
		} else { 							// AI plays first
			System.out.println("First Move: Player 2");
			ai.lastMove(-1);
		}
	}
	/**
	 * firstMoveMaker - helper method to determine the first player to make the move
	 * @return Status of which player makes the first move - AI or HUMAN
	 */
	private Status firstMoveMaker()
	{
		Status out = Status.TWO;
		int num = rand.nextInt(2);
		if(num == 0)
		{
			out = Status.ONE;
		}
		return out;
	}
	@Override
	/**
	 * setAnswer - pass the parameter value to the class PlayerAI or Human depending on the 
	 * opposite of which class called it.
	 * @param col
	 */
	public void setAnswer(int col) {
		String callerClass = new Throwable().getStackTrace()[1].getClassName();
		if(callerClass.endsWith("HumanPlayer"))
		{
			// came from human -> going to ai
			ai.lastMove(col);
		} else {
			// came from ai -> going to human
			h.setAnswer(col);
		}
	}
}//class