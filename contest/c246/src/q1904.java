import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class q1904 {
    public int numberOfRounds(String startTime, String finishTime) {
        int start = toMinutes(startTime);
        int finish = toMinutes(finishTime);
        finish += finish < start ? 1440 : 0;
        finish = finish / 15 * 15; // 第一个<=finishTime的完整对局结束的时间
        return (finish - start) / 15;
    }

    private int toMinutes(String time) {
        int h = Integer.parseInt(time.substring(0, 2));
        int min = Integer.parseInt(time.substring(3, 5));
        return h * 60 + min;
    }

    @Test
    public void test() {
        assertEquals(1, numberOfRounds("12:01", "12:44"));
        assertEquals(40, numberOfRounds("20:00", "06:00"));
        assertEquals(95, numberOfRounds("00:00", "23:59"));
        assertEquals(95, numberOfRounds("00:01", "00:00"));
    }
}
