public interface ExtrinsicMinPQ<T> {
    /* Adds an item with the given priority value. Throws an
     * IllegalArgumentExceptionb if item is already present.
     * You may assume that item is never null. */
    void add(T item, double priority);
    /* Returns true if the PQ contains the given item. */
    boolean contains(T item);
    /* Returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    T getSmallest();
    /* Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    T removeSmallest();
    /* Returns the number of items in the PQ. */
    int size();
    /* Changes the priority of the given item. Throws NoSuchElementException if the item
     * doesn't exist. */
    void changePriority(T item, double priority);
}