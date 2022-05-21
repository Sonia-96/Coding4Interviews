import java.util.Stack;

public class q155_MinStack {
    Stack<Integer> stack;
    Stack<Integer> minStack;

    public q155_MinStack() {
        stack = new Stack<>();
        minStack = new Stack<>();
        minStack.add(Integer.MAX_VALUE);
    }

    public void push(int val) {
        stack.add(val);
        minStack.add(Math.min(minStack.peek(), val));
    }

    public void pop() {
        stack.pop();
        minStack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}
