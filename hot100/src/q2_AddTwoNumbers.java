import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class q2_AddTwoNumbers {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    // convert int array to link list
    private static ListNode of(int[] l) {
        ListNode pre = new ListNode(l[l.length - 1], null);
        for (int i = l.length - 2; i >= 0; i--) {
            ListNode curr = new ListNode(l[i], pre);
            pre = curr;
        }
        return pre;
    }

    // convert link list to int array
    private static int[] to(ListNode n) {
        int length = 0;
        ListNode p = n;
        while (p != null) {
            length += 1;
            p = p.next;
        }
        int[] res = new int[length];
        for (int i = 0; i < length; i++) {
            res[i] = n.val;
            n = n.next;
        }
        return res;
    }

    // solution
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = null, p = null;
        ListNode p1 = l1;
        ListNode p2 = l2;
        int carry = 0;
        while (p1 != null || p2 != null) {
            int n1 = (p1 == null ? 0 : p1.val);
            int n2 = (p2 == null ? 0 : p2.val);
            int sum = n1 + n2 + carry;
            p1 = (p1 == null ? null : p1.next);
            p2 = (p2 == null ? null : p2.next);

            carry = sum / 10;
            sum = sum % 10;
            if (p == null) {
                head = p = new ListNode(sum, null);
            } else {
                p.next = new ListNode(sum, null);
                p = p.next;
            }
        }
        if (carry == 1) {
            p.next = new ListNode(1, null);
        }
        return head;
    }

    // print the link list
    public static void print(ListNode l) {
        while (l != null) {
            System.out.print(l.val + " ");
            l = l.next;
        }
    }

    @Test
    public void test1() {
        ListNode l1 = of(new int[] {9,9,9,9,9,9,9});
        ListNode l2 = of(new int[] {9,9,9,9});
        ListNode l = addTwoNumbers(l1, l2);
        assertArrayEquals(new int[] {8,9,9,9,0,0,0,1}, to(l));
    }

    @Test
    public void test2() {
        ListNode l1 = of(new int[] {2, 4, 3});
        ListNode l2 = of(new int[] {5, 6, 4});
        ListNode l = addTwoNumbers(l1, l2);
        assertArrayEquals(new int[] {7, 0, 8}, to(l));
    }

    @Test
    public void test3() {
        ListNode l1 = of(new int[] {0});
        ListNode l2 = null;
        ListNode l = addTwoNumbers(l1, l2);
        assertArrayEquals(new int[] {0}, to(l));
    }
}
