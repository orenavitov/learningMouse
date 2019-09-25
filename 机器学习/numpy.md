# Numpy常用函数

1. array

    生成一个数组， 函数原型：array(p_object, dtype=None, copy=True, order='K', subok=False, ndmin=0)
    其中dtype表示返回的数组中元素是什么类型（尽量不要乱设这个值）

```(python)
    array = np.array([1, 2, 3])
```

![image text](./pictures/p1.png)

2. reshape

3. zeros

4. zeros_like

5. ones

6. ones_like

7. empty

    生成一个3 * 2的数组， 数值随机

```(python)
    empty_array = np.empty(shape=(3, 2))
```

8. empty_like

    函数原型：empty_like(a, dtype=None, order='K', subok=True)， 生成一个和a shape相同的空的多维数组

```(python)
    ones_array = np.ones(shape=(3, 4))
    empty_array = np.empty_like(ones_array)
```

```(python)

```

9. linspace

10. fromfunction

    函数原型fromfunction(functijon, shape, dtype)

11. ravel

    将数组化成1 * n的新数组

12. transpose

13. resize

14. vstack

    vstack(a, b) 将a, b 两个数组的第一个维度合并，  假设a 是 a * b * c, b是 a * b * c, 结果是2a * b * c

15. hstack

    hstack(a, b) 将a, b 两个数组的最后一个维度合并, 假设a 是 a * b * c, b是 a * b * c, 结果是a * b * 2c

16. numpy.matmul

    numpy.matmul(a, b)计算a, b两个矩阵的乘积

17. numpy.vdot

    numpy.vdot(a, b)计算两个向量a, b的内积

18. numpy.linalg.det

    numpy.linalg.det(a)计算矩阵a的行列式

19. numpy.linalg.inv

    numpy.linalg.inv(a)计算矩阵a的逆矩阵

20. numpy.array().T

    numpy.array(a).T 求矩阵a的转置矩阵

21. numpy.dot()
    numpy.dot(a, b) 求矩阵a, b的乘积

22. numpy.diag()
    numpy.diag(a) 根据a生成一个对角矩阵

23. numpy.linalg.eig()
    b = numpy.diag(a) 计算矩阵a的特征值和特征向量， 其中b[0]为特征值， b[1]为特征向量组成的矩阵
