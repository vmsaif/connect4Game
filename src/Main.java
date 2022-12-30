
import java.util.Random;
/**
 * @author 		Saif Mahmud
 * @version     Aug. 7, 2020
 * @purpose		Make text-based/GUI based connect 4 games with the use of JAVA Interface
 */
public class Main 
{
	private static Random rand = new Random();
	
	public static void main(String[] args) 
	{
		GameLogic gl = new Logic(5 + rand.nextInt(3));
	}
}//class
