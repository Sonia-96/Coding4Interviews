class Solution:
    def replaceSpace(self, s):
        s = list(s)
        n = len(s)
        for i in range(n):
            if s[i] == ' ':
                s[i] = '%20'
        return ''.join(s)
