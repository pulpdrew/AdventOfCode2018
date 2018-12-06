package com.pulpdrew.puzzles;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

public class Day4 {

	private static class DateTime implements Comparable<DateTime> {
		
		private int year, month, day, hour, minute;
		
		public DateTime(String dateString) {
			
			// [1518-11-01 00:00]
			dateString = dateString.replace("[", "");
			dateString = dateString.replace("]", "");
			dateString = dateString.replace("-", " ");
			dateString = dateString.replace(":", " ");
			
			// 1518 11 01 00 00
			String[] datePieces = dateString.split(" ");
			this.year = Integer.parseInt(datePieces[0]);
			this.month = Integer.parseInt(datePieces[1]);
			this.day = Integer.parseInt(datePieces[2]);
			this.hour = Integer.parseInt(datePieces[3]);
			this.minute = Integer.parseInt(datePieces[4]);
			
		}

		@Override
		public int compareTo(DateTime other) {
			if (this.year != other.year) return this.year - other.year;
			if (this.month != other.month) return this.month - other.month;
			if (this.day != other.day) return this.day - other.day;
			if (this.hour != other.hour) return this.hour - other.hour;
			return this.minute - other.minute;
		}
		
	}
	
	private static int sum(int[] minutes) {
		int sum = 0;
		
		for (int i : minutes) {
			sum += i;
		}
		
		return sum;
	}
	
	private static int indexOfMax(int[] minutes) {
		int indexOfMax = 0;
		
		for (int i = 0; i < minutes.length; i++) {
			if (minutes[i] > minutes[indexOfMax]) indexOfMax = i;
		}
		
		return indexOfMax;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		
		// Read and sort the input
		Scanner s = new Scanner(new File("input.txt"));
		Map<DateTime, String> lines = new TreeMap<>();
		while (s.hasNext()) {
			String line = s.nextLine();
			String dateString = line.substring(0, line.indexOf("]") + 1);
			DateTime d = new DateTime(dateString);
			lines.put(d, line);
		}
		s.close();
		
		// Assign each event to the correct guard
		Map<Integer, int[]> guards = new HashMap<>();
		int currentGuardID = 0;
		int[] currentGuardMinutes = null;
		int lastFellAsleep = 0;
		
		for (Map.Entry<DateTime, String> entry : lines.entrySet()) {
			
			DateTime time = entry.getKey();
			String line = entry.getValue();
			
			if (line.contains("#")) {
				currentGuardID = Integer.parseInt(line.substring(line.indexOf("#") + 1, line.indexOf(" ", line.indexOf("#"))));
				if (guards.containsKey(currentGuardID)) {
					currentGuardMinutes = guards.get(currentGuardID);
				} else {
					currentGuardMinutes = new int[60];
					guards.put(currentGuardID, currentGuardMinutes);
				}
			} else if (line.contains("falls asleep")) {
				lastFellAsleep = time.minute;
			} else if (line.contains("wakes up")) {
				for (int i = lastFellAsleep; i < time.minute; i++) {
					currentGuardMinutes[i]++;
				}
			}	
		}
		
		// Find the sleepiest Guard
//		int sleepiest = currentGuardID;
//		for (Map.Entry<Integer, int[]> guard : guards.entrySet()) {
//			
//			int id = guard.getKey();
//			int[] minutes = guard.getValue();
//			
//			if (sum(minutes) > sum(guards.get(sleepiest))) {
//				sleepiest = id;
//			}			
//			
//		}
		
		int maxID = currentGuardID, minuteOfMax = 0, maxTimesAsleep = 0;
		for (Map.Entry<Integer, int[]> guard : guards.entrySet()) {
			
			int id = guard.getKey();
			int[] minutes = guard.getValue();
			
			int sleepiestMinute = indexOfMax(minutes);
			int timesAsleep = minutes[sleepiestMinute];
			if (timesAsleep > maxTimesAsleep) {
				maxTimesAsleep = timesAsleep;
				minuteOfMax = sleepiestMinute;
				maxID = id;
			}
			
		}
		
		System.out.println("Guard #" + maxID + " slept " + maxTimesAsleep + " times at minute " + minuteOfMax);
		
	}

}
