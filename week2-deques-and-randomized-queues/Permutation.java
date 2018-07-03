/******************************************************************************
 *  Compilation:  javac-algs4 Permutation.java
 *  Execution:    java-algs4 Permutation
 *  Dependencies: StdIn.java StdRandom.java
 *
 *  Implements Randomized Queue.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int size = 0;
        int n = Integer.parseInt(args[0]);
        RandomizedQueue<String> ranque = new RandomizedQueue<String>();
        
        if (n == 0) return;
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            
            // The queue is full
            if (size >= n) {
                // true for replace, false for pass
                if (StdRandom.bernoulli()) {
                    ranque.dequeue(); // randomly dequeue a node
                    ranque.enqueue(item); // add the lucky node
                }
            }
            // when the size is less than specified n
            else {
                ranque.enqueue(item);
                size++;
            }
        }
        for (int i = 0; i < n; i++) {
            System.out.println(ranque.dequeue());
        }
        // System.out.println("Ranque size: " + size);
    }
}