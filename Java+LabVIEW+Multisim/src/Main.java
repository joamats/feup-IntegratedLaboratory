import java.awt.EventQueue;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
	
		ArrayList<String[]> dataTable = ReadTableConfig.read("src./TableConfig.txt");
			
		// show initial interface and start writing program
		Interface Window = new Interface(dataTable);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window.frame.setVisible(true);					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		Boolean continueGame = false;
		int count = 1; // count number of files

		StringBuilder filename=new StringBuilder(); //StringBuilder and not string so we can pass the value by "reference"
		
		int time = 180; //wait for 3 minutes, so co simulation has time to run
		continueGame=Timer.searchTimer(count, filename, time); //search for new file during 3 min
		
		while (continueGame == true) { // while new files appear
			
			Signal Eog = new Signal(filename.toString()); //create eog signal and classify it
			
			String directionCode=Eog.getDirectionNumber(); //get signal's direction
			
			Window.labelBuilder(dataTable,directionCode); //update interface labels
			
			count++; // next file
			
			filename.setLength(0); //clear filename
			
			time=30;
			
			continueGame=Timer.searchTimer(count, filename, time); //search for new file during 30 secs
			
		}
		
		Window.endGameFrame();
	}
}