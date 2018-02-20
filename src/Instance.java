import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class Instance {
	private int nbVars;
	private int nbConstraints;

	/**
	 * Constructor sets number of variables and constraints to 0
	 */
	public Instance() {
		this.nbVars = 0;
		this.nbConstraints = 0;
	}

	/**
	 * loads values from input filename
	 * 
	 * @param filename
	 * @return
	 */
	public HashMap<String, HashSet<String>> loadInstance(String filename) {
		String ligne;
		String[] s;
		String arg1;
		String arg2;
		HashMap<String, HashSet<String>> csts = new HashMap<String, HashSet<String>>();

		try {
			BufferedReader fic = new BufferedReader(new FileReader(filename));

			while ((ligne = fic.readLine()) != null) {

				s = ligne.split("\\s+");

				if (s[0].equals("e")) {
					arg1 = s[1];
					arg2 = s[2];
					if (!csts.containsKey(arg1)) {
						csts.put(arg1, new HashSet<String>());
					}
					csts.get(arg1).add(arg2);
					if (!csts.containsKey(arg2)) {
						csts.put(arg2, new HashSet<String>());
					}
					csts.get(arg2).add(arg1);
				} else if (s[0].equals("p")) {
					nbVars = Integer.valueOf(s[2]);
					nbConstraints = Integer.valueOf(s[3]);
				}
			}
		} catch (IOException ioe) {
			System.out.println("pb / file " + filename);
		}
		return (csts);
	}

	@Override
	public String toString() {
		return "--------------------\n|    Instance      |\n--------------------\nnbVars="
				+ nbVars
				+ "\nnbConstraints="
				+ nbConstraints
				+ "\n--------------------";
	}

	public int getNbVars() {
		return nbVars;
	}

	public int getNbConstraints() {
		return nbConstraints;
	}

}
