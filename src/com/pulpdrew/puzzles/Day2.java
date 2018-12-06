package com.pulpdrew.puzzles;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day2 {

	public static int[] getCounts(String id) {
		int[] counts = new int[26];
		
		for (char c : id.toCharArray()) {
			counts[c - 'a']++;
		}
		
		return counts;
	}
	
	public static boolean containsDouble(String id) {
		int[] counts = getCounts(id);
		boolean containsDouble = false;
		for (int i : counts) {
			if (i == 2) containsDouble = true;
		}
		return containsDouble; 
	}
	
	public static boolean containsTriple(String id) {
		int[] counts = getCounts(id);
		boolean containsDouble = false;
		for (int i : counts) {
			if (i == 3) containsDouble = true;
		}
		return containsDouble; 
	}
	
	public static int checkSum(List<String> ids) {
		int triples = 0, doubles = 0;
		for (String id : ids) {
			if (containsDouble(id)) doubles++;
			if (containsTriple(id)) triples++;
		}
		return triples * doubles;
	}
	
	public static boolean differsBy1Character(String s1, String s2) {
		int count = 0;
		for (int i = 0; i < s1.length(); i++) {
			if (s1.charAt(i) != s2.charAt(i)) count++;
			if (count > 1) return false;
		}
		return count == 1;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner s = new Scanner(new File("input.txt"));
		List<String> ids = new ArrayList<>();
		while (s.hasNext()) {
			ids.add(s.nextLine());
		}
		s.close();
		
		
		for (String s1 : ids) {
			for (String s2 : ids) {
				if (differsBy1Character(s1, s2)) System.out.println(s1 + "\n" + s2);
			}
		}
		

	}

}
