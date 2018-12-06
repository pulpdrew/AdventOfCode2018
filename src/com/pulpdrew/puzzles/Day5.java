package com.pulpdrew.puzzles;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day5 {

	private static String alphabet = "abcdefghijklmnopqrstuvwxyz"; 
	
	private static void eliminatePairs(StringBuilder polymer) {
		
		for (int i = 0; i < polymer.length() - 1; i++) {
			char c1 = polymer.charAt(i), c2 = polymer.charAt(i + 1);
			if (c1 != c2 && Character.toUpperCase(c1) == Character.toUpperCase(c2)) {
				polymer.deleteCharAt(i);
				polymer.deleteCharAt(i);
				i--;
			}
		}
		
	}
	
	private static int smallestPossible(StringBuilder polymer) {
		
		int min = polymer.length();
		
		for (char c : alphabet.toCharArray()) {
			String copy = polymer.toString();
			copy = copy.replace("" + c, "");
			copy = copy.replace("" + Character.toUpperCase(c), "");
			
			StringBuilder minimized = new StringBuilder(copy);
			
			int lastLength = minimized.length();
			while (true) {
				eliminatePairs(minimized);
				if (minimized.length() == lastLength) break;
				lastLength = minimized.length();
			}
			
			if (minimized.length() < min) min = minimized.length();
		}
		
		return min;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner s = new Scanner(new File("input.txt"));
		StringBuilder polymer = new StringBuilder(s.nextLine());
		s.close();
		
//		int lastLength = polymer.length();
//		while (true) {
//			eliminatePairs(polymer);
//			if (polymer.length() == lastLength) break;
//			lastLength = polymer.length();
//		}
//		
//		System.out.println(polymer.toString());
//		System.out.println(polymer.length());
		
		System.out.println(smallestPossible(polymer));
		
	}
	
}
