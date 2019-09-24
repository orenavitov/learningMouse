* memoryview

```
# memoryview其实就是在不复制内存的情况下操作一块内存， 如下以无符号整型的方式查看numbers数组
    numbers = array.array('h', [-2, 1, -1, 0, 3])
    memv = memoryview(numbers)
    memv_oct = memv.cast('B')
```

* ord & chr

```
# 返回字符a的Ascaii码
ord('a')
# 返回Ascaii码对应的字符
chr(48)
```


