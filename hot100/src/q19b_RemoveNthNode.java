import java.util.Stack;

public class q19b_RemoveNthNode {
    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        Stack<ListNode> stack = new Stack<>();
        ListNode sentinel = new ListNode(0, head);
        ListNode p = sentinel;
        while (p != null) {
            stack.push(p);
            p = p.next;
        }
        for (int i = 0; i < n; i++) {
            stack.pop();
        }
        ListNode pre = stack.pop();
        pre.next = pre.next.next;
        return sentinel.next;
    }
}
