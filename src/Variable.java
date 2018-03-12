import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class Variable {
	// domaine courant - all possible colors
	// domaine de ref - all remaining colors
	static ArrayList<Integer> domRef;
	static ArrayList<Point> assignedColors;
	private ArrayList<Integer> domCourant = new ArrayList<Integer>(); // vars to
																		// choose
																		// from
	private String name;
	private ArrayList<String> neighbours;
	private int constraint;

	/**
	 * Constructor with a pre-defined reference domain, number of colors must be
	 * specified in this case
	 * 
	 * @param nbColors
	 */
	public Variable(int nbColors, String name, ArrayList<Integer> domRef) {
		if (Variable.domRef != domRef)
			Variable.domRef = domRef;
		Collections.sort(Variable.domRef);
		Variable.assignedColors = new ArrayList<Point>();
		// all colours are available at the
		// beginning
		for (int i = 0; i < Variable.domRef.size(); i++) {
			if (!Variable.assignedColors.contains(new Point(Integer
					.valueOf(name), Variable.domRef.get(i)))) {
				this.domCourant.add(Variable.domRef.get(i));
			}
		}

		this.name = name;
	}

	/**
	 * Constructor with no pre-defined domains, number of colors must be
	 * specified in this case
	 * 
	 * @param nbColors
	 */
	public Variable(int nbColors, String name,
			HashMap<String, HashSet<String>> csts, ArrayList<Integer> domRef) {
		if (Variable.domRef != domRef)
			Variable.domRef = domRef;
		Variable.assignedColors = new ArrayList<Point>();
		Collections.sort(Variable.domRef);
		// all colours are available at the
		// beginning
		for (int i = 0; i < Variable.domRef.size(); i++) {
			if (!Variable.assignedColors.contains(new Point(Integer
					.valueOf(name), Variable.domRef.get(i)))) {
				this.domCourant.add(Variable.domRef.get(i));
			}
		}
		this.name = name;
		this.neighbours = new ArrayList<String>(csts.get(name));
		this.constraint = this.neighbours.size();
	}

	/**
	 * removes color from the current domain and adds the current point and
	 * color to the list of assigned colors
	 * 
	 * @param color
	 */
	public boolean removeColor(int color) {
		if (this.domCourant.contains(color)) {
			this.domCourant.remove(new Integer(color));
			Variable.assignedColors.add(new Point(Integer.valueOf(this.name),
					color));
			// all of this vars neighbours are also affected
			for (String s : this.getNeighbours()) {
				Variable.assignedColors
						.add(new Point(Integer.valueOf(s), color));
			}
			return true;
		} else {
			return false;
		}
	}

	public void cleanUp() {
		// Clean up colors that have been used
		for (int i = 0; i < Variable.domRef.size(); i++) {
			if (Variable.assignedColors.contains(new Point(Integer
					.valueOf(name), Variable.domRef.get(i)))) {
				this.domCourant.remove(new Integer(Variable.domRef.get(i)));
			}
		}
		// order remaining colours: the one with the most neighbours = most
		// constraints is at the beginning

	}

	/**
	 * Sorts 
	 * @param arr
	 * @return
	 */
	public static ArrayList<Integer> quickSort(ArrayList<Integer> arr) {
		if (arr.isEmpty())
			return arr;
		else {
			int pivot = arr.get(0);
			ArrayList<Integer> less = new ArrayList<Integer>();
			ArrayList<Integer> pivotList = new ArrayList<Integer>();
			ArrayList<Integer> more = new ArrayList<Integer>();

			// Partition
			for (int i : arr) {
				if (i < pivot)
					less.add(i);
				else if (i > pivot)
					more.add(i);
				else
					pivotList.add(i);
			}

			// Recursively sort sublists
			less = quickSort(less);
			more = quickSort(more);

			// Concatenate results
			less.addAll(pivotList);
			less.addAll(more);
			return less;
		}

	}

	public boolean possibleColor(int color) {
		Point p = new Point(Integer.valueOf(this.name), color);
		this.cleanUp();
		if (Variable.assignedColors.contains(p))
			return false;
		else
			return true;
	}

	public boolean possibleColor(int color, String dest) {
		Point p = new Point(Integer.valueOf(dest), color);
		this.cleanUp();
		if (Variable.assignedColors.contains(p))
			return false;
		else
			return true;
	}

	public void backtrack() {// J'ai repris ligne par ligne l'algo du cours
		if (!Variable.assignedColors.isEmpty()) {
			// D'[1]=copie(D[1])
			ArrayList<Integer> domainePrime = new ArrayList<Integer>();
			domainePrime.add((int) Variable.assignedColors.get(0).getX());
			// a={}
			ArrayList<Integer> a = new ArrayList<Integer>();
			// prof=1
			int prof = 1;
			// while 1<=prof<=n do
			int n = Variable.assignedColors.size();
			for (prof = 1; prof < n - 1; prof++) {
				// ok = select_valeur(a,D'[prof],prof)
				System.out.println("N:" + n + " prof:" + prof + "\nD': "
						+ domainePrime.toString());
				if (selectValeur(a, domainePrime, prof)) {
					// prof=prof-1
					prof--;
					System.out.println("prof=prof-1");
					// reset_instances(a,prof)
					System.out.println("Resetting instance...\n");
					resetInstances(a, prof);
				} else {
					// prof=prof+1
					prof++;
					System.out.println("prof=prof+1");
					// if prof<=n then
					if (prof <= n) {
						System.out
								.println("prof<=n\nCopying value from D into D'...");
						// D'[prof]=copie(D[prof])
						if (domainePrime.size() > prof) {
							domainePrime.add(prof,
									(int) Variable.assignedColors.get(prof)
											.getX());
						} else {
							domainePrime.add((int) Variable.assignedColors.get(
									prof).getX());
						}
						System.out.println("Value added\n");
					}
				}
			}
			// if prof=0 then
			if (prof == 0) {
				// CSP incoherent
				throw new java.lang.Error("*****Incoherent CSP!*****");
			} else {
				// a contient les assignations de variables pour une solution
				System.out
						.println("Les assignations de variables pour une solution A:"
								+ a.toString());
			}
		}
	}

	private boolean selectValeur(ArrayList<Integer> a, ArrayList<Integer> dom,
			int prof) {
		System.out.println("Select valeur");
		// while Dom !=0 do
		while (!dom.isEmpty()) {
			// choisir
			int v = dom.get(0);
			// et enlever v€Dom
			dom.remove(new Integer(v));
			System.out.println("enlever v€Dom");
			// a = a U {xprof:v}
			a.add(v);
			if (a.contains(v)) {
				// renvoie vrai
				System.out.println("A contient V");
				return true;
			}
		}
		// renvoie faux
		System.out.println("A ne contient pas V");
		return false;
	}

	private void resetInstances(ArrayList<Integer> a, int prof) {
		for (int k = prof; k < a.size(); k++) {
			if (!a.isEmpty())
				// enlever la valeur asignee a xk dans a
				a.remove(k);
		}
	}

	// VERY Unfinished
	public void forwardChecking() {
		// i=1
		int i = 1;
		// a={}
		ArrayList<Integer> a = new ArrayList<Integer>();
		// pour tout j D'[j]=copie(D[j])
		ArrayList<Integer> domainePrime = new ArrayList<Integer>();
		for (int j = 0; j < Variable.assignedColors.size(); j++) {
			domainePrime.add((int) Variable.assignedColors.get(i).getX());
		}
		// while 1<=i<=n do
		for (i = 1; i < Variable.assignedColors.size(); i++) {
			// ok=select_valeur_ FC(a,i,D')
			// if not(ok) then
			if (!selectValeurFC(a, i, domainePrime)) {
				// i=i-1
				i--;
			} else {
				// i=i+1
				i++;
			}
		}
		// if i=0 then
		if (i == 0) {
			// CSP incoherent
			throw new java.lang.Error("*****Incoherent CSP!*****");
		} else {
			// a contient les assignations de variables pour une solution
			System.out.println(a.toString());
		}
	}

	private boolean selectValeurFC(ArrayList<Integer> a, int i,
			ArrayList<Integer> domainePrime) {
		// sauvegarde du contexte [i..n]
		int svgde = Math.abs(i - domainePrime.size());
		// while D'[i] non vide do
		while (domainePrime.get(i) != null) {
			// choisir et enlever v€D'[i]
			// new=aU{xi:v}
			// saved = {D'[i+1],... D'[n]}
			// Pb=False
			// for all k, i<k<=n do
			for (int k = i; k >= i && k <= domainePrime.size(); k++) {
				// for all b € D'[k] do
				/*
				 * for(int b: domainePrime.get(k)){ //if new U {xk : b} n'est
				 * pas coheerent then //enlever b de D'[k] }
				 */
				// if D'[k]=0 then
				/*
				 * if(domainePrime.get(k)==null){ //D'[i+1],... D'[n]}=saved
				 * //Pb=Trues } //if not(Pb) then if(!Pb){ //renvoie True return
				 * true; }
				 */
			}
		}
		// restauration du contexte [i..n]
		// renvoie false
		return false;
	}

	@Override
	public String toString() {
		return "--------------------\n     Var : "
				+ name
				+ "\n--------------------\n\n  Current Domain:"
				+ domCourant
				+ "\nReference Domain:"
				+ Variable.domRef
				+ "\n--------------------\n"
				+ "-------------------------------\nThis variables' neighbours are:\n-------------------------------\n"
				+ this.neighbours.toString()
				+ "\n-------------------------------\n";
	}

	public static ArrayList<Integer> getDomRef() {
		return domRef;
	}

	public ArrayList<Integer> getDomCourant() {
		return domCourant;
	}

	public String getName() {
		return name;
	}

	public int getNameInt() {
		return Integer.valueOf(name);
	}

	public ArrayList<String> getNeighbours() {
		return neighbours;
	}

	public int getConstraint() {
		return constraint;
	}

	public boolean isName(String name) {
		if (this.name.equals(name))
			return true;
		else
			return false;
	}

	public ArrayList<Integer> getNeighboursInt() {
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		for (String s : this.neighbours) {
			tmp.add(Integer.valueOf(s));
		}
		return tmp;
	}

}
