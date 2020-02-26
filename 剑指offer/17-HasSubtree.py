# -*- coding:utf-8 -*-
# class TreeNode:
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None

# 第一步：在树A中找到和树B根节点的值一样的节点R
# 第二步：判断树A中以R为根节点的子树是否包含和树B一样的结构

# 代码一：
class Solution1:
    def HasSubtree(self, pRoot1, pRoot2):
        result = False
        if pRoot1 and pRoot2:
            if pRoot1.val == pRoot2.val:
                result = self.IsSubtree(pRoot1, pRoot2)
            if not result:
                result = self.HasSubtree(pRoot1.left, pRoot2)
            if not result:
                result = self.HasSubtree(pRoot1.right, pRoot2)
        return result

    def IsSubtree(self, pRoot1, pRoot2):
        if pRoot2 is None:
            return True
        if pRoot1 is None:
            return False
        if pRoot1.val == pRoot2.val:
            return self.IsSubtree(pRoot1.left, pRoot2.left) \
                   and self.IsSubtree(pRoot1.right, pRoot2.right)
        return False

# 代码二
class Solution2:
    def HasSubtree(self, pRoot1, pRoot2):
        if not pRoot1 or not pRoot2:
            return False
        return self.IsSubtree(pRoot1, pRoot2) \
               or self.HasSubtree(pRoot1.left, pRoot2) \
               or self.HasSubtree(pRoot1.right, pRoot2)

    def IsSubtree(self, pRoot1, pRoot2):
        if not pRoot2:
            return True
        if not pRoot1:
            return False
        if pRoot1.val == pRoot2.val:
            return self.IsSubtree(pRoot1.left, pRoot2.left) \
                   and self.IsSubtree(pRoot1.right, pRoot2.right)
        return False
