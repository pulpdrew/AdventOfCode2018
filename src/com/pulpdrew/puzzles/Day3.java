package com.pulpdrew.puzzles;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day3 {

	private final static int HEIGHT = 2000;
	private static final int WIDTH = 2000;
	
	private static class Claim {
		
		public int id;
		public int x, y;
		public int width, height;
		
		public Claim(String claimString) {
			
			claimString = claimString.replace("#", "");
			claimString = claimString.replace("@ ", "");
			claimString = claimString.replace(",", " ");
			claimString = claimString.replace(":", "");
			claimString = claimString.replace("x", " ");
			String[] nums = claimString.split(" ");
			
			id = Integer.parseInt(nums[0]);
			x = Integer.parseInt(nums[1]);
			y = Integer.parseInt(nums[2]);
			width = Integer.parseInt(nums[3]);
			height = Integer.parseInt(nums[4]);
		}
		
		public boolean overlaps(Claim other) {
			if (other.x > x + width || other.x + other.width < x) return false;
			if (other.y > y + height || other.y + other.height < y) return false;
			return true;
		}
	}
	
	private static void addClaim(int[][] fabric, Claim claim) {
		
		for (int i = claim.x; i < claim.x + claim.width; i++) {
			for (int j = claim.y; j < claim.y + claim.height; j++) {
				fabric[i][j]++;
			}
		}
		
	}
	
	private static int countOverlapping(List<Claim> claims) {
		int[][] fabric = new int[WIDTH][HEIGHT];
		
		for (Claim claim : claims) {
			addClaim(fabric, claim);
		}
		
		int count = 0;
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				if (fabric[i][j] >= 2) count++;
			}
		}
		
		return count;
		
	}
	
	
	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner s = new Scanner(new File("input.txt"));
		List<Claim> claims = new ArrayList<>();
		while (s.hasNext()) {
			Claim claim = new Claim(s.nextLine());
			claims.add(claim);
		}
		s.close();
		
		for (Claim c1 : claims) {
			boolean overlaps = false;
			for (Claim c2 : claims) {
				if (c2.id != c1.id && c1.overlaps(c2)) {
					overlaps = true;
					break;
				}
			}
			if (!overlaps) System.out.println(c1.id);
		}

	}

}
