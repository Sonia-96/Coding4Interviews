import org.junit.Test;

public class q276_ReverseLinkList {
     public class ListNode {
         int val;
         ListNode next;
         ListNode() {}
         ListNode(int val) { this.val = val; }
         ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     }

    public ListNode reverseList(ListNode head) {
        ListNode prev = null, curr = head, next;
        while (curr != null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    public ListNode reverseList2(ListNode head) {
         return reverseList2(null, head);
    }

    private ListNode reverseList2(ListNode prev, ListNode curr) {
         if (curr == null) {
             return prev;
         }
         ListNode next = curr.next;
         curr.next = prev;
         return reverseList2(curr, next);
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
    public void test1() {
        ListNode head = of(new int[] {10, 1, 60, 30, 5});
        ListNode res = reverseList2(head);
        printList(res);
    }
}
