
/**
 * @author 		Saif Mahmud
 * @version     Aug. 7, 2020
 * @purpose		Make text-based connect 4 games with the use of JAVA Interface
 */
public interface UI {
    void lastMove(int lastCol);
    void gameOver(Status winner);
    void setInfo(Human h, int size);
}
