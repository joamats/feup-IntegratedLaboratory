import java.util.ArrayList;

public class Signal {

	public enum Direction {
		UP, DOWN, RIGHT, LEFT, NONE
	}

	private ArrayList<Double> data;
	private Direction direction;
	private String filename;
	private final double limit = 3.5;

	public Signal(String filename) {
		this.filename = filename;
		this.data = Reader.getEOG(filename);
		classifySignal();
	}

	private void classifySignal() {
		char H = 'H';
		char V ='V';
		if (filename.indexOf(H) != -1) { // H exists in filename, eog corresponds to horizontal position
			if (this.verifyCondition() == "above")
				this.direction = Signal.Direction.RIGHT; //when eog has a value above limit
			else if (this.verifyCondition() == "below")
				this.direction = Signal.Direction.LEFT; //when eog has a value below -limit
		}
		else if (filename.indexOf(V) != -1) { // V exists in filename, eog corresponds to vertical position
			if (this.verifyCondition() == "above")
				this.direction = Signal.Direction.UP; //when eog has a value above limit
			else if (this.verifyCondition() == "below")
				this.direction = Signal.Direction.DOWN; //when eog has a value below -limit
		}
	}
	
	public String getDirectionNumber() {
		if(this.direction==Signal.Direction.LEFT)
			return "1";
		else if(this.direction==Signal.Direction.UP)
			return "2";
		else if(this.direction==Signal.Direction.RIGHT)
			return "3";
		else //down direction
			return "4";
	}

	private String verifyCondition() {
		// find condition that need to be verified
		for (Double iterator : this.data) {
			if (iterator > limit)
				return "above"; //returns above if eog has a value above limit
			else if (iterator < -limit)
				return "below"; //returns below if eog has a value below -limit
		}
		return "error";
	}

}
