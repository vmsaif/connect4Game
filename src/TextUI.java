
/**
 * @author 		Assigmnent Question
 * @id          7808507
 * @version     Aug. 7, 2020
 * @instructor	Ali Neshati
 * @assignment	03
 * @purpose		Make text-based connect 4 games with the use of JAVA Interface
 */
import java.util.Arrays;
import java.util.Scanner;

/**
 * This class implements the UI interface through a Scanner-based
 * implementation. The board is printed through System.out and the
 * Scanner reads input from the user.
 *
 * See the assignment for description of the UI required methods.
 *
 */
public class TextUI implements UI {

    private Status[][] board; // internal board storage.
    private Scanner kbd; // Scanner for reading from the user.
    private Human human; // pointer to the Human object using this UI.

    public TextUI () {
        kbd = new Scanner(System.in);
    }
    /**
     * rowToString - private helper method to print a single
     * row of the board
     * @param s - an array from the board to be printed.
     * @return - String representation of the board.
     */
    private String rowToString(Status[] s) {

        String out = "";
        for (Status curr : s) {
            switch (curr) {
                case ONE:
                    out += "O  ";
                    break;
                case TWO:
                    out += "X  ";
                    break;
                case NEITHER:
                    out += ".  ";
                    break;
            }
        }
        return out;
    }
    /**
     * printBoard - private helper method to print the board.
     */
    private void printBoard() {
    	for(int i = 1; i < 13; i++)
    	{
    		System.out.print(i + "  ");
    	}
    	System.out.println();
        for (Status[] s : board) {
            System.out.println(rowToString(s));
            System.out.println();
        }
        for(int i = 1; i < 13; i++)
    	{
    		System.out.print(i + "  ");
    	}
        System.out.println();
    }
    /**
     * verifyCol - private helper method to determine if an integer is a valid
     * column that still has spots left.
     * @param col - integer (potential column number)
     * @return - is the column valid?
     */
    private boolean verifyCol(int col) {
        return (col >= 0 && col < board[0].length && board[0][col] == Status.NEITHER);
    }
    /**
     * lastMove - called to indicate the last move of the opponent. See the
     * assignment for more detail.
     * @param lastCol - column where the opponent played.
     */
    public void lastMove(int lastCol) {

        if (lastCol != -1) {
            int lastPosn = drop(lastCol);
            board[lastPosn][lastCol] = Status.TWO; // this is the AI's move, so it's TWO.
        }
        
        // show board
        System.out.println("BOARD");
        printBoard();


        // prompt for input and verify
        System.out.println("What column do you want to play?");
        int num = kbd.nextInt()-1;

        while (!verifyCol(num)) {
            System.out.println("Sorry, that column is not available.");
            System.out.println("What column do you want to play?");
            num = kbd.nextInt()-1;
        }
        int posn = drop(num);
        board[posn][num] =  Status.ONE; // this is the human's move, so it's ONE.
        human.setAnswer(num); // tell the human class where the human person chose.   
    }
    /**
     * gameOver - called when the game is over. See assignment
     * for more details
     * @param winner - who won the game or NEITHER if the game is a draw.
     */
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
    /**
     * setInfo - called before any other action. Sets the Human and size
     * of the board. See assignment for more details.
     * @param h - pointer to the human that is using this UI
     * @param size - size of the board.
     */
    @Override
    public void setInfo(Human h, int size) {
        this.human = h;
        board = new Status[size][size];
        for (Status[] s : board) {
            Arrays.fill(s, Status.NEITHER);
        }
    }
    /**
     * drop - a private helper method that finds the position of a marker
     * when it is dropped in a column.
     * @param col the column where the piece is dropped
     * @return the row where the piece lands
     */
    private int drop(int col) {
        int posn = 0;
        while (posn < board.length && board[posn][col] == Status.NEITHER) {
            posn ++;
        }
        return posn-1;
    }
}// class