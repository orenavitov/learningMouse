"""
给定一个二叉树，找出其最大深度。

二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。

说明: 叶子节点是指没有子节点的节点。

示例：
给定二叉树 [3,9,20,null,null,15,7]，

    3
   / \
  9  20
    /  \
   15   7
返回它的最大深度 3 。

链接：https://leetcode-cn.com/problems/maximum-depth-of-binary-tree
"""
class TreeNode:
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None

class Solution:

    def maxDepth(self, root):
        result = 0 if root == None else max(self.maxDepth(root.left), self.maxDepth(root.right)) + 1
        return result

node1 = TreeNode(3)
node2 = TreeNode(9)
node3 = TreeNode(20)
node4 = TreeNode(15)
node5 = TreeNode(7)
node1.left = node2
node1.right = node3
node3.left = node4
node3.right = node5
s1 = Solution()
print(s1.maxDepth(node1))

