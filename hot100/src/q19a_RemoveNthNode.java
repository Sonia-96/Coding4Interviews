public class q19a_RemoveNthNode {
    public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode sentinel = new ListNode(0, head);
        int size = getSize(head);
        ListNode p = sentinel;
        for (int i = 0; i < size - n; i++) {
            p = p.next;
        }
        p.next = p.next.next;
        return sentinel.next;
    }

    private int getSize(ListNode head) {
        int size = 0;
        while (head != null) {
            size += 1;
            head = head.next;
        }
        return size;
    }
}
