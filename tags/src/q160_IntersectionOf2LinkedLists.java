import org.junit.Test;

public class q160_IntersectionOf2LinkedLists {
     public class ListNode {
         int val;
         ListNode next;

         ListNode(int x) {
             val = x;
             next = null;
         }
     }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
         if (headA == null || headB == null) {
             return null;
         }
         ListNode pA = headA, pB = headB;
         while (pA != pB) {
             pA = pA == null ? headB : pA.next;
             pB = pB == null ? headA : pB.next;
         }
         return pA;
    }

    @Test
    public void test() {
         ListNode headA = new ListNode(3);
         ListNode headB = new ListNode(2);
         headB.next = headA;
         System.out.print(getIntersectionNode(headA, headB).val);
    }
}
