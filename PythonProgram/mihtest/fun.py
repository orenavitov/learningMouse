
import math
import numpy as num
def test():
    HD = -(6 / 15) * math.log(6 / 15, 2) - (9 / 15) * math.log(9 / 15, 2)
    HD_X2_0 = (2 / 3) * (-(4 / 10) * math.log(4 / 10, 2) - (6 / 10) * math.log(6 / 10, 2))
    return HD - HD_X2_0

def aler(em):
    return 0.5 * math.log((1 - em) / em, math.e)

W_m_init = [0.1 for i in range(10)]
def nextW_m(W_m, aler, wrongs, rights):
    Z_m = 0.0
    for index in wrongs:
        Z_m = Z_m + W_m[index - 1] * math.e ** ((-aler) * (-1))
    for index in rights:
        Z_m += W_m[index - 1] * math.e ** ((-aler) * 1)
    for index in wrongs:
        W_m[index - 1] = (W_m[index - 1] / Z_m) * math.e ** ((-aler) * (-1))
    for index in rights:
        W_m[index - 1] = (W_m[index - 1] / Z_m) * math.e ** ((-aler) * 1)
    return W_m
if __name__ == '__main__':
    wrongs = [3, 4, 5]
    rights = [1, 2, 6, 7, 8, 9, 10]
    W_m = nextW_m(W_m_init, aler(0.3), wrongs, rights)
    print(W_m)
    print(W_m[6] + W_m[7] + W_m[8])