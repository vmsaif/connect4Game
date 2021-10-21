
/**
 * @author 		Saif Mahmud
 * @id          7808507
 * @version     Aug. 7, 2020
 * @instructor	Ali Neshati
 * @assignment	03
 * @purpose		Make text-based connect 4 games with the use of JAVA Interface
 */
public interface UI {
    void lastMove(int lastCol);
    void gameOver(Status winner);
    void setInfo(Human h, int size);
}
