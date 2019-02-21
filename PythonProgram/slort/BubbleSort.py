import slort.RandomNumFactory as factory
"""
冒泡排序
bubbleSortForward 从小到大
"""


def bubbleSortForward(nums):
    length = len(nums)
    if 1 <= length < 2:
        return nums
    if length < 1:
        raise Exception("the length of input nums must bigger than 1")
    for i in range(0, length - 1):
        for j in range(0, length - 1 - i):
            if nums[j] > nums[j + 1]:
                temp = nums[j]
                nums[j] = nums[j + 1]
                nums[j + 1] = temp

    return nums


def bubbleSortBackward(nums):
    length = len(nums)
    if 1 <= length < 2:
        return nums
    if length < 1:
        raise Exception("the length of input nums must bigger than 1")
    for i in range(0, length - 1):
        for j in range(0, length - 1 - i):
            if nums[j] < nums[j + 1]:
                temp = nums[j]
                nums[j] = nums[j + 1]
                nums[j + 1] = temp

    return nums
    pass

if __name__ == "__main__":
    nums = factory.randomNumFactory(30)

    print("%s\n" % (str(nums)))
    result = bubbleSortBackward(nums)
    print("%s\n" % (str(nums)))
