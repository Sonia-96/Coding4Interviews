import java.util.Stack;

/**
 * Approach #2: push - O(n), pop - O(1)
 */
public class q232a_MyQueue {
    Stack<Integer> stack1;
    Stack<Integer> stack2;
    int front;

    public q232a_MyQueue() {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
    }

    public void push(int x) {
        if (stack1.isEmpty()) {
            front = x;
        }
        while (!stack1.isEmpty()) {
            stack2.push(stack1.pop());
        }
        stack1.push(x);
        while (!stack2.isEmpty()) {
            stack1.push(stack2.pop());
        }
    }

    public int pop() {
        int res = stack1.pop();
        if (!stack1.empty()) {
            front = stack1.peek();
        }
        return res;
    }

    public int peek() {
        return front;
    }

    public boolean empty() {
        return stack1.isEmpty();
    }
}
