import tensorflow as tf
import numpy as num
from numpy.random import RandomState

# y_代表准确值， y代表预测值

def placeholderTest():
    sess = tf.Session()
    placeholderNode1 = tf.placeholder(dtype=tf.int32)
    constantNode1 = tf.constant(value=num.random.rand(3, 4), dtype=tf.int32)
    addNode1 = tf.add(placeholderNode1, constantNode1)
    result = sess.run(addNode1, {placeholderNode1: num.random.rand(3, 4)})
    print(result)

def demo():
    # 定义训练数据batch的大小
    batch_size = 8
    #生成一个2*3的矩阵， 矩阵中的元素标准差为1， 随机数种子为1， 均值为0
    w1 = tf.Variable(tf.random_normal([2, 3], stddev=1, seed=1, mean=0))
    w2 = tf.Variable(tf.random_normal([3, 1], stddev=1, seed=1))

    x = tf.placeholder(dtype=tf.float32, shape=(None, 2), name="input-x")
    y_ = tf.placeholder(dtype=tf.float32, shape=(None, 1), name="input-y")

    # 定义神经网络向前传播过程
    a = tf.matmul(x, w1)
    y = tf.matmul(a, w2)

    #定义损失函数和反向传播算法
    cross_entropy = -tf.reduce_mean(y_ * tf.log(tf.clip_by_value(y, 1e-10, 1.0)))
    train_step = tf.train.AdamOptimizer(0.001).minimize(cross_entropy)

    # 产生模拟数据
    rdm = RandomState(1)
    data_size = 128
    X = rdm.rand(data_size, 2)

    # 产生样本标签
    Y = [[int(x1 + x2 < 1)] for x1, x2 in X]

    # 产生一个会话
    with tf.Session() as sess:
        # 初始化所有变量
        init_op = tf.initialize_local_variables()
        sess.run(init_op)

        # 定义训练轮数
        STEPS = 5000
        for i in range(STEPS):
            start = (i * batch_size) % data_size
            end = min(start + batch_size, data_size)
            # 训练神经网络并更新参数
            sess.run(train_step, feed_dict={x: X[start: end], y_: Y[start: end]})

from tensorflow.examples.tutorials.mnist import input_data
class demo_MNIST():

    def __init__(self):
        # 因为数据是28 * 28的图片， 所以输入是每一张图片的每个像素点
        self.INPUT_NODE = 784
        # 输出是0~9这10个数字中的一个
        self.OUTPUT_NODE = 10

        # 在这个DEMO中只有一个隐藏层， 这个隐藏层有500个节点
        self.LAYER1_NODE = 500

        # 每一轮训练的数据量
        self.BATCH_SIZE = 100

        # 基础的学习率
        self.LEARNING_RATE_BASE = 0.8
        # 学习率的衰减率
        self.LEARNING_RATE_DECAY = 0.99
        # 描述模型复杂度的正则化项在损失函数中的系数（lambda）
        self.REGULARIZATION_RATE = 0.0001
        # 训练轮数
        self.TRAINING_STEPS = 30000
        # 滑动平均衰减率
        self.MOVING_AVERAGE_DECAY = 0.99

    # 一个辅助函数， 给定神经网络的输入和所有参数， 计算神经网络的前向传播结果。
    # 在这里， 定义一个使用ReLU激活函数的三层（输入层， 一层隐藏层， 输出）全
    # 连接神经网络。 通过ReLU激活函数去线性化。 这个函数中也支持传入用于计算参数
    # 平均值的类， 这样方便在测试时使用滑动平均模型
    def inference(self, input_tensor, avg_class, weights1, biases1,
                  weights2, biases2):
        # 如果没有提供滑动平均类， 直接使用参数的当前取值
        if avg_class == None:
            layer1 = tf.nn.relu(tf.matmul(input_tensor, weights1) + biases1)
            return tf.matmul(layer1, weights2) + biases2
        else:
            layer1 = tf.nn.relu(tf.matmul(input_tensor, avg_class.average(weights1))
                                + avg_class.average(biases1))
            return tf.matmul(layer1, avg_class.average(weights2)) + avg_class.average(biases2)

    # 模型训练过程
    def train(self, mnist):
        x = tf.placeholder(tf.float32, [None, self.INPUT_NODE], name='x-input')
        y_ = tf.placeholder(tf.float32, [None, self.OUTPUT_NODE], name='y-input')

        weights1 = tf.Variable(
            tf.truncated_normal([self.INPUT_NODE, self.LAYER1_NODE], stddev=0.1)
        )
        biases1 = tf.Variable(
            tf.constant(0.1, shape=[self.LAYER1_NODE])
        )
        weights2 = tf.Variable(
            tf.truncated_normal([self.LAYER1_NODE, self.OUTPUT_NODE], stddev=0.1)
        )
        biases2 = tf.Variable(
            tf.constant(0.1, shape=[self.OUTPUT_NODE])
        )

        y = self.inference(x, None, weights1, biases1, weights2, biases2)

        global_step = tf.Variable(0, trainable=False)

        variable_averages = tf.train.ExponentialMovingAverage(
            self.MOVING_AVERAGE_DECAY, global_step
        )

        # 在神经网络的所有参数上应用滑动平均
        variable_averages_op = variable_averages.apply(tf.trainable_variables())

        average_y = self.inference(x, variable_averages, weights1, biases1, weights2, biases2)

        # 设置损失函数
        # tf.argmax(y_, 1) 返回y_每一行中最大元素的索引
        # labels 是一个bitch * 1的矩阵, 其中的每一个值代表这bitch个训练数据的结果， 如果labels[i] = 1表示这组数据代表1这张图片
        # sparse_softmax_cross_entropy_with_logits 表示先对输出的结果进行softMax， 再进行cross_entropy
        labels = tf.argmax(y_, 1);
        cross_entropy = tf.nn.sparse_softmax_cross_entropy_with_logits(logits=y, labels=labels)

        cross_entropy_mean = tf.reduce_mean(cross_entropy)

        regularizer = tf.contrib.layers.l2_regularizer(self.REGULARIZATION_RATE)

        regularization = regularizer(weights1) + regularizer(weights2)

        loss = cross_entropy_mean + regularization

        # exponential_decay(learning_rate, global_step, decay_steps, decay_rate)
        # learining_rate 表示基本的学习率
        # global_step 代表迭代轮数
        # decay_steps 代表多少轮对学习率进行更新
        # decay_rate 代表衰减大小
        learning_rate = tf.train.exponential_decay(
            self.LEARNING_RATE_BASE,
            global_step,
            mnist.train.num_examples / self.BATCH_SIZE,
            self.LEARNING_RATE_DECAY
        )

        train_step = tf.train.GradientDescentOptimizer(learning_rate).minimize(loss, global_step=global_step)
        # 下面两行代码同tf.group([train_step, variable_averages_op])
        # tf.group对操作进行分组， 保证列表中的操作已经完成
        # tf.control_dependencies 保证列
        # 表中的操作先执行完
        with tf.control_dependencies([train_step, variable_averages_op]):
            train_op = tf.no_op(name="train")

        correct_prediction = tf.equal(tf.argmax(average_y, 1), tf.argmax(y_, 1))

        accuracy = tf.reduce_mean(tf.cast(correct_prediction, tf.float32))

        with tf.Session() as sess:
            tf.initialize_all_variables().run()

            # 准备验证数据
            validate_feed = {
                x: mnist.validation.images,
                y_: mnist.validation.labels
            }

            # 准备测试数据
            test_feed = {
                x: mnist.test.images,
                y_: mnist.test.labels
            }

            for i in range(self.TRAINING_STEPS):
                if i % 1000 == 0:
                    validate_acc = sess.run(accuracy, feed_dict=validate_feed)
                    print("After {0} training step(s), validation accuracy using average model is {1}".format(i, validate_acc))

                xs, ys = mnist.train.next_batch(self.BATCH_SIZE)
                sess.run(train_op, feed_dict={x: xs, y_: ys})

            test_acc = sess.run(accuracy, feed_dict=test_feed)
            print("After {0} training step(s), test accuracy using average model is {1}".format(self.TRAINING_STEPS, test_acc))

    def main(self, argv = None):
        mnist = input_data.read_data_sets(r"E:\train_data", one_hot=True)
        self.train(mnist)

def cross_entry_test():
    a = tf.constant(value=[[1, 2, 3], [1, 2, 3]], dtype=tf.float32)
    b = tf.constant(value=[[1, 2, 3], [1, 3, 2]], dtype=tf.float32)
    c = tf.argmax(b, axis=1)
    cross_entry = tf.nn.sparse_softmax_cross_entropy_with_logits(logits=a, labels=c)
    with tf.Session() as sess:
        print(sess.run(cross_entry))

def MovingAverage_test():
    a = tf.Variable(initial_value=[[1, 2], [3, 4]], dtype=tf.float64)
    step = tf.Variable(0, trainable=False)
    ema = tf.train.ExponentialMovingAverage(0.80, step)
    maintain_average_op = ema.apply([a])
    with tf.Session() as sess:
        init_op = tf.initialize_all_variables()
        sess.run(init_op)
        # 保存影子变量
        sess.run(maintain_average_op)
        # 因为这时a没有发生变化， 所以不会进行更新
        print(sess.run([a, ema.average(a)]))
        # 改变a的值
        sess.run(tf.assign(a, [[5, 6], [7, 8]]))
        # 保存a的影子变量
        sess.run(maintain_average_op)
        a, a_ = sess.run([a, ema.average(a)])
        print("{0}\n{1}".format(a, a_))


def variable_test():
    with tf.variable_scope("layer1", reuse = False):
        a = tf.get_variable(name="weights", initializer=tf.truncated_normal(shape=[3, 2], dtype=tf.float32))

    with tf.variable_scope("layer2", reuse = False):
        a = tf.get_variable(name="weights", initializer=tf.truncated_normal(shape=[3, 2], dtype=tf.float32))
    with tf.variable_scope("", reuse=True):
        layer1_weights = tf.get_variable("layer1/weights", shape=[3, 2])
        layer2_weights = tf.get_variable("layer2/weights", shape=[3, 2])


    with tf.Session() as sess:
        init_op = tf.initialize_all_variables()
        sess.run(init_op)
        print(sess.run(a))
        print(sess.run(layer1_weights))

        print(sess.run(layer2_weights))

# tensorflow 训练结果保存
def save_test():
    # a = tf.Variable(tf.constant(1, dtype=tf.float64), name="a")
    # b = tf.Variable(tf.constant(2, dtype=tf.float64), name="b")
    a = tf.Variable(initial_value=1, dtype=tf.float64, name="v1")
    b = tf.Variable(initial_value=1, dtype=tf.float64, name="v2")
    result = a + b
    init_op = tf.initialize_all_variables()
    # saver用于保存训练模型, 将a保存成名字“v3”, b保存成名字“v4”
    saver = tf.train.Saver({"v3":a, "v4":b})
    with tf.Session() as sess:
        sess.run(init_op)
        saver.save(sess, r"C:\Users\mihao\Desktop\tensorflow_save\model.ckpt")
        print(sess.run(result))

# 使用保存的训练模型
def reload_test():
    a = tf.Variable(initial_value=1, dtype=tf.float64, name="v1")
    b = tf.Variable(initial_value=1, dtype=tf.float64, name="v2")
    result = a + b
    # 这里也可以通过定义变量名的方式指定加载的变量
    save = tf.train.Saver({"v3":a, "v4":b})
    with tf.Session() as sess:
        save.restore(sess, r"C:\Users\mihao\Desktop\tensorflow_save\model.ckpt")
        print(sess.run(result))


# Tensorflow通过元图(MetaGraph)来记录图中节点的信息， MetaGraph由MetaGraphDef Protocol Buffer
# 定义, 以下代码给出了MetaGraphDef类型的定义
# message MetaGraphDef {
#   MetaInfoDef meta_info_def = 1;
#   GraphDef graph_def = 2;
#   SaverDef saver_def = 3;
#   map<string, CollectionDef> collection_def = 4;
#   map<string, SignatureDef> signature_def = 5;
# }
def export_meta_graph_test():
    a = tf.Variable(initial_value=1, dtype=tf.float64, name="v1")
    b = tf.Variable(initial_value=1, dtype=tf.float64, name="v2")
    result = a + b
    # 这里也可以通过定义变量名的方式指定加载的变量
    saver = tf.train.Saver({"v3": a, "v4": b})
    saver.export_meta_graph(r"C:\Users\mihao\Desktop\tensorflow_save\model.ckpt.meda.json", as_text=True)

class new_demo_MNIST:

    def __init__(self):
        self.INPUT_NODE = 28 * 28
        self.OUT_PUT_NODE = 10
        self.LAYER1_NODE = 500
        self.BATCH_SIZE = 100
        self.LEARNING_RATE_BASE = 0.8
        self.LEARNING_RATE_DECAY = 0.99
        self.REGULARAZTION =_RATE = 0.0001
        self.TRAINING_STEPS = 30000
        self.MOVING_AVERAGE_DECAY = 0.99
        # 训练模型的保存路径
        self.MODEL_SAVE_PATH = ""
        self.MODEL_NAME = ""
        # 每10秒加载一次最新的模型， 并在测试数据上测试最新模型的正确率
        self.EVAL_INTERVAL_SECS = 10

    def get_weight_variable(self, shape, regularizer):
        weights = tf.get_variable("weights", shape=shape,
                                  initializer=tf.truncated_normal_initializer(stddev=0.1))

        if regularizer != None:
            tf.add_to_collection("losses", regularizer(weights))
        return weights

    def inference(self, input_tensor, regularizer):

        with tf.variable_scope("layer1"):
            weights = self.get_weight_variable([self.INPUT_NODE, self.LAYER1_NODE], regularizer)
            biases = tf.get_variable("biases", [self.LAYER1_NODE],
                                 initializer=tf.constant_initializer(0.0))
            layer1 = tf.nn.relu(tf.matmul(input_tensor, weights) + biases)

        with tf.variable_scope("layer2"):
            weights = self.get_weight_variable([self.LAYER1_NODE, self.OUT_PUT_NODE], regularizer)
            biases = tf.get_variable("biases", [self.OUT_PUT_NODE],
                                     initializer=tf.constant_initializer(0.0))
            layer2 = tf.matmul(layer1, weights) + biases

        return layer2

    def train(self, mnist):
        x = tf.placeholder(dtype=tf.float64, shape=[None, self.INPUT_NODE], name="x-input")
        y_ = tf.placeholder(dtype=tf.float32, shape=[None, self.OUT_PUT_NODE], name="y-input")

        regularizer = tf.contrib.layers.l2_regularizer(self.REGULARAZTION)
        y = self.inference(x, regularizer)
        global_step = tf.Variable(0, trainable=False)

        variable_averages = tf.train.ExponentialMovingAverage(self.MOVING_AVERAGE_DECAY, global_step)

        variables_averages_op = variable_averages.apply(tf.trainable_variables())

        cross_entropy = tf.nn.sparse_softmax_cross_entropy_with_logits(y, tf.argmax(y_, 1))

        cross_entropy_mean = tf.reduce_mean(cross_entry_test())
        #
        #
        loss = cross_entropy_mean + tf.add_n(tf.get_collection("losses"))

        learning_rate = tf.train.exponential_decay(
            self.LEARNING_RATE_BASE,
            global_step,
            mnist.train.num_examples / self.BATCH_SIZE,
            self.LEARNING_RATE_DECAY
        )
        train_step = tf.train.GradientDescentOptimizer(learning_rate).minimize(loss, global_step = global_step)

        train_group = tf.group([train_step, variables_averages_op])

        saver = tf.train.Saver()

        with tf.Session() as sess:
            sess.run(tf.initialize_all_variables())

            for i in range(self.TRAINING_STEPS):
                xs, ys = mnist.train.next_batch(self.BATCH_SIZE)
                _, loss_value, step = sess.run([train_group, loss, global_step]
                                                , feed_dict={x: xs, y_:ys})
                if i % 1000 == 0:
                    print('''After {0} training step(s), loss on training
                           batch is {1}'''.format(step, loss_value))

                    saver.save(sess, self.MODEL_SAVE_PATH + self.MODEL_NAME, global_step = global_step)
    def main(self):
        mnist = input_data.read_data_sets("")
        self.train(mnist)

    def evaluate(self, mnist):

        with tf.Graph().as_default() as g:
            x = tf.placeholder(dtype=tf.float32, shape = [None, self.INPUT_NODE]
                               , name="x-input")
            y_ = tf.placeholder(dtype=tf.float32, shape = [None, self.OUT_PUT_NODE], name="y-input")
            validate_feed = {x : mnist.validation.images, y_ : mnist.validation.labels}
            y = self.inference(x, None)
            correct_prediction = tf.equal(tf.argmax(y, 1), tf.argmax(y_, 1))
            accuracy = tf.reduce_mean(tf.cast(correct_prediction, tf.float32))

            variable_averages = tf.train.ExponentialMovingAverage(self.MOVING_AVERAGE_DECAY)
            #
            variables_to_restore = variable_averages.variables_to_restore()
            saver = tf.train.Saver(variables_to_restore)
if __name__ == '__main__':
    # save_test()
    MovingAverage_test()