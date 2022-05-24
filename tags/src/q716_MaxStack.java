import org.junit.Assert;
import org.junit.Test;

import java.util.Stack;

public class q716_MaxStack {

    Stack<Integer> stack;
    Stack<Integer> maxStack;

    public q716_MaxStack() {
        stack = new Stack<>();
        maxStack = new Stack<>();
    }

    public void push(int x) {
        stack.push(x);
        if (maxStack.isEmpty() || maxStack.peek() <= x) {
            maxStack.push(x);
        } else {
            maxStack.push(maxStack.peek());
        }
    }

    public int pop() {
        maxStack.pop();
        return stack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int peekMax() {
        return maxStack.peek();
    }

    public int popMax() {
        int max = peekMax();
        Stack<Integer> buffer = new Stack<>();
        while (top() != max) {
            buffer.push(pop());
        }
        pop();
        while (!buffer.isEmpty()) {
            push(buffer.pop());
        }
        return max;
    }

    @Test
    public void test() {
        q716_MaxStack s = new q716_MaxStack();
        s.push(3);
        s.push(1);
        s.push(5);
        s.push(2);
        s.push(4);
        Assert.assertEquals(5, s.peekMax());
        Assert.assertEquals(5, s.popMax());
    }

    @Test
    public void test2() {
        q716_MaxStack s = new q716_MaxStack();
        s.push(5);
        s.push(1);
        s.push(5);
        Assert.assertEquals(5, s.top());
        Assert.assertEquals(5, s.popMax());
        Assert.assertEquals(1, s.top());
        Assert.assertEquals(5, s.peekMax());
        Assert.assertEquals(1, s.pop());
        Assert.assertEquals(5, s.top());
    }
}