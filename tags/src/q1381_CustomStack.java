import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class CustomStack {
    int[] stack;
    int[] add;
    int size;

    public CustomStack(int maxSize) {
        stack = new int[maxSize];
        add = new int[maxSize];
        Arrays.fill(stack, 0);
        Arrays.fill(add, 0);
        size = 0;
    }

    public void push(int x) {
        if (size < stack.length) {
            stack[size++] = x;
        }
    }

    public int pop() {
        if (size == 0) {
            return -1;
        }
        int res = stack[size - 1] + add[size - 1];
        if (size > 1) {
            add[size - 2] += add[size - 1];
        }
        add[size - 1] = 0;
        size--;
        return res;
    }

    public void increment(int k, int val) {
        int limit = Math.min(k - 1, size - 1);
        if (limit >= 0) {
            add[limit] += val;
        }
    }

    @Test
    public static void main(String[] args) {
        CustomStack stack = new CustomStack(169);
        Assertions.assertEquals(stack.pop(), -1);
        Assertions.assertEquals(stack.pop(), -1);
        Assertions.assertEquals(stack.pop(), -1);
        Assertions.assertEquals(stack.pop(), -1);
        Assertions.assertEquals(stack.pop(), -1);
        Assertions.assertEquals(stack.pop(), -1);
        Assertions.assertEquals(stack.pop(), -1);
        Assertions.assertEquals(stack.pop(), -1);
        stack.increment(8, 26);
        stack.push(83);
        stack.increment(2, 94);
        Assertions.assertEquals(stack.pop(), 177);
        Assertions.assertEquals(stack.pop(), -1);
        stack.increment(3, 1);
        Assertions.assertEquals(stack.pop(), -1);
        Assertions.assertEquals(stack.pop(), -1);
        stack.increment(1, 51);
        stack.increment(6, 75);
        Assertions.assertEquals(stack.pop(), -1);
        Assertions.assertEquals(stack.pop(), -1);
        Assertions.assertEquals(stack.pop(), -1);
        stack.increment(10, 75);
        Assertions.assertEquals(stack.pop(), -1);
        stack.increment(5, 98);
        stack.increment(9, 99);
        Assertions.assertEquals(stack.pop(), -1);
        stack.increment(8, 50);
        stack.push(41);
        Assertions.assertEquals(stack.pop(), 41);
    }
}


