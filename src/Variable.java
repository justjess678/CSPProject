import java.util.ArrayList;

public class Variable {
	// domaine courant - all possible colors
	// domaine de ref - all remaining colors
	static ArrayList<Integer> domRef;
	ArrayList<Integer> domCourant;
	String name;

	/**
	 * Constructor with no pre-defined domains, number of colors must be
	 * specified in this case
	 * 
	 * @param nbColors
	 */
	public Variable(int nbColors, String name, ArrayList<Integer> domRef) {
		Variable.domRef = domRef;
		// all colours are available at the
		// beginning
		this.domCourant = Variable.domRef;
		this.name = name;
	}

	@Override
	public String toString() {
		return "--------------------\n     " + name
				+ "\n--------------------\n\nCurrent Domain:" + domCourant+"\nReference Domain:" + Variable.domRef
				+ "\n--------------------\n";
	}

}
