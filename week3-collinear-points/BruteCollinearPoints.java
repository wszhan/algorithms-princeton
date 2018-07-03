/******************************************************************************
 *  Compilation:  javac BruteCollinearPoints.java
 *  Execution:    java BruteCollinearPoints
 *  Dependencies: Point.java LineSegment.java ArrayList.java Arrays.java
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 * 
 * @author Weisi Zhan
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    /*
     * Finds all line segments containing 4 points
     */
    private LineSegment[] segments;
    
    /*
     * Constructor
     * 
     * @param points a list of Point object
     */
    public BruteCollinearPoints(Point[] points) {
        // null input
        if (points == null) {
            throw new NullPointerException("Null array");
        }
        
        // Make an array copy
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        
        // Sort the array
        Arrays.sort(pointsCopy);
        
        // chech input validity
        validInput(pointsCopy);
        
        // Parse the array and add the segment to the array
        // ArrayList<LineSegment> foundSegments = new ArrayList<>();
        segments = findCollinear(pointsCopy);
    }

    /*
     * Find collinear line segments and store them in 
     * private field ls.
     * 
     * @param a sorted points without duplicates
     * @return none
     */
    private LineSegment[] findCollinear(Point[] a) {
        // Local variables
        ArrayList<LineSegment> foundSegments = new ArrayList<>();
        int pointsLength = a.length;
        LineSegment ls;
        
        for (int i = 0; i < pointsLength - 3; i++) {
            for (int j = i + 1; j < pointsLength - 2; j++) {
                for (int k = j + 1; k < pointsLength - 1; k++) {
                    
                    // While the first 3 points are not collinear, any
                    // further progress would be futile as to find
                    // collinear points. Thus, skip and start the next loop
                    if (!(collinear(a[i], a[j], a[k]))) {
                        continue;
                    }
                    for (int m = k + 1; m < pointsLength; m++) {                        
                        // Debug: print out points
                        // System.out.println("Current Points:");
                        // System.out.println(a[i]);
                        // System.out.println(a[j]);
                        // System.out.println(a[k]);
                        // System.out.println(a[l]);
                        
                        // If four points are collinear
                        if (collinear(a[i], a[j], a[k], a[m])) {
                            ls = new LineSegment(a[i], a[m]);
                            if (!duplicateSegment(ls, foundSegments)) {
                                // System.out.println("Collinear!");
                                foundSegments.add(ls);
                            }
                        }
                    }
                }
            }
        }
        return foundSegments.toArray(new LineSegment[foundSegments.size()]);
    }
    
    /*
     * The number of line segments
     */
    public int numberOfSegments() {
        return segments.length;
    }
    
    /*
     * The line segments
     */
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, numberOfSegments());
    }
    
    /*
     * Check whether the inputs to the constructor
     * is valid.
     * 
     * @params p sorted Point[] input.
     */
    private boolean validInput(Point[] p) {
        // edge case
        if (p.length == 1 && p[0] == null) {
            throw new IllegalArgumentException("Null entries");
        }
        
        for (int i = 0; i < p.length; i++) {
            // null entries
            if (p[i] == null) {
                throw new IllegalArgumentException("Null entries");
            }
            // duplicates
            if (i < p.length - 1 && p[i].compareTo(p[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicated entries");
            }
        }
        return true;
    }
    
    /*
     * Check whether fours points are collinear by
     * comparing the slopes of the last 3 points with
     * the first point.
     * Returns False while ab and ac are not collinear,
     * in which case the test of ad is redundant.
     * 
     * @params four distinct poinits
     * @return boolean
     */
    private boolean collinear(Point a, Point b, Point c) {
        // return a.slopeTo(b) == a.slopeTo(c);
        return a.slopeOrder().compare(b, c) == 0;
    }
    
    private boolean collinear(Point a, Point b, Point c, Point d) {
        return a.slopeOrder().compare(b, c) == 0 && a.slopeOrder().compare(c, d) == 0;
    }

   /***************************************************************************
    *  Check for Duplicates
    ***************************************************************************/
    
    /*
     * Check whether a line segment is already in an ArrayList or not.
     * 
     * @param ls the line segment to be checked
     * @param seg the existing segment with which the ls is checked with
     * @return boolean true if duplicate
     * @return boolean false if not duplicate
     */
    private boolean duplicateSegment(LineSegment ls, Iterable<LineSegment> seg) {
        for (LineSegment m : seg) {
            // System.out.println("ls toString: " + ls.toString());
            // System.out.println("l toString: " + l.toString());
            
            // Check whether the line segment is already in the ArrayList
            // avoid "==" because we are comparing values rather than
            // objects.
            if (ls.toString().equals(m.toString())) {
                // System.out.println("These are duplicates");
                return true;
            }
        }
        return false;
    }
    
    
   /***************************************************************************
    *  Unit Test
    ***************************************************************************/
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}