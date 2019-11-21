题目描述
-
输入一个链表，按链表从尾到头的顺序返回一个ArrayList。

解题思路
-
创建deque，遍历链表每个结点，每次appendleft(结点值)。<br>

说明：为什么使用queue而不使用list？<br>
将结点值插入列表的最左侧，时间复杂度上list为O(n),而deque只为O(1)。但deque所需内存要更大一点。
