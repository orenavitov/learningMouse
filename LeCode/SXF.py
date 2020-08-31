
from sys import stdin

class Solution:

    def __init__(self):
        self.results = []
        pass

    def commonChars(self , chars):
        if (len(chars) < 1):
            print("")
            return
        # write code here
        charCountMaps = []
        firstString = chars[0]
        for s in chars:
            length = len(s)
            charCountMap = {}
            for i in range(length):
                c = s[i]
                if (c not in charCountMap.keys()):
                    charCountMap[c] = 1
                else:
                    charCountMap[c] = charCountMap[c] + 1;
            charCountMaps.append(charCountMap)
        result = []
        for c in firstString:
            ok = True
            for charCountMap in charCountMaps:
                if (c in charCountMap.keys() and charCountMap[c] >= 1):
                    charCountMap[c] = charCountMap[c] - 1
                else:
                    ok = False
                    break
            if (ok):
                result.append(c)
        result.sort()
        print("".join(result))

    def min_send(self, nums, m):

        self.devide(nums, m, 0)
        return min(self.results)

    def devide(self, left_nums, left_devide, temp_Max):
        if (left_devide == 1):
            temp_sum = sum(left_nums);
            if (temp_sum > temp_Max):
                self.results.append(temp_sum)
            else:
                self.results.append(temp_Max);
        else:
            length = len(left_nums)
            if (length < left_devide):
                return
            if (length == left_devide):
                _temp_Max = min(left_nums)
                if (_temp_Max > temp_Max):
                    self.results.append(_temp_Max)
                else:
                    self.results.append(temp_Max);
                return
            for i in range(0, length):
                temp_nums = left_nums[0 : i + 1]
                temp_sum = sum(temp_nums)
                if (temp_sum > temp_Max):
                    temp_Max = temp_sum;
                temp_left_nums = left_nums[i + 1 : length]
                self.devide(left_nums = temp_left_nums, left_devide = left_devide - 1, temp_Max = temp_Max)

    def find_diff_char(self, str1, str2):
        length1 = len(str1)
        length2 = len(str2)
        result1 = 0
        result2 = 0
        for i in range(length1):
            result1 = result1 + int(str1[i])
        for i in range(length2):
            result2 = result2 + int(str2[i])
        print(result2 - result1)

if __name__ == '__main__':
    # chars = ["bella","labela","rollera", "ela", ""]
    # chars = ["bella"]
    s = Solution();
    # s.commonChars(chars)
    nums = [4,3,5,10,12]
    m = 2
    result = s.min_send(nums, m)
    print("min max : {0}".format(result))
    # str1, str2 = "abcd","abcde"
    # s.find_diff_char(str1, str2)
