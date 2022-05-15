import java.util.LinkedList;
import java.util.Queue;

public class q346a_MovingAverage {
    Queue<Integer> queue;
    int size;
    double sum;

    public q346a_MovingAverage(int size) {
        queue = new LinkedList<>();
        this.size = size;
        sum = 0;
    }

    public double next(int val) {
        if (queue.size() == size) {
            sum -= queue.remove();
        }
        queue.add(val);
        sum += val;
        return sum / queue.size();
    }
}
