import org.junit.jupiter.api.Assertions;

import java.util.LinkedHashMap;
import java.util.Map;

public class q146b_LRUCache extends LinkedHashMap<Integer, Integer>{
    int capacity;

    public q146b_LRUCache(int c) {
        super(c, 0.75f, true); // true for accessOrder, false for insertion order
        this.capacity = c;
    }

    public int get(int key) {
        return super.get(key);
    }

    public void put(int key, int value) {
        super.put(key, value);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return size() > capacity;
    }

    public static void main(String[] args) {
        q146a_LRUCache lru = new q146a_LRUCache(2);
        lru.put(1, 1);
        lru.put(2, 2);
        Assertions.assertEquals(1, lru.get(1));
        lru.put(3, 3);
        System.out.println(lru.get(2));
        Assertions.assertEquals(-1, lru.get(2));
    }
}
