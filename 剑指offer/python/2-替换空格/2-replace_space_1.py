class Solution:
    def replaceSpace(self, s):
        t = ''
        for e in s:
            if e == ' ':
                t += '%20'
            else:
                t += e
        return t

