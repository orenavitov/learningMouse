import mih.slort.RandomNumFactory as factory
"""
插入排序
"""
def sort(nums):
    if len(nums) >= 1:
        result = []
        for num in nums:
            resultLength = len(result)
            if resultLength > 0:
                for i in range(0, resultLength):
                    if num < result[i]:
                        tempResult = []
                        for j in range(0, resultLength + 1):
                            if j < i:
                                tempResult.append(result[j])
                            if j == i:
                                tempResult.append(num)
                            if j > i:
                                tempResult.append(result[j - 1])
                        result = tempResult
                        break
                    else:
                        if i == resultLength - 1:
                            if num < result[resultLength - 1]:
                                last = result[resultLength - 1]
                                result[resultLength - 1] = num
                                result.append(last)
                            else:
                                result.append(num)


            else:
                result.append(num)

        return result
