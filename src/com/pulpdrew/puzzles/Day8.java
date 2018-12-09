import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day8 {

	private static class Node {
		
		private List<Integer> metadata;
		private List<Node> children;
		
		public Node() {
			this.metadata = new ArrayList<>();
			this.children = new ArrayList<>();
		}
		
		public int sumOfMetadata() {
			
			int sum = 0;
			for (Node n : children) {
				sum += n.sumOfMetadata();
			}
			for (int i : metadata) {
				sum += i;
			}
			return sum;
			
		}
		
		private int valueOfNode() {
			
			int value;
			
			if (children.size() == 0) {
				value = this.sumOfMetadata();
			} else {
				
				value = 0;
				
				for (int i : this.metadata) {
					int index = i - 1;
					if (index >= 0 && index < this.children.size()) {
						value += this.children.get(index).valueOfNode();
					}
				}
				
			}
			
			
			return value;
			
		}
		
		private void addChild(Node child) {
			this.children.add(child);
		}
		
		private void addMetadata(int metadata) {
			this.metadata.add(metadata);
		}
	}
	
	private static Node readTree(List<Integer> input) {
		
		Node node = new Node();
		
		int childNodes = input.remove(0);
		int metaData = input.remove(0);
		
		for (int i = 0; i < childNodes; i++) {
			node.addChild(readTree(input));
		}
		
		for (int i = 0; i < metaData; i++) {
			node.addMetadata(input.remove(0));
		}
		
		return node;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner s = new Scanner(new File("input.txt"));
		List<Integer> input = new ArrayList<>();
		while (s.hasNextInt()) {
			input.add(s.nextInt());
		}
		s.close();
		
		Node tree = readTree(input);
		System.out.println(tree.sumOfMetadata());
		System.out.println(tree.valueOfNode());
		
	}

}
