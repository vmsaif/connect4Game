
/**
 * @author 		Saif Mahmud
 * @version     Aug. 7, 2020
 * @purpose		Make text-based connect 4 games with the use of JAVA Interface
 */
public interface Player {
    void lastMove(int lastCol);
    void gameOver(Status winner);
    void setInfo(int size, GameLogic gl);
}
