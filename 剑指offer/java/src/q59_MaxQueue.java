import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class q59_MaxQueue {
    Queue<Integer> queue;
    // 递减的双端队列，其头部为队列的最大值
    Deque<Integer> deque;

    public q59_MaxQueue() {
        queue = new LinkedList<>();
        deque = new LinkedList<>();
    }

    public int max_value() {
        if (deque.isEmpty()) return -1;
        return deque.peekFirst();
    }

    public void push_back(int value) {
        while (!deque.isEmpty() && deque.peekLast() < value) {
            deque.pollLast();
        }
        deque.addLast(value);
        queue.add(value);
    }

    public int pop_front() {
        if (queue.isEmpty()) return -1;
        int res = queue.poll();
        if (res == deque.peekFirst()) {
            deque.pollFirst();
        }
        return res;
    }
}
