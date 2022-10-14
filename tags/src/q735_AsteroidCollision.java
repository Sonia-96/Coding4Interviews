import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Stack;

public class q735_AsteroidCollision {

    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stack = new Stack<>();
        for (int asteroid : asteroids) {
            collision: {
                while (!stack.isEmpty() && asteroid < 0 && stack.peek() > 0) {
                    if (-asteroid > stack.peek()) {
                        stack.pop();
                    } else if (asteroid == -stack.peek()) {
                        stack.pop();
                        break collision;
                    } else {
                        break collision;
                    }
                }
                stack.push(asteroid);
            }
        }
        int[] res = new int[stack.size()];
        for (int i = res.length - 1; i >= 0; i--) {
            res[i] = stack.pop();
        }
        return res;
    }

    @Test
    public void test() {
        Assertions.assertArrayEquals(new int[] {5, 10}, asteroidCollision(new int[] {5, 10, -5}));
        Assertions.assertArrayEquals(new int[] {}, asteroidCollision(new int[] {8, -8}));
        Assertions.assertArrayEquals(new int[] {10}, asteroidCollision(new int[] {10, 2, -5}));
        Assertions.assertArrayEquals(new int[] {-2,-1,1,2}, asteroidCollision(new int[] {-2,-1,1,2}));
        Assertions.assertArrayEquals(new int[] {-2, -2, -2}, asteroidCollision(new int[] {-2,-2,1,-2}));
    }
}
