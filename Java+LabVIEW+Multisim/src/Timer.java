
public class Timer {

	public static Boolean searchTimer(int count, StringBuilder filename, int time) {
	Boolean continueGame = false;
	
	for (double i=0; i<time;i=i+0.1) {
		continueGame = Reader.verifyDocumentExistence(count, filename); //verify if next eog file exists
		if(continueGame) {
			return continueGame;
		}
		try
		{
			Thread.sleep(100); //wait for 0.1 seconds until it searches for a new document again
		}
		catch(InterruptedException ex)
		{
		    Thread.currentThread().interrupt();
		}
	}
	return continueGame;
}
}
