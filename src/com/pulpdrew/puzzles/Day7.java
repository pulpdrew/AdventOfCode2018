import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeMap;

public class Day7 {
	
	/**
	 * Places any sources from G into sources, ensuring sources doesn't contain duplicates
	 */
	private static void detectSources(Map<Character, List<Character>> G, PriorityQueue<Character> sources) {
		
		for (char c : G.keySet()) {
			if (sources.contains(c)) continue;
			boolean isSource = true;
			for (Map.Entry<Character, List<Character>> vertex : G.entrySet()) {
				for (char destination : vertex.getValue()) {
					if (c == destination) isSource = false;
				}
			}
			if (isSource) sources.add(c);
		}
		
	}
	
	/**
	 * Returns a topological sorting (with ties broken alphabetically by label) of G.
	 */
	private static String topSort(Map<Character, List<Character>> G) {
		
		StringBuilder order = new StringBuilder(G.size());
		PriorityQueue<Character> sources = new PriorityQueue<>();
		
		while (G.size() > 0) {
			detectSources(G, sources);
			char source = sources.poll();
			order.append(source);
			G.remove(source);
		}
		
		return order.toString();
	}
	
	private static void incrementAndRemoveDone(Map<Character, Integer> inProgress, Map<Character, List<Character>> G, int base) {
		Iterator<Map.Entry<Character, Integer>> it = inProgress.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Character, Integer> next = it.next();
			next.setValue(next.getValue() + 1);
			if (next.getValue() == base + (next.getKey() - 'A' + 1)) {
				it.remove();
				G.remove(next.getKey());
			}
		}
	}
	
	private static int timeToAssemble(Map<Character, List<Character>> G) {
		
		int seconds = 0, workers = 5, base = 60;
		PriorityQueue<Character> sourcesWaiting = new PriorityQueue<>();
		Map<Character, Integer> inProgress = new HashMap<>();
		
		// While there is still more to assemble
		while (G.size() > 0 || sourcesWaiting.size() > 0 || inProgress.size() > 0) {
			
			incrementAndRemoveDone(inProgress, G, base);
			detectSources(G, sourcesWaiting);
			
			while (inProgress.size() < workers && sourcesWaiting.size() > 0) {
				char source = sourcesWaiting.poll();
				if (!inProgress.containsKey(source)) {
					inProgress.put(source, 0);
				}
			}
			sourcesWaiting.clear();
			
			System.out.println(seconds + ": " + inProgress);
			seconds++;
			
		}
		
		return seconds - 1;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		
		Map<Character, List<Character>> graph = new TreeMap<>();
		
		Scanner s = new Scanner(new File("input.txt"));
		while (s.hasNext()) {
			String[] line = s.nextLine().split(" ");
			char source = line[1].charAt(0);
			char dest = line[7].charAt(0);
			
			if (!graph.containsKey(source)) {
				graph.put(source, new ArrayList<Character>());
			}
			if (!graph.containsKey(dest)) {
				graph.put(dest, new ArrayList<Character>());
			}
			graph.get(source).add(dest);		
		}
		s.close();
		
		// System.out.println(topSort(graph));
		System.out.println(timeToAssemble(graph));

	}

}
