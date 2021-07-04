public class q21a_MergeTwoSortedLists {
     public class ListNode {
         int val;
         ListNode next;
         ListNode() {}
         ListNode(int val) { this.val = val; }
         ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     }

     // 迭代
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode sentinel = new ListNode(-101);
        ListNode p = sentinel;
        while (l1 != null & l2 != null) {
            if (l1.val <= l2.val) {
                p.next = l1; // 学习，不用新建结点，直接修改指针
                l1 = l1.next;
            } else {
                p.next = l2;
                l2 = l2.next;
            }
            p = p.next;
        }
        p.next = l1 == null ? l2 : l1; //学习
        return sentinel.next;
    }
}
