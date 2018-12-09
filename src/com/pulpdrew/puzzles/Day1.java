package com.pulpdrew.puzzles;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Day1 {
	s
	public static void main(String[] args) throws FileNotFoundException {
	
		
		List<Integer> shifts = new ArrayList<>();
		
		Scanner s = new Scanner(new File("input.txt"));
		while(s.hasNext()) {
			shifts.add(Integer.parseInt(s.nextLine()));
		}
		s.close();
		
		Set<Integer> frequencies = new HashSet<>();
		int result = 0;
		int shiftIndex = 0;
		while (!frequencies.contains(result)) {
			frequencies.add(result);
			result += shifts.get(shiftIndex);
			shiftIndex = (shiftIndex + 1) % shifts.size();
		}

		System.out.println("Result: " + result);
	}

}
