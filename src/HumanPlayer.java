

/**
 * @author 		Saif Mahmud
 * @id          7808507
 * @version     Aug. 3, 2020
 * @instructor	
 * @assignment	
 * @purpose		
 */
public class HumanPlayer implements Human {

	//private UI textUI;
	private SwingGUI gui;
	private int boardSize;
	private GameLogic gl;
	public HumanPlayer(int boardSize, GameLogic gl) 
	{
		this.boardSize = boardSize;
		this.gl = gl;
		//textUI = new TextUI();
		//textUI.setInfo(this, boardSize);
		gui = new SwingGUI();
		gui.setInfo(this, boardSize);
	}
	
	@Override
	/**
	 * setAnswer - sends the parameter col the human played to Logic class
	 * @param col - the column number
	 */
	public void setAnswer(int col) 
	{
		String callerClass = new Throwable().getStackTrace()[1].getClassName();
		if(callerClass.endsWith("Logic"))
		{
			gui.lastMove(col);
		} else {
			gl.setAnswer(col);
		}
	}
}
