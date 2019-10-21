# Tensorflow

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

## 持久化文件格式

```(json)

meta_info_def {
  // stripped_op_list记录计算图所有的操作信息
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
