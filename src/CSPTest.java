import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CSPTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int nbColors = 10;
		Instance i = new Instance();
		HashMap<String, HashSet<String>> csts = i
				.loadInstance("/home/jess/Documents/SRI/CSP/src/all-instaces/jean.col");
		System.out.println(csts.toString());
		System.out.println(i.toString());
		// initialization
		ArrayList<Integer> domRef = new ArrayList<Integer>();
		ArrayList<Variable> vars = new ArrayList<Variable>();
		// Fill the reference domain

		/*
		 * for (Iterator<String> k = csts.keySet().iterator(); k.hasNext();) {
		 * domRef.add(Integer.valueOf(k.next())); }
		 */

		/*
		 * Scanner sc = new Scanner(System.in); System.out .println(
		 * "--------------------------------\n|Nombre de couleurs souhaitées:|\n--------------------------------\n"
		 * ); int colSouhait = sc.nextInt(); sc.close();
		 */
		int colSouhait = 80;
		for (int k = 1; k <= colSouhait; k++) {
			domRef.add(k);
		}
		System.out.println("Dom Ref = "+domRef.toString());
		// fill vars with all the variables
		for (Iterator<String> k = csts.keySet().iterator(); k.hasNext();) {
			vars.add(new Variable(nbColors, k.next(), csts, domRef));
		}
		int colsUsed = 0;
		int nColor = 0;
		int backtrack=0;
		for (int v = 0; v < vars.size(); v++) {
			vars.get(v).cleanUp();// removes colors that have been used from the
									// current domain
			colsUsed = 0;
			if (Variable.domRef.size() >= vars.get(v).getNeighbours().size()) {
				// just to make things easier
				ArrayList<String> neighbours = vars.get(v).getNeighbours();
				for (int n = 0; n < neighbours.size(); n++) {
					// pick the color for the neighbour
					String nName = neighbours.get(n);// ok
					nColor = Variable.domRef.get(colsUsed);
					// if current state hasn't used this color AND the
					// destination state hasn't used it we can use it
					if (vars.get(v).possibleColor(nColor)
							&& vars.get(v).possibleColor(nColor, nName)) {
						if (vars.get(v).removeColor(nColor)) {
							colsUsed++;

						}
					}
				}
				// backtrackin'
				vars.get(vars.size() - 1).backtrack();
				backtrack++;
			} else {
				throw new java.lang.Error(
						"******There are not enough colours to colour this graph!*****");
			}
			System.out.println(vars.get(v).toString()
					+ "\nNumber of colours used on this variable:" + colsUsed
					+ "\n\nA=" + Variable.assignedColors.toString()+
					"\nBacktracks:"+backtrack);
		}
		int allColors=0;
		for(Point p : Variable.assignedColors){
			if(p.getY()>allColors){
				allColors=(int) p.getY();
			}
		}
		System.out.println("Total colours used in graph:"+allColors);
		// A list de doublons var+couleur affectee, then compare with A to make
		// sure no doubles
	}

}
