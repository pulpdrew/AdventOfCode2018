package com.pulpdrew.puzzles;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Day6 {

	private static class Point implements Comparable<Point>{
		
		private int x, y;
		
		/* Constructors */
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		/* Manhattan Distance */
		
		public int distance(Point p) {
			return Math.abs(this.x - p.x) + Math.abs(this.y - p.y);
		}
		
		/* Object Methods */
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Point other = (Point) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}
		
		@Override
		public String toString() {
			return "(" + this.x + ", " + this.y + ")";
		}

		
		@Override
		public int compareTo(Point o) {
			if (this.x != o.x) return this.x - o.x;
			return this.y - o.y;
		}
		
	}
	
	/**
	 * Returns a point indicating the maximum x and y coordinates of the points in points. 
	 * Ensures that all points in points are within the rectangle bounded by (0, 0) and getBounds().
	 * 
	 * @param points the points for which bounds should be calculated.
	 * @return a point indicating the maximum x and y coordinates of the points in points
	 */
	private static Point getBounds(List<Point> points) {
		int maxY = points.get(0).y;
		int maxX = points.get(0).x;
		
		for (Point p : points) {
			if (p.x > maxX) maxX = p.x;
			if (p.y > maxY) maxY = p.y;
		}
		
		return new Point(maxX, maxY);
	}
	
	/**
	 * Maps each point in the range (0,0) to (bounds.x, bounds.y) to the point in points which it is closest to.
	 * Ties will be indicated by a mapping to null.
	 * 
	 * @param bounds the size of the rectangle to test points from.
	 * @param points the points to which each point in bounds will be mapped to.
	 * @return the mapping from (0,0) to (bounds.x, bounds.y) to their nearest points in points.
	 * 
	 */
	private static Map<Point, Point> getClosestCoordinates(Point bounds, List<Point> points) {
		
		Map<Point, Point> closestCoordinates = new TreeMap<>();
		
		for (int x = 0; x <= bounds.x; x++) {
			for (int y = 0; y <= bounds.y; y++) {
				
				Point p = new Point(x, y);
				
				int shortestDistance = Integer.MAX_VALUE;
				Point closestPoint = null;
				
				for (Point candidate : points) {
					int candidateDistance = p.distance(candidate);
					if (candidateDistance < shortestDistance) {
						shortestDistance = candidateDistance;
						closestPoint = candidate;
					} 
				}
				
				for (Point candidate : points) {
					int candidateDistance = p.distance(candidate);
					if (candidateDistance == shortestDistance && closestPoint != candidate) {
						closestPoint = null;
						break;
					} 
				}
				
				closestCoordinates.put(p, closestPoint);				
				
			}
		}
		
		return closestCoordinates;
		
	}
	
	/**
	 * Maps each distinct point in valueSet(closestCoordinates) to each of the points in keySet(closestCoordinates)
	 * that map to it.
	 * 
	 * @param closestCoordinates the mapping from (0,0) to (bounds.x, bounds.y) to their nearest points in points.
	 * @return the inverse of the mapping given by closestCoordinates.
	 */
	private static Map<Point, List<Point>> invert(Map<Point, Point> closestCoordinates) {
		
		Map<Point, List<Point>> areas = new TreeMap<>();
		
		for (Map.Entry<Point, Point> entry : closestCoordinates.entrySet()) {
			if (entry.getValue() == null) continue;
			if (!areas.containsKey(entry.getValue())) {
				areas.put(entry.getValue(), new ArrayList<Point>());	
			}
			areas.get(entry.getValue()).add(entry.getKey());
		}
		
		return areas;
		
	}
	
	/**
	 * Removes any key from areas that contains a point on the boundary indicated by bounds.
	 * 
	 * @param bounds a point indicating the boundary that contains all points in keySet(areas).
	 * @param areas a mapping of coordinates to every point that is closest to that coordinate
	 */
	private static void removeInfiniteAreas(Point bounds, Map<Point, List<Point>> areas) {
		
		Iterator<Map.Entry<Point, List<Point>>> it = areas.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Point, List<Point>> entry = it.next();
			
			for (Point p : entry.getValue()) {
				if (p.x == 0 || p.y == 0 || p.x == bounds.x || p.y == bounds.y) {
					it.remove();
					break;
				}
			}
			
		}
		
	}
	
	/**
	 * Finds and returns the size of the largest list in valueSet(areas).
	 */
	private static int findLargestsArea(Map<Point, List<Point>> areas) {
		int largest = 0;
		for (Map.Entry<Point, List<Point>> entry : areas.entrySet()) {
			int area = entry.getValue().size();
			if (area > largest) largest = area;
		}
		return largest;
	}
	
	/**
	 * Prints a visualization of the points in closestCoordinates.
	 */
	private static void printPoints(Point bounds, Map<Point, Point> closestCoordinates) {
		
		for (int y = 0; y <= bounds.y; y++) {
			for (int x = 0; x <= bounds.x; x++) {
				
				Point p = new Point(x, y);
				Point closestCoordinate = closestCoordinates.get(p);
				
				if (closestCoordinate == null) {
					System.out.print("......  ");
				} else if (closestCoordinate.equals(p)) {
					System.out.print("[" + p.x + ", " + p.y + "]  ");
				} else {
					System.out.print(closestCoordinate + "  ");					
				}
	
			}
			System.out.println();
		}
		
	}
	
	/**
	 * Returns the sum of the distances between p and each of the points in points.
	 */
	private static int sumDistances(List<Point> points, Point p) {
		
		int sum = 0;
		for (Point point : points) {
			sum += point.distance(p);
		}
		return sum;
		
	}
	
	/**
	 * Returns the number of points within the given bounds that have a sum of distances to all
	 * the points in points of less that 10000.
	 */
	private static int getValidPoints(List<Point> points, Point lowerBound, Point upperBound) {
		
		int validPoints = 0;
		
		for (int x = lowerBound.x; x <= upperBound.x; x++) {
			for (int y = lowerBound.y; y <= upperBound.y; y++) {
				Point p = new Point(x, y);
				if (sumDistances(points, p) < 10000) validPoints++;
			}
		}
		
		return validPoints;
		
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		
		List<Point> points = new ArrayList<>();
		
		Scanner s = new Scanner(new File("input.txt"));
		while(s.hasNextLine()) {
			String[] line = s.nextLine().split(", ");
			int x = Integer.parseInt(line[0]);
			int y = Integer.parseInt(line[1]);
			points.add(new Point(x, y));
		}
		s.close();
		
		// Part 1
		
		Point bounds = getBounds(points);
		Map<Point, Point> closestCoordinates = getClosestCoordinates(bounds, points);
		
		// printPoints(bounds, closestCoordinates);
		
		Map<Point, List<Point>> areas = invert(closestCoordinates);
		removeInfiniteAreas(bounds, areas);
		System.out.println(findLargestsArea(areas));
		
		// Part 2
		
		System.out.println(getValidPoints(points, new Point(-500, -500), new Point(bounds.x + 500, bounds.y + 500)));
		
	}
	
	
}
