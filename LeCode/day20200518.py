"""
给定一个仅包含大小写字母和空格 ' ' 的字符串 s，返回其最后一个单词的长度。如果字符串从左向右滚动显示，那么最后一个单词就是最后出现的单词。

如果不存在最后一个单词，请返回 0 。

说明：一个单词是指仅由字母组成、不包含任何空格字符的 最大子字符串。
例如：
输入: "Hello World"
输出: 5
"""
class Solution:
    def lengthOfLastWord(self, s: str) -> int:
        l = len(s)
        if l == 0:
            return 0

        max_length = 0

        for i in range(l):
            if s[i] == ' ':
                if max_length > 0:
                    break
                else:
                    continue
            else:
                max_length = max_length + 1

        return max_length

if __name__ == '__main__':
    s = Solution()
    max_length = s.lengthOfLastWord("Today is a nice day")
    print(max_length)
