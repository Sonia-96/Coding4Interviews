import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class q1429_FirstUniqueNumber {

    private class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }

        ListNode(int x, ListNode n) {
            val = x;
            next = n;
        }
    }

    private class DataStream {
        Map<Integer, ListNode> uniqueNumToPrev;
        ListNode head, tail;
        Set<Integer> duplicates;

        public DataStream() {
            uniqueNumToPrev = new HashMap<>();
            head = new ListNode(-1); // dummy head
            tail = head;
            duplicates = new HashSet<>();
        }

        public void add(int x) {
            if (duplicates.contains(x)) {
                return;
            }
            if (uniqueNumToPrev.containsKey(x)) {
                remove(x);
                duplicates.add(x);
            } else {
                ListNode node = new ListNode(x, null);
                uniqueNumToPrev.put(x, tail);
                tail.next = node;
                tail = node;
            }
        }

        private void remove(int x) {
            // remove the node from the linked list
            ListNode prev = uniqueNumToPrev.get(x);
            prev.next = prev.next.next;
            uniqueNumToPrev.remove(x);
            // change tail or prev of the next node
            if (prev.next == null) {
                tail = prev;
            } else {
                uniqueNumToPrev.put(prev.next.val, prev);
            }

        }

        public int firstUnique() {
            if (head.next != null) {
                return head.next.val;
            }
            return -1;
        }
    }

    public int firstUniqueNumber(int[] nums, int number) {
        DataStream ds = new DataStream();
        for (int i = 0; i < nums.length; i++) {
            ds.add(nums[i]);
            if (nums[i] == number) {
                return ds.firstUnique();
            }
        }
        return -1;
    }

    @Test
    public void test() {
        Assert.assertEquals(-1, firstUniqueNumber(new int[] {}, 1));
        Assert.assertEquals(-1, firstUniqueNumber(new int[] {2}, 1));
        Assert.assertEquals(10, firstUniqueNumber(new int[] {1,2,2,1,2,4,4,5,6,6,7,7,8,8,8,5,10,11,11}, 11));
    }
}
