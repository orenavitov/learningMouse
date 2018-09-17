"""
希尔排序
"""
import mih.slort.RandomNumFactory as factory
import mih.slort.StraightInsertSort as insertSort
def shellSort(nums, skipNum):
    numsSize = len(nums)
    childNums = []
    result = []
    if skipNum > 1:
        for i in range(0, skipNum):
            temp = []
            for j in range(i, numsSize, skipNum):
                temp.append(nums[j])
            childNums.append(temp)
        for j in range(0, len(childNums)):
            childNums[j] = insertSort.sort(childNums[j])
        for k in range(0, numsSize):
            index = k % skipNum
            result.append(childNums[index].pop(0))
        result = shellSort(result, int(skipNum / 2))
    else:
        result = insertSort.sort(nums)
    return result

nums = factory.randomNumFactory(10)

result = shellSort(nums, 3)
print(str(result))