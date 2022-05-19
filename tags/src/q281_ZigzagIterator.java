import java.util.Iterator;
import java.util.List;

public class q281_ZigzagIterator {
    Iterator<Integer> it1;
    Iterator<Integer> it2;
    int count;

    public q281_ZigzagIterator(List<Integer> v1, List<Integer> v2) {
        it1 = v1.iterator();
        it2 = v2.iterator();
        count = 0;
    }

    public int next() {
        count++;
        if (!it2.hasNext()) {
            return it1.next();
        }
        if (!it1.hasNext()) {
            return it2.next();
        }
        if ((count & 1) == 1) {
            return it1.next();
        } else {
            return it2.next();
        }
    }

    public boolean hasNext() {
        return it1.hasNext() || it2.hasNext();
    }
}
