public class q328_OddEvenLinkedList {
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public ListNode oddEvenList2(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }
        ListNode oddLast = head, evenLast = head.next;
        ListNode curr = evenLast.next, next;
        while (curr != null) {
            next = curr.next;
            curr.next = oddLast.next;
            evenLast.next = next;
            oddLast.next = curr;
            oddLast = curr;
            evenLast = next;
            curr = next == null ? null : next.next;
        }
        return head;
    }

    public ListNode oddEvenList(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode odd = head, even = head.next, evenHead = even;
        while (even != null && even.next != null) {
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }
        odd.next = evenHead;
        return head;
    }
}
