import org.junit.Test;

public class q346b_MovingAverage {
    int[] window;
    int count;
    double sum;
    int i;

    public q346b_MovingAverage(int count) {
        window = new int[count];
        sum = 0;
        i = 0;
        this.count = 0;
    }

    public double next(int val) {
        if (count < window.length) {
            count--;
        }
        sum -= window[i];
        sum += val;
        window[i] = val;
        i = (i + 1) % window.length;
        return sum / count;
    }

    @Test
    public static void main(String[] args) {
        q346b_MovingAverage ds = new q346b_MovingAverage(3);
        System.out.print(ds.next(1));
//        Assert.assertEquals(1, ds.next(1), 0.00001);
//        Assert.assertEquals(5.5, ds.next(10), 0.00001);
//        Assert.assertEquals(4.6667, ds.next(3), 0.00001);
    }
}
