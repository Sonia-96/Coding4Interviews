import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

public class q225b_MyStack {
    Queue<Integer> q1;
    Queue<Integer> q2;
    int top;

    public q225b_MyStack() {
        q1 = new LinkedList<>();
        q2 = new LinkedList<>();
    }

    public void push(int x) {
        q2.add(x);
        top = x;
        while (!q1.isEmpty()) {
            q2.add(q1.remove());
        }
        Queue<Integer> temp = q1;
        q1 = q2;
        q2 = temp;
    }

    public int pop() {
        int res = q1.remove();
        if (!q1.isEmpty()) {
            top = q1.peek();
        }
        return res;
    }

    public int top() {
        return top;
    }

    public boolean empty() {
        return q1.isEmpty();
    }

    @Test
    public void test() {
        q225b_MyStack myStack = new q225b_MyStack();
        myStack.push(1);
        myStack.push(2);
        Assert.assertEquals(2, myStack.top());
        Assert.assertEquals(2, myStack.pop());
        Assert.assertEquals(false, myStack.empty());
    }

    @Test
    public void test2() {
        q225b_MyStack myStack = new q225b_MyStack();
        myStack.push(1);
        Assert.assertEquals(1, myStack.pop());
        Assert.assertEquals(true, myStack.empty());
    }
}
