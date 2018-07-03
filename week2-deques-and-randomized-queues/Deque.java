/******************************************************************************
 *  Compilation:  javac-algs4 Deque.java
 *  Execution:    java-algs4 Deque
 *  Dependencies: Iterator.java StdIn.java StdOut.java
 *
 *  Implements deque.
 *
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    /*
     * Private variables.
     */
    private Node<Item> first;
    private Node<Item> last;
    private int size;
    
    /*
     * Constructs an empty deque.
     */
    public Deque() {
        first = null;
        last = null;
        size = 0; // size initialized to 0 at creation
    }
    
    /*
     * Creates a new node.
     */
    private static class Node<Item> {
        private Item item;
        private Node<Item> prev;
        private Node<Item> next;
    }
    
    public boolean isEmpty() {
        return first == null && last == null;
        // return size == 0;
    }
    
    /*
     * Returns the number of items on the deque
     */
    public int size() {
        return size;
    }
    
    /*
     * Adds the item to the front
     */
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("Null not accepted");
        // pointer for swapping
        Node<Item> oldFirst = first;
        
        first = new Node<Item>();
        first.item = item;
        first.prev = null;
        // first.next = oldFirst;
        
        // whether first == null, that is, whether
        // before addFirst there was no item on the deque
        if (last == null && oldFirst == null) {
            // first.next = last;
            first.next = null;
            last = first;
        }
        else if (size == 1) {
            first.next = last;
            last.prev = first;
        }
        else {
            oldFirst.prev = first;
            first.next = oldFirst;
        }
        size++;
    }
    
    /*
     * Adds the item to the end
     */
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("Null not accepted");
        Node<Item> oldLast = last;
        
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        
        // whether first == null, that is, whether
        // before addLast there was no item on the deque
        // if (isEmpty())
        if (first == null && oldLast == null) {
            last.prev = null;
            first = last; // the only one item on the deque
        }
        else if (size == 1) {
            last.prev = first;
            first.next = last;
        }
        else {
            last.prev = oldLast;
            oldLast.next = last; // now oldLast is second last node
        }
        size++;
    }
    
    /*
     * Removes and returns the item from the front
     */
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item item = first.item; // For returning
        
        // Edge case, not empty but only one node
        if (size == 1) {
            // remove the only one node in the deque
            // and avoid loitering by setting both
            // pointers to be null
            first = null;
            last = null;
        }
        else if (size > 1) {
            first.next.prev = null;
            first = first.next;
        }
        // if (isEmpty()) last = null; // to avoid loitering
        // Decrements size and returns item;
        size--;
        return item;
    }
    
    /*
     * Removes and returns the item from the end
     */
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item item = last.item; // For returning
        // last = null;
        
        // Edge case, not empty but only one node
        if (size == 1) {
            // remove the only one node in the deque
            // and avoid loitering by setting both
            // pointers to be null
            first = null;
            last = null;
        }
        else if (size > 1) {
            last.prev.next = null;
            last = last.prev;
        }
        // if (isEmpty()) first = null; // avoid loitering
        
        // Decrements size and returns item;
        size--;
        return item;
    }
    
    /*
     * Returns an iterator over items in order from front to end
     */
    public Iterator<Item> iterator() {
        return new LinkedListIterator<Item>(first);
    }
    
    /*
     * Iterator
     */
    private class LinkedListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public LinkedListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No next element");
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
    
    /*
     * Unit test.
     */
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addLast(0);
        deque.addLast(1);
        // deque.isEmpty();
        deque.removeLast();
        
        /*
        // Unit test by manipulating integers
        Deque<Integer> deque = new Deque<Integer>();
        // Node<Integer> current = new Node<Integer>();
        for (int i = 0; i < 10; i++) {
            deque.addFirst(i);
            // System.out.println("Adding " + i + " to the first");
            // System.out.println(i + "th first: " + deque.first);
            // System.out.println(i + "th first.next: " + deque.first.next);
            // System.out.println(i + "th last: " + deque.last);
        }
        System.out.println("===================");
        
        System.out.println("Deque size: " + deque.size);
        System.out.println("Deque is empty? " + deque.isEmpty());
        System.out.println("===================");
        
        
        current = deque.first;
        // Print out the deque elements
        for (int i = 1; i < deque.size + 1; i++) {
            System.out.println(i + "th node: " + current);
            current = current.next;
        }
        
        
        
        for (Integer s : deque) {
            System.out.println("Deque elements: " + s);
        }
        
        System.out.println("===================");
        
        // Current first and last
        // System.out.println("Current first and last after adding:");
        // System.out.println(deque.first);
        // System.out.println(deque.last);
        
        // remove experiment
        System.out.println("Removing elements...");
        for (int i = 0; i < 10; i++) {
            // deque.removeLast();
            System.out.println("Last node to be removed: " + deque.last);
            System.out.println("Removing item: " + deque.removeLast());
            System.out.println("Item removed done. Next...");
            // System.out.println("Current first: " + deque.first);
            // System.out.println("Current last: " + deque.last);
        }
        
        deque.addLast(1);
        // System.out.println(deque.first.next.item);
        */
        
        // Unit test with string inputs
        /*
        Deque<String> deque = new Deque<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                // System.out.println(item);
                if (!deque.isEmpty()) {
                    deque.addLast(item);
                    // System.out.println(item + "Add First!!!");
                }
                else {
                    deque.addFirst(item);
                    // System.out.println(item + "Add Last!!!");
                }
            }
            else if (item.equals("-"))
                System.out.println(item + " : Ignoring dash.");
                // deque.addLast(item);
        }
        // System.out.println(deque.size);
        
        for (String s : deque) {
            StdOut.println(s);
        }
        */
    }
}