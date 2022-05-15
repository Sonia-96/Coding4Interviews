import org.junit.Test;

public class q92_ReverseLinkedList2 {
    public class ListNode {
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

    public ListNode reverseBetween2(ListNode head, int left, int right) {
        // create a sentinel node
        ListNode sentinel = new ListNode(-1, head);
        // find the node previous to the position left
        ListNode leftPrev = sentinel;
        for (int i = 0; i < left - 1; i++) {
            leftPrev = leftPrev.next;
        }
        // reverse the linked list from the position left to right
        ListNode prev = null, curr = leftPrev.next, next;
        for (int i = left; i <= right; i++) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        // link each part
        leftPrev.next.next = curr;
        leftPrev.next = prev;
        return sentinel.next;
    }

    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode sentinel = new ListNode(-1, head);
        // find the node previous to the position left
        ListNode leftPrev = sentinel;
        for (int i = 0; i < left - 1; i++) {
            leftPrev = leftPrev.next;
        }
        ListNode curr = leftPrev.next, next;
        for (int i = left; i < right; i++) {
            next = curr.next;
            curr.next = next.next;
            next.next = leftPrev.next;
            leftPrev.next = next;
        }
        return sentinel.next;
    }

    private ListNode of(int[] nums) {
        ListNode head = null;
        for (int i = nums.length - 1; i >= 0; i--) {
            head = new ListNode(nums[i], head);
        }
        return head;
    }

    private void printNode(ListNode x) {
        while (x != null) {
            System.out.print(x.val);
            x = x.next;
        }
        System.out.println();
    }

    @Test
    public void test1() {
        ListNode head = of(new int[] {3, 5});
        printNode(reverseBetween(head, 1, 2));
    }

    @Test
    public void test2() {
        ListNode head = of(new int[] {1, 2, 3, 4, 5});
        printNode(reverseBetween(head, 2, 4));
    }

    @Test
    public void test3() {
        ListNode head = new ListNode(3);
        printNode(reverseBetween(head, 1, 1));
    }

    @Test
    public void test4() {
        ListNode head = of(new int[] {3, 5});
        printNode(reverseBetween(head, 1, 1));
    }
}
