import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

public class Reader {

	public static Boolean verifyDocumentExistence(int count, StringBuilder filename) {
		File myDirectory = new File("src/Signals./"); // directory of files

		String[] fileNames = myDirectory.list(); // create list with filenames on the directory
		
		String fileNumber=String.valueOf(count);
		
		for (String name : fileNames) {
			String number=name.substring(4); 
			number=number.substring(0,number.length()-4); //get number of textfile
			
			if (name.indexOf("eog")!=-1 && number.equals(fileNumber)) { // check if next file exists
				filename.append(name); //updates the current filename
				return true;
			}
		}
		return false;
	}

	public static ArrayList<Double> getEOG(String filename) {

		File file = new File("src/Signals./"+filename); // create document

		ArrayList<Double> data = new ArrayList<Double>();

		try {
			Scanner input = new Scanner(file); // read from file

			while (input.hasNextLine()) { // while there are new lines in the document
				String line = input.nextLine();
				data.add(Double.parseDouble(line)); // adds line in data, as a double
			}
			input.close(); // close input file

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return data;
	}

}
