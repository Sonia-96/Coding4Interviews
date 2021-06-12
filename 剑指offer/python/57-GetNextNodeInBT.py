# -*- coding:utf-8 -*-

class TreeLinkNode:
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None
        self.next = None

class Solution:
    # Method 1
    def GetNext1(self, pNode):
        # 找到根结点
        root = pNode
        while root.next:
            root = root.next
        # 中序遍历，用指针prev记录上一个打印的结点
        stack = []
        prev = None
        while stack or root:
            while root:
                stack.append(root)
                root = root.next
            root = stack.pop()
            if prev == pNode:
                return root
            prev = root
            root = root.right
        return None

    # Method 2
    def GetNext2(self, pNode):
        if not pNode:
            return None
        # 如果pNode有右子树，则找到右子树最左子结点
        if pNode.right:
            pNode = pNode.right
            while pNode.left:
                pNode = pNode.left
            return pNode
        # 如果没有右子树，则找到第一个位于其右祖先节点
        while pNode.next:
            parent = pNode.next
            if parent.left == pNode:
                return parent
            pNode = parent


# 测试样例
n1 = TreeLinkNode(1)
n2 = TreeLinkNode(2)
n3 = TreeLinkNode(3)
n4 = TreeLinkNode(4)
n5 = TreeLinkNode(5)
n6 = TreeLinkNode(6)
n7 = TreeLinkNode(7)
n8 = TreeLinkNode(8)
n1.left, n1.right = n2, n3
n2.next, n2.left = n1, n4
n3.next, n3.left, n3.right = n1, n5, n6
n4.next, n4.right = n2, n7
n5.next = n3
n6.next, n6.left = n3, n8
n7.next = n4
n8.next = n6


S = Solution()
res = S.GetNext(n6)
print(res)



