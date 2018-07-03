/******************************************************************************
 *  Compilation:  javac FastCollinearPoints.java
 *  Execution:    java FastCollinearPoints
 *  Dependencies: Point.java LineSegment.java
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

public class FastCollinearPoints {
    /*
     * finds all line segments containing 4 or more points
     */
    private ArrayList<LineSegment> segments = new ArrayList<>();
    
    /*
     * Constructor
     * 
     * @param points an array of Point object
     */
    public FastCollinearPoints(Point[] points) {
        // null input
        if (points == null) {
            throw new NullPointerException("Null array");
        }
        
        // check for duplicates and null entries
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);
        validInput(pointsCopy);
        
        // Loop through the points:
        
        for (int i = 0; i < pointsCopy.length-3; i++) {
            // First sort: sort points
            Arrays.sort(pointsCopy);
            Point currentPoint = pointsCopy[i];
            
            // For each point, sort the Point array with respect to the slopes
            // they make with that point.
            Arrays.sort(pointsCopy, currentPoint.slopeOrder());
            
            // no need to check the first point
            // and there should be no duplicate
            int start = 0;
            while (start < pointsCopy.length) {
            // Check whether the adjacent points have the same slope.
            // If yes, they are collinear.
                int end = start;
                // While slope is the same, keep going.
                while (end < pointsCopy.length && 
                       currentPoint.slopeOrder().compare(pointsCopy[start],
                                                         pointsCopy[end]) == 0) {
                    end++;
                }
                // There are 4 or more elements included.
                // Remember here pointsCopy[start] has the same slope with
                // pointsCopy[end-1] rather than pointsCopy[end]
                if (end - start >= 3 && currentPoint.compareTo(pointsCopy[start]) < 0) {
                    segments.add(new LineSegment(currentPoint, pointsCopy[end-1]));
                }
                start = end;
            }
            // If 4 or more points are collinear, make a new segment and add it to the segments array.
        }
    }

   /***************************************************************************
    *  Class API
    ***************************************************************************/
    
    /*
     * The number of line segments
     */
    public int numberOfSegments() {
        return segments.size();
    }
    /*
     * Return the line segments.
     */
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[numberOfSegments()]);
    }
    
   /***************************************************************************
    *  Inner class
    ***************************************************************************/
    // private class SlopeOrder implements Comparator<Point> {
        
        /*
         * Compare the order with respect to slopes.
         */
    // }
    
   /***************************************************************************
    *  Helper Functions
    ***************************************************************************/
    
    /*
     * Check for valid input. The input is not valid if
     * - the input is null
     * - any entry is null
     * - any duplicate exists
     * Throw Exceptions for various errors
     * 
     * @param point - an sorted array of Point object
     * @return boolean - true for valid input
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