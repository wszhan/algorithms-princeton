/******************************************************************************
 *  Compilation:  javac-algs4 Percolation.java
 *  Execution:    java-algs4 Percolation
 *  Dependencies: WeightedQuickUnionUF.java
 *
 *  Implements percolation grid
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[] grid;
    private final int size, top, bottom;
    private final WeightedQuickUnionUF wqTop, wqBottom;
    private int count;
    
    /**
     * Initializes a (n * n + 2) grid with all sites block
     * The grid starts at 1 and ends at 25.
     * Virtual top at index 0, and virtual bottom index 26.
     * Virtual top connects to the first 5 sites(1-5), and
     * Virtual bottom to the last 5 sites(21-25).
     * 0 represents the site is block.
     * The index of each site is (i // n + 1, i % n).
     * 
     * @param the length of the grid
     * @throw IllegalArgumentException if n is not a positive integer
     */
    public Percolation(int n) {
        if ((n <= 0) || !(n < Integer.MAX_VALUE))
            throw new IllegalArgumentException("n must be larger than 0.");
        
        // normal case
        int length = n * n + 2;
        grid = new boolean[length];
        top = 0;
        bottom = length - 1;
        count = 0;
        wqTop = new WeightedQuickUnionUF(length);
        wqBottom = new WeightedQuickUnionUF(length);
        
        size = n;
        // Initialize grid
        for (int i = 1; i < length-1; i++) grid[i] = false;
        
        // Initialze virtual sites
        grid[top] = true;
        grid[bottom] = true;
    }
    
    /**
     * Gets the index of the site (row, col)
     * 
     * @param the row, col coordinate of a site
     */
    private int getIndex(int row, int col) {
        // Validating index
        validateIndex(row, col);
        
        return (row - 1) * size + col;
    }
    
    /**
     * Connects to both find-union objects.
     */
    private void linkSites(int i, int j) {
        wqTop.union(i, j);
        wqBottom.union(i, j);
    }
    
    /**
     * Validates the index.
     * 
     * @param index row and col.
     */
    private void validateIndex(int row, int col) {
        if (row > size || row <= 0) {
            // System.out.println("Row " + row + " Col " + col + " with n " + size + " and length " + length); // debug
            throw new IllegalArgumentException("Row index out of bounds.");
        }
        if (col > size || col <= 0) {
            // System.out.println("Row " + row + " Col " + col + " with n " + size + " and length " + length); // debug
            throw new IllegalArgumentException("Column index out of bounds.");
        }
    }
    
    /**
     * Opens site with index(row, col) if it is not open already
     * 
     * @param the row, col coordinate of a site
     */
    public    void open(int row, int col) {
        // Validating index
        validateIndex(row, col);
        
        int index = getIndex(row, col);
        
        // Check if already open
        if (isOpen(row, col))
            return;
        // 0 means blocked. 1 means opened.
        grid[index] = true;
        count++;
        
        // Union action after opening a site.
        if (row == 1) // top row
            // weightedQU.union(top, index);
            linkSites(top, index);
        
        //    weightedQU.union(length-1, index);
        if (row > 1 && isOpen(row-1, col)) // up site
            // weightedQU.union(index, getIndex(row-1, col));
            linkSites(index, getIndex(row-1, col));
        if (row < size && isOpen(row+1, col)) // down site
            // weightedQU.union(index, getIndex(row+1, col));
            linkSites(index, getIndex(row+1, col));
        if (col > 1 && isOpen(row, col-1)) // left site
            // weightedQU.union(index, getIndex(row, col-1));
            linkSites(index, getIndex(row, col-1));
        if (col < size && isOpen(row, col+1)) // right site
            // weightedQU.union(index, getIndex(row, col+1));
            linkSites(index, getIndex(row, col+1));
        if (row == size) 
            wqBottom.union(bottom, index);
    }
    
    /**
     * Checks whether site (row, col) is open or not.
     * 
     * @param the row, col coordinate of a site
     */
    public boolean isOpen(int row, int col) {
        // Validating index
        validateIndex(row, col);
        
        int index = getIndex(row, col);
        if (grid[index]) return true;
        // else return false;
        return false;
    }
    
    /**
     * Check whether the site is open and is connected
     * to any open site(or the virtual top) in the top row.
     * 
     * @param the row, col coordinate of a site
     */
    public boolean isFull(int row, int col)  {
        // Validating index
        validateIndex(row, col);
        
        int index = getIndex(row, col);
        if (wqTop.connected(top, index) && isOpen(row, col))
            return true;
        return false;
    }
    
    /**
     * Counts the number of open sites,
     * excluding the virtual sites.
     */
    public int numberOfOpenSites() {
        return count;
    }
    
    /**
     * Checks whether the grid has percolated.
     */
    public boolean percolates() {
        return wqBottom.connected(top, bottom);
    }
    
    /**
     * Unit tests the {@code Percolation} data type.
     * 
     * @param args the command-line arguments.
     */
    public static void main(String[] args) {
        // int n = Integer.parseInt(args[0]);
        // Percolation perc = new Percolation(n);
        // System.out.println(perc.percolates());
        // perc.open(1, 1);
        // perc.isFull(1, 1);
        // System.out.println(perc.percolates());
        // System.out.println(perc.percolates()); // false
    }
}