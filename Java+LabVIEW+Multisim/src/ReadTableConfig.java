import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadTableConfig {


	public static ArrayList<String[]> read(String filename) {
		
		File file = new File(filename); // create document

		ArrayList<String[]> data = new ArrayList<String[]>();

		try {
			Scanner input = new Scanner(file); // read from file

			while (input.hasNextLine()) { // while there are new lines in the document
				String line = input.nextLine();
				String[] sepLine = line.split("\t");	//splits data, like istringstream
				data.add(sepLine); // adds line in data
			}
			
			input.close(); // close input file
		
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return data;
		
	}
	
	public static void main(String args[]) {
		
		
		ArrayList<String[]> data = read("src./TableConfig.txt");
		
		for(int i = 0; i < data.size(); i++) {
			for(int k = 0; k < 5; k++) {
				
				System.out.print(data.get(i)[k]);
				System.out.print(' ');

			
			System.out.println("\n");
				
		
	}
		}
}
}
