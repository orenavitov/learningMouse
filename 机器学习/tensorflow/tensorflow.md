# Tensorflow
    https://tensorflow.google.cn/tutorials?hl=zh-CN
    Tensorflow中将所有的计算（Operation）、数据（常量， 变量，张量）都抽象成节点， 执行一个操作需要指定一个计算图（graph）和一个会话（session）, 一个计算图中包含多种计算资源（operation, constant, varible, tensor）, tensorflow指定计算流时是需要正向指定， 执行计算流时是反向执行

## 常用函数

import tensorflow as tf
import numpy as num

1. constant

    Tensorflow常量节点， 函数形式constant(value=[1, 2], dtype=tf.float32, shape=(1,2), name="testconstant")

2. placeholder

    占位符， 可理解为某一个操作中的形参， 必须在run某一个操作的时候才能进行初始化;

    ```(python)
    sess = tf.Session()
    placeholderNode1 = tf.placeholder(dtype=tf.int32)
    constantNode1 = tf.constant(value=num.random.rand(3, 4), dtype=tf.int32)
    addNode1 = tf.add(placeholderNode1, constantNode1)
    result = sess.run(addNode1, {placeholderNode1: num.random.rand(3, 4)})
    ```

3. graph

4. variable

    ```(python)
    # 先声明一个变量, 声明时必须指定初始值
    v = tf.Variable([1,2,3])
    # 变脸必须显式初始化
    sess.run(v.initializer)
    # 也可以使用tf.global_variables_initializer()直接初始化所有变量
    ```

5. concat

将多个张量在某个维度上进行合并
```(python)

```

6. stack

```(python)

```
7. unstack

```(python)

```

8. gather

用一个一维数组， 将张量中对应索引的向量提取出来
```(python)
import tensorflow as tf
 
a = tf.Variable([[1,2,3,4,5], [6,7,8,9,10], [11,12,13,14,15]])
index_a = tf.Variable([0,2])
 
b = tf.Variable([1,2,3,4,5,6,7,8,9,10])
index_b = tf.Variable([2,4,6,8])
 
with tf.Session() as sess:
    sess.run(tf.global_variables_initializer())
    print(sess.run(tf.gather(a, index_a)))
    print(sess.run(tf.gather(b, index_b)))
 
#  [[ 1  2  3  4  5]
#   [11 12 13 14 15]]
 
#  [3 5 7 9]
```

9. norm

计算范数

10. TensorArray
相当于一个链表， 可以不断的向里添加张量

11. while_loop

相当于以下python代码：
```(python)
while(a < n):
  a = a + 1

```

```(python)
import tensorflow as tf 
i = tf.get_variable("ii", dtype=tf.int32, shape=[], initializer=tf.ones_initializer())
n = tf.constant(10)
# 用来做判断
def cond(a, n):
    return  a< n
# 循环体中的执行代码
def body(a, n):
    a = a + 1
    return a, n

a, n = tf.while_loop(cond, body, [a, n])
with tf.Session() as sess:
    tf.global_variables_initializer().run()
    res = sess.run([a, n])
    print(res)
```

12. reduce_sum 

求和

```(python)
x = tf.constant([[1, 1, 1], [1, 1, 1]])
tf.reduce_sum(x, 0)  # 对 tensor 的 0 级进行求和，[1,1,1] + [1,1,1] =  [2, 2, 2]
tf.reduce_sum(x, 1)  # 对 tensor 的 1 级进行仇和，[1+1+1, 1+1+1] = [3, 3]
tf.reduce_sum(x, 1, keep_dims=True)  # 对第 1 级进行求和，但不降维, [[3], [3]]
tf.reduce_sum(x, [0, 1])  # 0 级和 1级都要求和，6
tf.reduce_sum(x)  # 因为 x 只有 2 级，所以结果同上一个，6
```

13. unsorted_segment_sum

分段求和

```(python)
a = np.arange(1,10).reshape(3,3)
print(a)
print('----------')
# segment_ids = [0, 1, 0]表示a的第一个元素、第三个元素的求和结果用与结果的第一个元素， a的第二个元素用作结果的第二个元素
print((sess.run(tf.unsorted_segment_sum(data=a,segment_ids=[0,1,0],num_segments=2)))

#[[1 2 3]
# [4 5 6]
# [7 8 9]]
#----------
#[[ 8 10 12]
# [ 4  5  6]]

```

14. cumsum
累计求和：沿着axis指定的轴， 结果的i个元素是输入张量的[:i]的和

```(python)
a = [[1 ,2, 3], 
     [4, 5, 6], 
     [7, 8, 9]]
# axis=0
sum1 = tf.cumsum(a, axis=0)
 
# axis=1
sum2 = tf.cumsum(a, axis=1)
 
# exclusive=False 表示初始的第一个求和结果sum3[0] = a[0]
sum3 = tf.cumsum(a, exclusive=False)
 
# exclusive=True 表示初始的第一个求和结果sum3[0]用0初始化
sum4 = tf.cumsum(a, exclusive=True)
 
# reverse=True
sum5 = tf.cumsum(a, reverse=True)
 
# exclusive=True, reverse=True
sum6 = tf.cumsum(a, exclusive=True, reverse=True)


sum1 = [[ 1,  2,  3],
        [ 5,  7,  9],
        [12, 15, 18]]

sum2 = [[ 1,  3,  6],
        [ 4,  9, 15],
        [ 7, 15, 24]]

sum3 = [[ 1,  2,  3],
        [ 5,  7,  9],
        [12, 15, 18]]

sum4= [[0, 0, 0],
       [1, 2, 3],
       [5, 7, 9]]

sum5 = [[12, 15, 18],
        [11, 13, 15],
        [ 7,  8,  9]]

sum6 = [[11, 13, 15],
        [ 7,  8,  9],
        [ 0,  0,  0]]

```

15. cast
类型转化

```(python)
a = tf.Variable([1.0,1.3,2.1,3.41,4.51])
# 表示将a > 3的结果以bool的形式输出
b = tf.cast(a>3,dtype=tf.bool)
# 表示将a > 3的结果以int8的形式输出
c = tf.cast(a>3,dtype=tf.int8)
# 表示将a > 3的结果以float32的形式输出
e = tf.cast(a<2,dtype=tf.float32)
# 表示将a以bool的形式输出
d = tf.cast(a,dtype=tf.int8)

sess = tf.Session()
sess.run(tf.initialize_all_variables())
print(sess.run(b))
print(sess.run(c))
print(sess.run(e))
print(sess.run(d))

#[False False False  True  True]
#[0 0 0 1 1]
#[1. 1. 0. 0. 0.]
#[1 1 2 3 4]

```

16. expand_dims
增加一个维度
```(python)
a = tf.constant([[1, 2], [3, 4], [5, 6]], dtype=tf.float32)
a0 = tf.expand_dims(a, 0)
a1 = tf.expand_dims(a, 1)
a2 = tf.expand_dims(a, 2)
with tf.Session() as sess:
    print(sess.run(a))
    print('------------')
    print(sess.run(a0))
    print('------------')
    print(sess.run(a1))
    print('------------')
    print(sess.run(a2))


# 结果
[[1. 2.]
 [3. 4.]
 [5. 6.]]
------------
[[[1. 2.]
  [3. 4.]
  [5. 6.]]]
------------
[[[1. 2.]]
 
 [[3. 4.]]
 
 [[5. 6.]]]
------------
[[[1.]
  [2.]]
 
 [[3.]
  [4.]]
 
 [[5.]
  [6.]]]
```

## 持久化文件格式

```(json)

meta_info_def {
  stripped_op_list {
    op {
      name: "Add"
      input_arg {
        name: "x"
        type_attr: "T"
      }
      input_arg {
        name: "y"
        type_attr: "T"
      }
      output_arg {
        name: "z"
        type_attr: "T"
      }
      attr {
        name: "T"
        type: "type"
        allowed_values {
          list {
            type: DT_HALF
            type: DT_FLOAT
            type: DT_DOUBLE
            type: DT_UINT8
            type: DT_INT8
            type: DT_INT16
            type: DT_INT32
            type: DT_INT64
            type: DT_COMPLEX64
            type: DT_COMPLEX128
            type: DT_STRING
          }
        }
      }
    }
    op {
      name: "Assign"
      input_arg {
        name: "ref"
        type_attr: "T"
        is_ref: true
      }
      input_arg {
        name: "value"
        type_attr: "T"
      }
      output_arg {
        name: "output_ref"
        type_attr: "T"
        is_ref: true
      }
      attr {
        name: "T"
        type: "type"
      }
      attr {
        name: "validate_shape"
        type: "bool"
        default_value {
          b: true
        }
      }
      attr {
        name: "use_locking"
        type: "bool"
        default_value {
          b: true
        }
      }
      allows_uninitialized_input: true
    }
    op {
      name: "Const"
      output_arg {
        name: "output"
        type_attr: "dtype"
      }
      attr {
        name: "value"
        type: "tensor"
      }
      attr {
        name: "dtype"
        type: "type"
      }
    }
    op {
      name: "Identity"
      input_arg {
        name: "input"
        type_attr: "T"
      }
      output_arg {
        name: "output"
        type_attr: "T"
      }
      attr {
        name: "T"
        type: "type"
      }
    }
    op {
      name: "NoOp"
    }
    op {
      name: "RestoreV2"
      input_arg {
        name: "prefix"
        type: DT_STRING
      }
      input_arg {
        name: "tensor_names"
        type: DT_STRING
      }
      input_arg {
        name: "shape_and_slices"
        type: DT_STRING
      }
      output_arg {
        name: "tensors"
        type_list_attr: "dtypes"
      }
      attr {
        name: "dtypes"
        type: "list(type)"
        has_minimum: true
        minimum: 1
      }
      is_stateful: true
    }
    op {
      name: "SaveV2"
      input_arg {
        name: "prefix"
        type: DT_STRING
      }
      input_arg {
        name: "tensor_names"
        type: DT_STRING
      }
      input_arg {
        name: "shape_and_slices"
        type: DT_STRING
      }
      input_arg {
        name: "tensors"
        type_list_attr: "dtypes"
      }
      attr {
        name: "dtypes"
        type: "list(type)"
        has_minimum: true
        minimum: 1
      }
      is_stateful: true
    }
    op {
      name: "VariableV2"
      output_arg {
        name: "ref"
        type_attr: "dtype"
        is_ref: true
      }
      attr {
        name: "shape"
        type: "shape"
      }
      attr {
        name: "dtype"
        type: "type"
      }
      attr {
        name: "container"
        type: "string"
        default_value {
          s: ""
        }
      }
      attr {
        name: "shared_name"
        type: "string"
        default_value {
          s: ""
        }
      }
      is_stateful: true
    }
  }
  tensorflow_version: "1.3.0-rc0"
  tensorflow_git_version: "b\'unknown\'"
}
graph_def {
  node {
    name: "v1/initial_value"
    op: "Const"
    attr {
      key: "_output_shapes"
      value {
        list {
          shape {
          }
        }
      }
    }
    attr {
      key: "dtype"
      value {
        type: DT_DOUBLE
      }
    }
    attr {
      key: "value"
      value {
        tensor {
          dtype: DT_DOUBLE
          tensor_shape {
          }
          double_val: 1.0
        }
      }
    }
  }
  node {
    name: "v1"
    op: "VariableV2"
    attr {
      key: "_output_shapes"
      value {
        list {
          shape {
          }
        }
      }
    }
    attr {
      key: "container"
      value {
        s: ""
      }
    }
    attr {
      key: "dtype"
      value {
        type: DT_DOUBLE
      }
    }
    attr {
      key: "shape"
      value {
        shape {
        }
      }
    }
    attr {
      key: "shared_name"
      value {
        s: ""
      }
    }
  }
  node {
    name: "v1/Assign"
    op: "Assign"
    input: "v1"
    input: "v1/initial_value"
    attr {
      key: "T"
      value {
        type: DT_DOUBLE
      }
    }
    attr {
      key: "_class"
      value {
        list {
          s: "loc:@v1"
        }
      }
    }
    attr {
      key: "_output_shapes"
      value {
        list {
          shape {
          }
        }
      }
    }
    attr {
      key: "use_locking"
      value {
        b: true
      }
    }
    attr {
      key: "validate_shape"
      value {
        b: true
      }
    }
  }
  node {
    name: "v1/read"
    op: "Identity"
    input: "v1"
    attr {
      key: "T"
      value {
        type: DT_DOUBLE
      }
    }
    attr {
      key: "_class"
      value {
        list {
          s: "loc:@v1"
        }
      }
    }
    attr {
      key: "_output_shapes"
      value {
        list {
          shape {
          }
        }
      }
    }
  }
  node {
    name: "v2/initial_value"
    op: "Const"
    attr {
      key: "_output_shapes"
      value {
        list {
          shape {
          }
        }
      }
    }
    attr {
      key: "dtype"
      value {
        type: DT_DOUBLE
      }
    }
    attr {
      key: "value"
      value {
        tensor {
          dtype: DT_DOUBLE
          tensor_shape {
          }
          double_val: 1.0
        }
      }
    }
  }
  node {
    name: "v2"
    op: "VariableV2"
    attr {
      key: "_output_shapes"
      value {
        list {
          shape {
          }
        }
      }
    }
    attr {
      key: "container"
      value {
        s: ""
      }
    }
    attr {
      key: "dtype"
      value {
        type: DT_DOUBLE
      }
    }
    attr {
      key: "shape"
      value {
        shape {
        }
      }
    }
    attr {
      key: "shared_name"
      value {
        s: ""
      }
    }
  }
  node {
    name: "v2/Assign"
    op: "Assign"
    input: "v2"
    input: "v2/initial_value"
    attr {
      key: "T"
      value {
        type: DT_DOUBLE
      }
    }
    attr {
      key: "_class"
      value {
        list {
          s: "loc:@v2"
        }
      }
    }
    attr {
      key: "_output_shapes"
      value {
        list {
          shape {
          }
        }
      }
    }
    attr {
      key: "use_locking"
      value {
        b: true
      }
    }
    attr {
      key: "validate_shape"
      value {
        b: true
      }
    }
  }
  node {
    name: "v2/read"
    op: "Identity"
    input: "v2"
    attr {
      key: "T"
      value {
        type: DT_DOUBLE
      }
    }
    attr {
      key: "_class"
      value {
        list {
          s: "loc:@v2"
        }
      }
    }
    attr {
      key: "_output_shapes"
      value {
        list {
          shape {
          }
        }
      }
    }
  }
  node {
    name: "add"
    op: "Add"
    input: "v1/read"
    input: "v2/read"
    attr {
      key: "T"
      value {
        type: DT_DOUBLE
      }
    }
    attr {
      key: "_output_shapes"
      value {
        list {
          shape {
          }
        }
      }
    }
  }
  node {
    name: "save/Const"
    op: "Const"
    attr {
      key: "_output_shapes"
      value {
        list {
          shape {
          }
        }
      }
    }
    attr {
      key: "dtype"
      value {
        type: DT_STRING
      }
    }
    attr {
      key: "value"
      value {
        tensor {
          dtype: DT_STRING
          tensor_shape {
          }
          string_val: "model"
        }
      }
    }
  }
  node {
    name: "save/SaveV2/tensor_names"
    op: "Const"
    attr {
      key: "_output_shapes"
      value {
        list {
          shape {
            dim {
              size: 2
            }
          }
        }
      }
    }
    attr {
      key: "dtype"
      value {
        type: DT_STRING
      }
    }
    attr {
      key: "value"
      value {
        tensor {
          dtype: DT_STRING
          tensor_shape {
            dim {
              size: 2
            }
          }
          string_val: "v3"
          string_val: "v4"
        }
      }
    }
  }
  node {
    name: "save/SaveV2/shape_and_slices"
    op: "Const"
    attr {
      key: "_output_shapes"
      value {
        list {
          shape {
            dim {
              size: 2
            }
          }
        }
      }
    }
    attr {
      key: "dtype"
      value {
        type: DT_STRING
      }
    }
    attr {
      key: "value"
      value {
        tensor {
          dtype: DT_STRING
          tensor_shape {
            dim {
              size: 2
            }
          }
          string_val: ""
          string_val: ""
        }
      }
    }
  }
  node {
    name: "save/SaveV2"
    op: "SaveV2"
    input: "save/Const"
    input: "save/SaveV2/tensor_names"
    input: "save/SaveV2/shape_and_slices"
    input: "v1"
    input: "v2"
    attr {
      key: "dtypes"
      value {
        list {
          type: DT_DOUBLE
          type: DT_DOUBLE
        }
      }
    }
  }
  node {
    name: "save/control_dependency"
    op: "Identity"
    input: "save/Const"
    input: "^save/SaveV2"
    attr {
      key: "T"
      value {
        type: DT_STRING
      }
    }
    attr {
      key: "_class"
      value {
        list {
          s: "loc:@save/Const"
        }
      }
    }
    attr {
      key: "_output_shapes"
      value {
        list {
          shape {
          }
        }
      }
    }
  }
  node {
    name: "save/RestoreV2/tensor_names"
    op: "Const"
    attr {
      key: "_output_shapes"
      value {
        list {
          shape {
            dim {
              size: 1
            }
          }
        }
      }
    }
    attr {
      key: "dtype"
      value {
        type: DT_STRING
      }
    }
    attr {
      key: "value"
      value {
        tensor {
          dtype: DT_STRING
          tensor_shape {
            dim {
              size: 1
            }
          }
          string_val: "v3"
        }
      }
    }
  }
  node {
    name: "save/RestoreV2/shape_and_slices"
    op: "Const"
    attr {
      key: "_output_shapes"
      value {
        list {
          shape {
            dim {
              size: 1
            }
          }
        }
      }
    }
    attr {
      key: "dtype"
      value {
        type: DT_STRING
      }
    }
    attr {
      key: "value"
      value {
        tensor {
          dtype: DT_STRING
          tensor_shape {
            dim {
              size: 1
            }
          }
          string_val: ""
        }
      }
    }
  }
  node {
    name: "save/RestoreV2"
    op: "RestoreV2"
    input: "save/Const"
    input: "save/RestoreV2/tensor_names"
    input: "save/RestoreV2/shape_and_slices"
    attr {
      key: "_output_shapes"
      value {
        list {
          shape {
            unknown_rank: true
          }
        }
      }
    }
    attr {
      key: "dtypes"
      value {
        list {
          type: DT_DOUBLE
        }
      }
    }
  }
  node {
    name: "save/Assign"
    op: "Assign"
    input: "v1"
    input: "save/RestoreV2"
    attr {
      key: "T"
      value {
        type: DT_DOUBLE
      }
    }
    attr {
      key: "_class"
      value {
        list {
          s: "loc:@v1"
        }
      }
    }
    attr {
      key: "_output_shapes"
      value {
        list {
          shape {
          }
        }
      }
    }
    attr {
      key: "use_locking"
      value {
        b: true
      }
    }
    attr {
      key: "validate_shape"
      value {
        b: true
      }
    }
  }
  node {
    name: "save/RestoreV2_1/tensor_names"
    op: "Const"
    attr {
      key: "_output_shapes"
      value {
        list {
          shape {
            dim {
              size: 1
            }
          }
        }
      }
    }
    attr {
      key: "dtype"
      value {
        type: DT_STRING
      }
    }
    attr {
      key: "value"
      value {
        tensor {
          dtype: DT_STRING
          tensor_shape {
            dim {
              size: 1
            }
          }
          string_val: "v4"
        }
      }
    }
  }
  node {
    name: "save/RestoreV2_1/shape_and_slices"
    op: "Const"
    attr {
      key: "_output_shapes"
      value {
        list {
          shape {
            dim {
              size: 1
            }
          }
        }
      }
    }
    attr {
      key: "dtype"
      value {
        type: DT_STRING
      }
    }
    attr {
      key: "value"
      value {
        tensor {
          dtype: DT_STRING
          tensor_shape {
            dim {
              size: 1
            }
          }
          string_val: ""
        }
      }
    }
  }
  node {
    name: "save/RestoreV2_1"
    op: "RestoreV2"
    input: "save/Const"
    input: "save/RestoreV2_1/tensor_names"
    input: "save/RestoreV2_1/shape_and_slices"
    attr {
      key: "_output_shapes"
      value {
        list {
          shape {
            unknown_rank: true
          }
        }
      }
    }
    attr {
      key: "dtypes"
      value {
        list {
          type: DT_DOUBLE
        }
      }
    }
  }
  node {
    name: "save/Assign_1"
    op: "Assign"
    input: "v2"
    input: "save/RestoreV2_1"
    attr {
      key: "T"
      value {
        type: DT_DOUBLE
      }
    }
    attr {
      key: "_class"
      value {
        list {
          s: "loc:@v2"
        }
      }
    }
    attr {
      key: "_output_shapes"
      value {
        list {
          shape {
          }
        }
      }
    }
    attr {
      key: "use_locking"
      value {
        b: true
      }
    }
    attr {
      key: "validate_shape"
      value {
        b: true
      }
    }
  }
  node {
    name: "save/restore_all"
    op: "NoOp"
    input: "^save/Assign"
    input: "^save/Assign_1"
  }
  versions {
    producer: 24
  }
}
saver_def {
  filename_tensor_name: "save/Const:0"
  save_tensor_name: "save/control_dependency:0"
  restore_op_name: "save/restore_all"
  max_to_keep: 5
  keep_checkpoint_every_n_hours: 10000.0
  version: V2
}
collection_def {
  key: "trainable_variables"
  value {
    bytes_list {
      value: "\n\004v1:0\022\tv1/Assign\032\tv1/read:0"
      value: "\n\004v2:0\022\tv2/Assign\032\tv2/read:0"
    }
  }
}
collection_def {
  key: "variables"
  value {
    bytes_list {
      value: "\n\004v1:0\022\tv1/Assign\032\tv1/read:0"
      value: "\n\004v2:0\022\tv2/Assign\032\tv2/read:0"
    }
  }
}

```
