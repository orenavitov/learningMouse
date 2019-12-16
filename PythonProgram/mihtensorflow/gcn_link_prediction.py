'''
@Time: 2019/11/14 16:31
@Author: mih
@Des:

A：邻接矩阵
I: 单位矩阵
D: 度矩阵

A'： A + I
D’： A'的度矩阵
A'': D^(1/2) * A' * D(-1/2)
X: 输入特征向量

整个传播过程： softmax(A'' * ReLU(A'' * X * W_0) * W_1)
'''

"""
model = tf.keras.Sequential([
    tf.keras.layers.Conv2D(32, (3,3), padding='same', activation=tf.nn.relu,
                           input_shape=(28, 28, 1)),
    tf.keras.layers.MaxPooling2D((2, 2), strides=2),
    tf.keras.layers.Conv2D(64, (3,3), padding='same', activation=tf.nn.relu),
    tf.keras.layers.MaxPooling2D((2, 2), strides=2),
    tf.keras.layers.Flatten(),
    tf.keras.layers.Dense(128, activation=tf.nn.relu),
    tf.keras.layers.Dense(10,  activation=tf.nn.softmax)
])
"""

import tensorflow as tf
from tensorflow import keras
import numpy


class MyLayer(keras.layers.Layer):
    def __init__(self, output_dim, **kwargs):
        self.output_dim = output_dim
        super(MyLayer, self).__init__(**kwargs)

    # build(): 创建层的权重
    # input_shape: 权重矩阵的规格
    def build(self, input_shape):
        # output_dim： 等于本层中神经元的数量
        # 权重的规格为： 输入数据的数量N * 神经元的数量L
        shape = tf.TensorShape((input_shape[1], self.output_dim))
        self.kernel = self.add_weight(name='kernel1', shape=shape,
                                      initializer='uniform', trainable=True)
        super(MyLayer, self).build(input_shape)

    # call: 定义前向传播过程
    def call(self, inputs):
        #

        return tf.matmul(inputs, self.kernel)

    def compute_output_shape(self, input_shape):
        shape = tf.TensorShape(input_shape).as_list()
        shape[-1] = self.output_dim
        return tf.TensorShape(shape)

    def get_config(self):
        base_config = super(MyLayer, self).get_config()
        base_config['output_dim'] = self.output_dim
        return base_config

    @classmethod
    def from_config(cls, config):
        return cls(**config)


class model:

    # A: 临界矩阵
    def __init__(self, A, N, m):
        self.A = A
        self.m = m
        self.N = N

    # 训练数据
    def train(self):
        pass


# 模型构建
def struct_model(A, input_shape):

    N = input_shape[0]
    # 特征值数量
    m = input_shape[1]
    # A' = A + I, I 是单位矩阵
    A_I = A + numpy.eye(1);
    # D:度矩阵
    D = numpy.diag([sum(line) for line in A_I])
    _A = numpy.matmul(D ** 1/2, A_I)
    _A = numpy.matmul(A_I, D ** -1/2)
    _A = tf.Variable(_A, dtype=tf.float32)
    input = tf.keras.Input(shape=(100, 1))
    input_A = tf.matmul(_A, input).reshape((100, 1))

    layer1_output = tf.keras.layers.Dense(32, activation='relu')(input_A)
    layer1_output_A = tf.matmul(_A, layer1_output)
    output_y = tf.keras.layers.Dense(16, activation='softmax')(layer1_output_A)


    model = tf.keras.Model(inputs=input, outputs=output_y)
    # model.compile(optimizer=tf.keras.optimizers.Adam(0.001),
    #               loss=tf.keras.losses.categorical_crossentropy,
    #               metrics=['accuracy'])
    # model.fit(train_x, train_y, batch_size=32, epochs=5)
    # model.summary()
    return model



if __name__ == '__main__':
    # 邻接矩阵
    A = numpy.arange(0, 10000, dtype=int).reshape((100, 100))

    model = struct_model(A, (100, 100))
    model.compile(optimaizer = 'adam', loss = 'sparse_categorical_crossentropy', metrics=['accuracy'])

