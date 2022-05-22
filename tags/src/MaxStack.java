import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaxStack {
    List<Integer> stack;
    List<int[]> maxStack; // [max element, position]

    public MaxStack() {
        stack = new ArrayList<>();
        maxStack = new ArrayList<>();
    }

    public void push(int x) {
        stack.add(x);
        if (maxStack.isEmpty() || x >= maxStack.get(size() - 2)[0]) {
            maxStack.add(new int[] {x, size() - 1});
        } else {
            maxStack.add(Arrays.copyOf(maxStack.get(size() - 2), 2));
        }
    }

    public int pop() {
        int res = stack.get(size() - 1);
        stack.remove(size() - 1);
        maxStack.remove(size());
        return res;
    }

    public int top() {
        return stack.get(size() - 1);
    }

    public int peekMax() {
        return maxStack.get(size() - 1)[0];
    }

    public int popMax() {
        int res = maxStack.get(size() - 1)[0];
        int index = maxStack.get(size() - 1)[1];
        maxStack.remove(size() - 1);
        for (int i = index + 1; i < size(); i++) {
            stack.set(i - 1, stack.get(i));
            // set max element
            if (i == 1 || stack.get(i) >= maxStack.get(i - 2)[0]) {
                maxStack.set(i - 1, new int[] {stack.get(i), i - 1});
            } else {
                maxStack.set(i - 1, Arrays.copyOf(maxStack.get(i - 2), 2));
            }
        }
        stack.remove(size() - 1);
        return res;
    }

    private int size() {
        return stack.size();
    }

    @Test
    public void test() {
        MaxStack s = new MaxStack();
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
        MaxStack s = new MaxStack();
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