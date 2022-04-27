import org.junit.Test;

public class q876_MiddleNode {
    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    /**
     * Fast and Slow Pointer:
     * While traversing the list with a pointer `slow`, make another pointer fast traverses twice as `fast`.
     * When `fast` reaches the end of the list or is null, `slow` must be in the middle.
     */
    public ListNode middleNode(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    private ListNode of(int[] nums) {
        ListNode head = null;
        for (int i = nums.length - 1; i >= 0; i--) {
            head = new ListNode(nums[i], head);
        }
        return head;
    }

    private void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
        System.out.println();
    }

    @Test
    public void test() {
        ListNode head1 = of(new int[] {10, 1, 60, 30, 5});
        ListNode res1 = middleNode(head1);
        printList(res1);
        ListNode head2 = of(new int[] {10, 1, 60, 30, 5, 20});
        ListNode res2 = middleNode(head2);
        printList(res2);
    }
}
