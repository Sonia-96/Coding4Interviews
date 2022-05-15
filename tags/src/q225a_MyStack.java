import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

public class q225a_MyStack {
    Queue<Integer> q1;
    Queue<Integer> q2;
    int top;

    public q225a_MyStack() {
        q1 = new LinkedList<>();
        q2 = new LinkedList<>();
    }

    public void push(int x) {
        q1.add(x);
        top = x;
    }

    public int pop() {
        while (q1.size() > 1) {
            top = q1.remove();
            q2.add(top);
        }
        Queue<Integer> temp = q1;
        q1 = q2;
        q2 = temp;
        return q2.remove();
    }

    public int top() {
        return top;
    }

    public boolean empty() {
        return q1.isEmpty();
    }

    @Test
    public void test() {
        q225a_MyStack myStack = new q225a_MyStack();
        myStack.push(1);
        myStack.push(2);
        Assert.assertEquals(2, myStack.top());
        Assert.assertEquals(2, myStack.pop());
        Assert.assertEquals(false, myStack.empty());
    }

    @Test
    public void test2() {
        q225a_MyStack myStack = new q225a_MyStack();
        myStack.push(1);
        myStack.push(2);
        myStack.push(3);
        Assert.assertEquals(3, myStack.top());
    }
}
