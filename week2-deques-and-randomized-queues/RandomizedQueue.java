/******************************************************************************
 *  Compilation:  javac-algs4 RandomizedQueue.java
 *  Execution:    java-algs4 RandomizedQueue
 *  Dependencies: Iterator.java StdIn.java StdRandom.java
 *
 *  Implements Randomized Queue.
 *
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a; // array of items
    private int size; // size of the array
    // private int nullSize;
    
    // Initializes an empty queue
    public RandomizedQueue() {
        int initialCapacity = 1;
        a = (Item[]) new Object[initialCapacity];
        size = 0;
        // nullSize = initialCapacity;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    public int size() {
        return size;
    }
    
    private void resize(int capacity) {
        assert capacity > size;
        
        // Textbook implementation
        Item[] temp = (Item[]) new Object[capacity];
        
        // Copy items from the original array 
        // into the new longer temp array
        for (int i = 0; i < size; i++) {
            temp[i] = a[i];
        }
        
        // REPLACE the old one with the new array
        a = temp;
    }
    
    public void enqueue(Item item) {
        // Catch the exception while trying to enqueue a null value
        if (item == null) throw new IllegalArgumentException("Null not accepted");
        
        // Double the array size when it is full
        if (size == a.length) resize(2 * a.length);
        
        // if not, just enqueue the item
        a[size++] = item;
    }
    
    public Item dequeue() {
        int removeIndex;
        Item item;
        // Catch the exception when the array is empty
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        
        // Randomly choose an index to remove without setting
        // the removed spot null.
        removeIndex = StdRandom.uniform(size); // returns an index within the bound
        item = a[removeIndex]; // for returning
        
        // Swap the last item to the empty spot
        // and set the last spot null
        a[removeIndex] = a[size-1]; // replacement
        a[size-1] = null; // to avoid loitering
        
        // Decrement the size count
        size--;
        
        // If ncessary, that is, the size is less than a quarter
        // of the array, shrink it by half
        if (size > 0 && size == a.length/4) resize(a.length/2);
        
        // Return the item
        return item;
    }
    
    /*
     * Returns a random item (but do not remove it)
     */
    public Item sample() {
        int sampleIndex;
        
        // Catch the exception when the array is empty
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        
        // Samples the index
        sampleIndex = StdRandom.uniform(size);
        
        return a[sampleIndex];
    }
    
    /*
     * Returns an independent iterator over items in random order
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
    
    // An iterator for the above method
    private class RandomizedQueueIterator implements Iterator<Item> {
        // private int i = 0;
        private RandomizedQueue<Item> queueCopy = new RandomizedQueue<Item>();
        
        public RandomizedQueueIterator() {
            for (int k = 0; k < size; k++) queueCopy.enqueue(a[k]);
        }
        
        public boolean hasNext() {
            // return i < size; // all the way till null
            return !queueCopy.isEmpty();
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            // return a[i++];
            return queueCopy.dequeue();
        }
    }
    
    // Unit Testing
    public static void main(String[] args) {
        RandomizedQueue<String> ranque = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                System.out.println("Adding to the queue: " + item);
                ranque.enqueue(item);
            }
            else {
                System.out.println("!!!Enemy spotted: " + item);
                System.out.println("!!!Kicking out: " + ranque.dequeue());
            }
        }
        System.out.println("Queue size: " + ranque.size);
        System.out.println("====================\nPrint out all items:");
        for (String s : ranque) {
            System.out.println(s);
        }
    }
}