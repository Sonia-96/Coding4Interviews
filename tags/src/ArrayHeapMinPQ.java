import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private ArrayList<Node> heap; // 1-based index
    private HashMap<T, Integer> itemMapIndex;

    private class Node {
        T item;
        double priority;

        public Node(T i, double p) {
            item = i;
            priority = p;
        }
    }

    public ArrayHeapMinPQ() {
        heap = new ArrayList<>();
        heap.add(null);
        itemMapIndex = new HashMap<>();
    }

    private int parent(int i) {
        return i / 2;
    }

    private int left(int i) {
        return i * 2;
    }

    private int right(int i) {
        return i * 2 + 1;
    }

    private void swap(int i, int j) {
        Node temp = heap.get(j);
        heap.set(j, heap.get(i));
        heap.set(i, temp);
        itemMapIndex.replace(heap.get(i).item, i);
        itemMapIndex.replace(heap.get(j).item, j);
    }

    /* If the node is smaller than its parent, then swap them.
     */
    private void swim(int i) {
        int parent = parent(i);
        if (parent > 0 && parent < size()) {
            if (heap.get(i).priority < heap.get(parent).priority) {
                swap(i, parent);
                swim(parent);
            }
        }
    }

    /* If the node is bigger than its children, then swap them.
     */
    private void sink(int i) {
        int minIndex = i, left = left(i), right = right(i);
        if (left <= size() && heap.get(i).priority > heap.get(left).priority) {
            minIndex = left;
        }
        if (right <=size() && heap.get(minIndex).priority > heap.get(right).priority) {
            minIndex = right;
        }
        if (i != minIndex) {
            swap(minIndex, i);
            sink(minIndex);
        }
    }

    /* Adds an item with the given priority value. Throws an
     * IllegalArgumentExceptionb if item is already present.
     * You may assume that item is never null. */
    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        itemMapIndex.put(item, size() + 1);
        heap.add(new Node(item, priority));
        swim(size());
    }

    /* Returns true if the PQ contains the given item. */
    @Override
    public boolean contains(T item) {
        return itemMapIndex.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        return heap.get(1).item;
    }

    @Override
    public T removeSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        T toReturn = getSmallest();
        swap(1, size());
        itemMapIndex.remove(toReturn);
        sink(1);
        return toReturn;
    }

    @Override
    public int size() {
        return itemMapIndex.size();
    }

    /* Changes the priority of the given item. Throws NoSuchElementException if the item
     * doesn't exist. */
    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        int index = itemMapIndex.get(item);
        double oldPriority = heap.get(index).priority;
        heap.get(index).priority = priority;
        if (priority > oldPriority) {
            sink(index);
        } else {
            swim(index);
        }
    }
}
