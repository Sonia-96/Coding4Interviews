import java.util.HashSet;

public class q141_LinkedListCycle {
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public boolean hasCycleNaive(ListNode head) {
        HashSet<ListNode> visited = new HashSet<>();
        while (head != null) {
            if (!visited.add(head)) {
                return true;
            }
            head = head.next;
        }
        return false;
    }

    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head, fast = head.next;
        while (slow != fast) {
            if (slow == null || fast == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }
}
