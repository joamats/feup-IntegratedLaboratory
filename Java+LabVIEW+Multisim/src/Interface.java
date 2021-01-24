import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Vector;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.io.BufferedWriter;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

public class Interface {

	public JFrame frame;
	private JTextField messageField;
	private String numbers;
	public Vector<String> labelText;
	private JTextPane upText;
	private JTextPane leftText;
	private JTextPane downText;
	private JTextPane rightText;
	private JInternalFrame warningFrame;
	
	int formatnumber = 0;
	private JTextField textField;
	
	//the dimensions and positions used for labels and warning are according with a smaller screen
	//the commented parameters when using setBounds can be used for larger screens
	
	//create warning panel
	private void createWarning(int time, String warningMessage) {
		JInternalFrame warningFrame = new JInternalFrame("Ocular Control");
		warningFrame.setClosable(true);
		warningFrame.setToolTipText("");
		warningFrame.setBounds(220,230,250, 250); //(230,400,250,250);
		frame.getContentPane().add(warningFrame);
		
		textField = new JTextField();
		warningFrame.getContentPane().add(textField, BorderLayout.CENTER); 
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setEditable(false);
		textField.setFont(new Font("Application", Font.BOLD, 20));
		textField.setColumns(10);
		textField.setText(warningMessage);
		warningFrame.setVisible(true);
		warningFrame.toFront();
		
		this.warningFrame = warningFrame;
		
		try
		{
			Thread.sleep(time); //wait for 1 seconds so that the user has time to think: re-adjust time if needed!!
		}
		catch(InterruptedException ex)
		{
		    Thread.currentThread().interrupt();
		}
	}
	
	//create warning that message is saved
	private void savedMessageFrame() {
		createWarning(2000, "MESSAGE SAVED"); //show warning message
		
		warningFrame.dispose(); //close warning window
	}
	
	//create warning that email is sent
	private void emailSentMessageFrame() {
		createWarning(2000, "EMAIL SENT"); //show warning message
		
		warningFrame.dispose(); //close warning window
	}
	
	//create warning that game is over	
	public void endGameFrame() {
		createWarning(3000, "Time Out!"); //show warning message
		
		try {							 //creates a file when the game is over
			String ExitFilename = "EXIT.txt";
			FileWriter ExitFile = new FileWriter("src/Signals./"+ExitFilename);
			ExitFile.write("EXIT");
			ExitFile.close();
		} 
		 catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		 }
		//close all interface
		frame.setVisible(false);
		frame.dispose();
		System.exit(0);
		
		
	}
	
	//update labels in frame
	private void updateLabels(ArrayList<String[]> configu, int R) {
		
		for (int i = 1; i < 5; i++) 
			labelText.add(configu.get(R)[i]); //update LabelText in new label
		
		//update in frame
		leftText.setText(labelText.get(0));
		upText.setText(labelText.get(1));
		rightText.setText(labelText.get(2));
		downText.setText(labelText.get(3));
	}
	
	//build appropriate label
	public void labelBuilder(ArrayList<String[]> configu, String direction) 
	{					
			numbers=numbers.concat(direction); // update direction code
			
		if (numbers.length() == 1)
		{
			if (numbers.equals("1")) { // NEW OPTION
				
				labelText.clear(); //clears the vector
				int R= Integer.parseInt(numbers);
				
				updateLabels(configu, R); //update labels and frame
			}
			else if(numbers.equals("2")) {  //DELETE OPTION
				if (!messageField.getText().isEmpty())
					{
						int position = messageField.getText().length() - 1 ;
						messageField.setText(messageField.getText().substring(0, position)); //eliminates the last character in the message
					}
				numbers = "";
			}
			else if(numbers.equals("3")) {  //EXIT OPTION
				endGameFrame();
			}
			else if(numbers.equals("4")) { //SAVE OPTION
				
				numbers=numbers+"11"; //so that the row is recognized in the table
				
				for (int i = 0; i < configu.size(); i++) // reads the table
				{
					if (configu.get(i)[0].contains(numbers) && configu.get(i)[0].equals(numbers)) // if the first column of a specific row has that number (aka
																// direction)
					{ // save the info in LabelText
						labelText.clear(); //clears the vector
						
						updateLabels(configu, i); //update labels and frame
						
						break; //when labels are updated, stop looking for it
					} 
				}

			}		
		}
		else if (numbers.length() < 4 && numbers.length() > 1) // while we still don't have the final char
		{
			for (int i = 0; i < configu.size(); i++) // reads the table
			{
				if (configu.get(i)[0].contains(numbers) && configu.get(i)[0].equals(numbers)) // if the first column of a specific row has that number (aka
															// direction)
				{ // save the info in LabelText
					labelText.clear(); //clears the vector
					
					updateLabels(configu, i); //update labels and frame
					
					break; //when labels are updated, stop looking for it
				} 
			}
		}
		else if (numbers.length() == 4) // final direction, we got to the final char
		{
			char a = numbers.charAt(3); // gets last value in the numbers vector
			int index = Character.getNumericValue(a); // converts the char into int
			
				if(labelText.get(index - 1).contains("EMAIL MARIA C.")) { //send email to maria carvalho
					try {
						SendEmail.send("marialcatiri@gmail.com", messageField.getText());
					} catch (Exception e) {
						e.printStackTrace();
					}
					emailSentMessageFrame();
				}
				else if(labelText.get(index - 1).contains("EMAIL MARIA J.")) {
					try {
						SendEmail.send("m4ria.loureiro@gmail.com", messageField.getText());
					} catch (Exception e) {
						e.printStackTrace();
					}
					emailSentMessageFrame();
				}
				else if(labelText.get(index - 1).contains("SPEAK")) {

					TextToSpeech.speak(messageField.getText()); //voice reads message
				}
				else if(labelText.get(index - 1).contains("SAVE ON PC")) {
					//every time the user chooses save, the message on the MessageField is saved on a new txt file
					BufferedWriter FileWriter=null;
					String txtfilename = new String();
					
					try {
						  txtfilename = ("OcularMessage" + Integer.toString(formatnumber) + ".txt"); //the filename depends on
					 	  File fileWrite = new File("src./",txtfilename); //create file in directory
					      FileWriter = new BufferedWriter(new FileWriter(fileWrite));
					      FileWriter.write(messageField.getText()); //write message in file
					      FileWriter.close();
					    } 
					  catch (IOException e) {
					      System.out.println("An error occurred.");
					      e.printStackTrace();
					    }
					  savedMessageFrame(); 
					  formatnumber++;
				}
				else if(!labelText.get(index - 1).contains("EXIT")) 
					messageField.setText(messageField.getText()+labelText.get(index-1)); //add new char to the message
				
				labelText.clear(); //clear previous labels
				
				updateLabels(configu, 0); //update to initial labels and frame
					
				numbers=""; // cleans numbers for next char
		}
	}

	//build appropriate frames
	private void createFrameLayout(int x, int y, int R, JTextPane text) {
		text.setEditable(false);
		text.setFont(new Font("Application", Font.PLAIN, 18));
		text.setBounds(x, y, 120, 70); //different from down
		frame.getContentPane().add(text);
		text.setBackground(new java.awt.Color(210,210,210));
		StyledDocument doc2 = text.getStyledDocument();
		SimpleAttributeSet center2 = new SimpleAttributeSet();
		StyleConstants.setAlignment(center2, StyleConstants.ALIGN_CENTER);
		doc2.setParagraphAttributes(0, doc2.getLength(), center2, false);
		text.setText(labelText.get(R)); //different from down
	}
	
	//initialize interface
	private void initialize(ArrayList<String[]> configu) {

		String numbers = new String(); // declare private attribute
		this.numbers = numbers;

		Vector<String> LabelText = new Vector<String>(); // vector for the messages displayed, in each box
		for (int i = 1; i < 5; i++)
			LabelText.add(configu.get(0)[i]); // initially, the label presents the default message, with NEW, SAVE,
												// DELETE and EXIT options

		this.labelText = LabelText;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenHeight = (int)Math.round(screenSize.getHeight()*1); //(int)Math.round(screenSize.getHeight()*1.25);
		int screenWidth = (int)Math.round(screenSize.getWidth()*0.5); //(int)Math.round(screenSize.getWidth()/2);
		
		//create frame
		frame = new JFrame();
		frame.setBounds(0,0,screenHeight, screenWidth);//(0,0, 762, 756);
		frame.getContentPane().setBackground(new java.awt.Color(52,58,65));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Ocular Control");
		
		//create central arrow
		JLabel upArrow = new JLabel("");
		upArrow.setBounds(220,230,250,250);//(230,400,250,250);
		ImageIcon arrow = new ImageIcon("src./arrow250.png");
		upArrow.setIcon(arrow);
		frame.getContentPane().add(upArrow);
		
		//create logo
		JLabel imLogo = new JLabel("");
		imLogo.setBounds(260,20,176,50);//(265, 100, 176, 50);
		ImageIcon logo = new ImageIcon("src./Logo.png");
		imLogo.setIcon(logo);
		frame.getContentPane().add(imLogo);
		
		//create left frame
		JTextPane leftText = new JTextPane();
		createFrameLayout(80, 315, 0, leftText);//(80,485,0,leftText);
		this.leftText = leftText;
		
		//create up frame
		JTextPane upText = new JTextPane();
		createFrameLayout(287,150,1,upText);//(290,300,1,upText);
		this.upText = upText;
		
		//create right frame
		JTextPane rightText = new JTextPane();
		createFrameLayout(490, 315, 2, rightText);//(520,485,2,rightText);
		this.rightText = rightText;
		
		//create down frame
		JTextPane downText = new JTextPane();
		createFrameLayout(287, 495, 3, downText); //(295,670,3,downText);
		this.downText = downText;
		
		//create message frame
		messageField = new JTextField();
		messageField.setEditable(false);
		messageField.setBounds(150, 90, 390, 40);//(150,175,416,51);
		frame.getContentPane().add(messageField);
		messageField.setColumns(10);
		messageField.setFont(new Font("Application", Font.BOLD, 20));
		messageField.setBackground(new java.awt.Color(50,50,50));
		messageField.setBorder(null);
		messageField.setForeground(Color.white);
		messageField.setText("");
	}
	
	//create initial interface
	public Interface(ArrayList<String[]> configu) {
		initialize(configu);
	}
}
