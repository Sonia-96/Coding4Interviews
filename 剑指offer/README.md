# 59-II 队列的最大值

- 使用队列queue记录元素的插入顺序
- 插入元素v后，v前插入的、比它小的元素对`max_value()`的返回值不起作用。例如，按顺序插入[1, 1, 2]，在2被弹出以前，max_value()的返回值都是2。
- 使用单调递减的双端队列deque记录`max_value()`的返回值。
  - 当`push_back(v)`时，首先从deque队尾开始删除比v小的元素，然后将v放入deque队尾
  - 当`pop_front()`时，如果queue和deque的头部元素相同，则同步弹出，否则只弹出queue的头部元素
  - deque头部即为`max_value()`的返回值

```java
class MaxQueue {
    Queue<Integer> queue;
    // 递减的双端队列，其头部为队列的最大值
    Deque<Integer> deque;

    public MaxQueue() {
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
```

