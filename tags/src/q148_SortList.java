import java.util.ArrayList;
import java.util.Collections;

public class q148_SortList {
    public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode sortList(ListNode head) {
        ArrayList<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        Collections.sort(list);
        ListNode sentinel = new ListNode(-1, null), p = sentinel;
        for (int i = 0; i < list.size(); i++) {
            p.next = new ListNode(list.get(i), null);
            p = p.next;
        }
        return sentinel.next;
    }
}
