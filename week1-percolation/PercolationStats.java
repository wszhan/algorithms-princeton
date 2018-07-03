/******************************************************************************
 *  Compilation:  javac-algs4 PercolationStats.java
 *  Execution:    java-algs4 PercolationStats
 *  Dependencies: StdRandom.java StdStats.java
 *
 *  Implements percolation statistics.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] thresholdPerTrial;
    private final double trialNums;
    private final double mu, sigma;
    private final double a;
    /**
     * Initializes a PercolationStats object, and
     * performs trails independent experiments on
     * a n-by-n lattice.
     * 
     * @param n: the size of the lattice.
     * @param trials: number of trials to be conducted.
     */
    public PercolationStats(int n, int trials) {
        // Check arguments.
        if (trials <= 0 || !(trials < Integer.MAX_VALUE)) 
            throw new IllegalArgumentException("Illegal trials value");

        // Declares some variables.
        int index, row, col;
        double opened;
        double totalGrids;
        
        // Keeps track of the test results.
        thresholdPerTrial = new double[trials];
        trialNums = trials;
        totalGrids = n * n;
        a = 1.96;
        
        // Iterates trials
        for (int i = 0; i < trials; i++) {
            // Creates a new Percolation object.
            Percolation perc = new Percolation(n);
            // Randomly open a site until percolation.
            while (!perc.percolates()) {
                row = StdRandom.uniform(1, n+1);
                col = StdRandom.uniform(1, n+1);
                perc.open(row, col);
            }
            opened = perc.numberOfOpenSites();
            thresholdPerTrial[i] = opened / totalGrids;
        }
        mu = mean();
        sigma = stddev();
    }
    
    /**
     * Computes the sample mean of percolation thresholds.
     */
    public double mean() {
        return StdStats.mean(thresholdPerTrial);
    }
    
    /**
     * sample standard deviation of percolation threshold
     */
    public double stddev() {
        return StdStats.stddev(thresholdPerTrial);
    }
    
    /**
     * low  endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return (mu - (a * sigma / Math.sqrt(trialNums)));
    }
        
    /**
     * high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return (mu + (a * sigma / Math.sqrt(trialNums)));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);
        System.out.println("mean\t\t\t= " + ps.mu);
        System.out.println("stddev\t\t\t= " + ps.sigma);
        System.out.println("95% confidence interval = [" + ps.confidenceLo() 
                               + ", " + ps.confidenceHi() + "]");
    }
}