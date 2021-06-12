
class Solution:
    # Brute Force
    def Find1(self, target, array):
        n = len(array)
        for i in range(n):
            if target in array[i]:
                return 'true'
        return 'false'

    # Divide and Conquer
    def Find2(self, target, array):
        row = len(array)
        col = len(array[0])
        i = 0
        j = col - 1
        while 0 <= i < row and 0 <= j < col:
            if array[i][j] < target:
                i += 1
            elif array[i][j] > target:
                j -= 1
            else:
                return 'true'
        return 'false'

    # Binary Search
    def Find3(self, target, array):
        row = len(array)
        col = len(array[0])
        for i in range(row):
            low = 0
            high = col - 1
            while low <= high:
                mid = (low + high) // 2
                if array[i][mid] < target:
                    low = mid + 1
                elif array[i][mid] > target:
                    high = mid - 1
                else:
                    return 'true'
        return 'false'


while True:
    try:
        S = Solution()
        L = list(eval(input()))
        target, array = L[0], L[1]
        print(S.Find2(target, array))
    except:
        break
