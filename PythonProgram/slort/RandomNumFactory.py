import random
"""
产生指定数量的随机数
"""

def randomNumFactory(size):
    nums = []
    for i in  range(0, size):
        num = int(random.uniform(10, 99))
        nums.append(num)
    return nums

