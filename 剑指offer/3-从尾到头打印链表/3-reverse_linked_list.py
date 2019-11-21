from collections import deque


class ListNode:
    def __init__(self, x):
        self.val = x
        self.next = None


class Solution:
    def printListFromTailToHead(self, listNode):
        lst = deque()
        while listNode:
            lst.appendleft(listNode.val)
            listNode = listNode.next
        return lst


