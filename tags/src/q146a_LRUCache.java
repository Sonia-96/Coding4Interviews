import org.junit.jupiter.api.Assertions;

import java.util.HashMap;
import java.util.Map;

public class q146a_LRUCache {
    private static class Node {
        int key;
        int value;
        Node prev;
        Node next;

        Node(int k, int v) {
            key = k;
            value = v;
        }
    }

    int capacity;
    Map<Integer, Node> cache;
    Node sentinel;

    public q146a_LRUCache(int cap) {
        capacity = cap;
        cache = new HashMap<>();
        sentinel = new Node(-1, -1);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    public int get(int key) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            moveToHead(node);
            return node.value;
        }
        return -1;
    }

    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            node.value = value;
            moveToHead(node);
        } else {
            Node node = new Node(key, value);
            addHead(node);
            cache.put(key, node);
            if (cache.size() > capacity) {
                Node last = removeLast();
                cache.remove(last.key);
            }
        }
    }

    private void addHead(Node node) {
        node.prev = sentinel;
        node.next = sentinel.next;
        sentinel.next = node;
        node.next.prev = node;
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void moveToHead(Node node) {
        removeNode(node);
        addHead(node);
    }

    private Node removeLast() {
        Node last = sentinel.prev;
        sentinel.prev = last.prev;
        last.prev.next = sentinel;
        return last;
    }

    public static void main(String[] args) {
        q146a_LRUCache lru = new q146a_LRUCache(2);
        lru.put(1, 1);
        lru.put(2, 2);
        Assertions.assertEquals(1, lru.get(1));
    }
}
