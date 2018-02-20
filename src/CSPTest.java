import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

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
		ArrayList<Integer> domRef = new ArrayList<Integer>();
		for (Iterator<String> k = csts.keySet().iterator(); k.hasNext();) {
			domRef.add(Integer.valueOf(k.next()));
		}
		Variable jean = new Variable(nbColors, "Jean", domRef);
		System.out.println(jean.toString());
	}

}
