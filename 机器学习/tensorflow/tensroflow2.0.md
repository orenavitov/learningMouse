# tensorflow 2.0

https://tensorflow.google.cn/tutorials/quickstart/beginner

## Build the model

### set up the layers

```
model = keras.Sequential([
    keras.layers.Flatten(input_shape=(28, 28)),
    keras.layers.Dense(128, activation='relu'),
    keras.layers.Dense(10, activation='softmax')
])
```

Flatten: 将输入28 * 28 的数组转换成 1 * 784的一维数组
Dense： 全连接层， Dense(128, activation='relu') 包含128个神经元， 激活函数是Relu, 参数kernel_initializer, bias_initialzer表示核和偏差的初始化方案， kernel_regular和bias_regular表示核和偏差的正则化方案。

层实例是可以调用的：

```
input_x = tf.keras.Input(shape=(72,))
hidden1 = layers.Dense(32, activation='relu')(input_x)
hidden2 = layers.Dense(16, activation='relu')(hidden1)
pred = layers.Dense(10, activation='softmax')(hidden2)

model = tf.keras.Model(inputs=input_x, outputs=pred)
```

使用Model创建网络

```
inputs = tf.keras.Input(shape=(784,), name='img')
h1 = layers.Dense(32, activation='relu')(inputs)
h2 = layers.Dense(32, activation='relu')(h1)
outputs = layers.Dense(10, activation='softmax')(h2)
model = tf.keras.Model(inputs=inputs, outputs=outputs, name='mnist model')
```

以类的方式定义：
(是不是有问题？？？？？？？？？)
```
class MyModel(tf.keras.Model):
    def __init__(self, num_classes=10):
        super(MyModel, self).__init__(name='my_model')
        self.num_classes = num_classes
        self.layer1 = layers.Dense(32, activation='relu')
        self.layer2 = layers.Dense(num_classes, activation='softmax')
    def call(self, inputs):
        h1 = self.layer1(inputs)
        out = self.layer2(h1)
        return out

    def compute_output_shape(self, input_shape):
        shape = tf.TensorShape(input_shape).as_list()
        shape[-1] = self.num_classes
        return tf.TensorShape(shape)
```



### Compile the model

```
model.compile(optimizer='adam',
              loss='sparse_categorical_crossentropy',
              metrics=['accuracy'])
```
optimizer: 模型参数更新的方法
loss: 损失函数
metrics: 

### train the model

```
model.fit(train_images, train_labels, epochs=10)
```

输入numpy数据

```
train_x = np.random.random((1000, 72))
train_y = np.random.random((1000, 10))

val_x = np.random.random((200, 72))
val_y = np.random.random((200, 10))

model.fit(train_x, train_y, epochs=10, batch_size=100,
          validation_data=(val_x, val_y))
```
batch_size: 指定每个epoch中每个batch的大小

输入data数据

```
dataset = tf.data.Dataset.from_tensor_slices((train_x, train_y))
dataset = dataset.batch(32)
dataset = dataset.repeat()
val_dataset = tf.data.Dataset.from_tensor_slices((val_x, val_y))
val_dataset = val_dataset.batch(32)
val_dataset = val_dataset.repeat()

model.fit(dataset, epochs=10, steps_per_epoch=30,
          validation_data=val_dataset, validation_steps=3)
```

```
fit(x=None, y=None, batch_size=None, epochs=1, verbose=1, callbacks=None, 
validation_split=0.0, validation_data=None, shuffle=True, class_weight=None, 
sample_weight=None, initial_epoch=0, steps_per_epoch=None, validation_steps=None, validation_freq=1)
x：输入数据。如果模型只有一个输入，那么x的类型是numpy array，如果模型有多个输入，那么x的类型应当为list，list的元素是对应于各个输入的numpy array
y：标签，numpy array
batch_size：整数，指定进行梯度下降时每个batch包含的样本数。训练时一个batch的样本会被计算一次梯度下降，使目标函数优化一步。
epochs：整数，训练终止时的epoch值，训练将在达到该epoch值时停止，当没有设置initial_epoch时，它就是训练的总轮数，否则训练的总轮数为epochs - inital_epoch
verbose：日志显示，0为不在标准输出流输出日志信息，1为输出进度条记录，2为每个epoch输出一行记录
callbacks：list，其中的元素是keras.callbacks.Callback的对象。这个list中的回调函数将会在训练过程中的适当时机被调用，参考回调函数
validation_split：0~1之间的浮点数，用来指定训练集的一定比例数据作为验证集。验证集将不参与训练，并在每个epoch结束后测试的模型的指标，如损失函数、精确度等。注意，validation_split的划分在shuffle之前，因此如果你的数据本身是有序的，需要先手工打乱再指定validation_split，否则可能会出现验证集样本不均匀。
validation_data：形式为（X，y）的tuple，是指定的验证集。此参数将覆盖validation_spilt。
shuffle：布尔值或字符串，一般为布尔值，表示是否在训练过程中随机打乱输入样本的顺序。若为字符串“batch”，则是用来处理HDF5数据的特殊情况，它将在batch内部将数据打乱。
class_weight：字典，将不同的类别映射为不同的权值，该参数用来在训练过程中调整损失函数（只能用于训练）
sample_weight：权值的numpy array，用于在训练时调整损失函数（仅用于训练）。可以传递一个1D的与样本等长的向量用于对样本进行1对1的加权，或者在面对时序数据时，传递一个的形式为（samples，sequence_length）的矩阵来为每个时间步上的样本赋不同的权。这种情况下请确定在编译模型时添加了sample_weight_mode=’temporal’。
initial_epoch: 从该参数指定的epoch开始训练，在继续之前的训练时有用。
fit函数返回一个History的对象，其History.history属性记录了损失函数和其他指标的数值随epoch变化的情况，如果有验证集的话，也包含了验证集的这些指标变化情况

```


### Evaluate accuracy

评估与预测
```
test_x = np.random.random((1000, 72))
test_y = np.random.random((1000, 10))
model.evaluate(test_x, test_y, batch_size=32)
test_data = tf.data.Dataset.from_tensor_slices((test_x, test_y))
test_data = test_data.batch(32).repeat()
model.evaluate(test_data, steps=30)
# predict
result = model.predict(test_x, batch_size=32)
```

### Make predictions

```
predictions = model.predict(test_images)
```
predictions: 一个数组， 其中每一个值同样是一个数组， 这个数组代表样本属于每个类别的概率;

```
np.argmax(predictions[0])
```
使用argmax得到第一个样本属于哪个类别概率最大；

### 保存和加载权重

```
model.save_weights('./weights/model')
model.load_weights('./weights/model')
```

### 保存和加载网络结构

```
import json
import pprint
json_str = model.to_json()
pprint.pprint(json.loads(json_str))
fresh_model = tf.keras.models.model_from_json(json_str)
# 保持为yaml格式, 需要提前安装pyyaml

yaml_str = model.to_yaml()
print(yaml_str)
fresh_model = tf.keras.models.model_from_yaml(yaml_str)
```

### 保存整个模型

```
model.save('all_model.h5')
model = tf.keras.models.load_model('all_model.h5')
```

### keras 可视化网络

```
encode_input = keras.Input(shape=(28, 28, 1), name='src_img')
    h1 = layers.Conv2D(16, 3, activation='relu')(encode_input)
    h1 = layers.Conv2D(32, 3, activation='relu')(h1)
    h1 = layers.MaxPool2D(3)(h1)
    h1 = layers.Conv2D(32, 3, activation='relu')(h1)
    h1 = layers.Conv2D(16, 3, activation='relu')(h1)
    encode_output = layers.GlobalMaxPool2D()(h1)

    encode_model = keras.Model(inputs=encode_input, outputs=encode_output, name='encoder')

    encode_model.summary()

    decode_input = keras.Input(shape=(16,), name='encoded_img')
    h2 = layers.Reshape((4, 4, 1))(decode_input)
    h2 = layers.Conv2DTranspose(16, 3, activation='relu')(h2)
    h2 = layers.Conv2DTranspose(32, 3, activation='relu')(h2)
    h2 = layers.UpSampling2D(3)(h2)
    h2 = layers.Conv2DTranspose(16, 3, activation='relu')(h2)
    decode_output = layers.Conv2DTranspose(1, 3, activation='relu')(h2)
    decode_model = keras.Model(inputs=decode_input, outputs=decode_output, name='decoder')
    decode_model.summary()

    autoencoder_input = keras.Input(shape=(28, 28, 1), name='img')
    h3 = encode_model(autoencoder_input)
    autoencoder_output = decode_model(h3)
    autoencoder = keras.Model(inputs=autoencoder_input, outputs=autoencoder_output,
                              name='autoencoder')
    autoencoder.summary()
    keras.utils.plot_model(autoencoder, r'C:\\Users\\mih\\Desktop\\NN_Graph\\autoencoder_model.png')
    keras.utils.plot_model(autoencoder, r'C:\\Users\\mih\\Desktop\\NN_Graph\\autoencoder_info.png', show_shapes=True)
```

encode_model的模型：

Model: "encoder"
_________________________________________________________________
Layer (type)                 Output Shape              Param    
=================================================================
src_img (InputLayer)         [(None, 28, 28, 1)]       0         
_________________________________________________________________
conv2d (Conv2D)              (None, 26, 26, 16)        160       
_________________________________________________________________
conv2d_1 (Conv2D)            (None, 24, 24, 32)        4640      
_________________________________________________________________
max_pooling2d (MaxPooling2D) (None, 8, 8, 32)          0         
_________________________________________________________________
conv2d_2 (Conv2D)            (None, 6, 6, 32)          9248      
_________________________________________________________________
conv2d_3 (Conv2D)            (None, 4, 4, 16)          4624      
_________________________________________________________________
global_max_pooling2d (Global (None, 16)                0         
=================================================================

src_img: 输入层， (None, 28, 28, 1)因为没有规定数据量， 所以第一个维度是None， 输入的每一个数据是一个28 * 28 * 1的数组；
第一个卷积层： layers.Conv2D(16, 3, activation='relu')， 16表示输出结果的深度是16， 卷积核是一个3 * 3的矩阵， 所以参数数量是： 3 * 3 * 16 + 16 ， 最后的 +16 是因为每次卷积需要有一个bias值， 所以 +16；
第二个卷积层： layers.Conv2D(32, 3, activation='relu')， 输出的深度是32， 卷积核是3 * 3， 所以参数的数量是3 * 3 * 16 * 32 + 32， 同样+32是因为每次卷积操作需要加一个bias， 最后输出结果深度是32， 所以需要32次卷积操作， 需要+32；

Model: "decoder"
_________________________________________________________________
Layer (type)                 Output Shape              Param   
=================================================================
encoded_img (InputLayer)     [(None, 16)]              0         
_________________________________________________________________
reshape (Reshape)            (None, 4, 4, 1)           0         
_________________________________________________________________
conv2d_transpose (Conv2DTran (None, 6, 6, 16)          160       
_________________________________________________________________
conv2d_transpose_1 (Conv2DTr (None, 8, 8, 32)          4640      
_________________________________________________________________
up_sampling2d (UpSampling2D) (None, 24, 24, 32)        0         
_________________________________________________________________
conv2d_transpose_2 (Conv2DTr (None, 26, 26, 16)        4624      
_________________________________________________________________
conv2d_transpose_3 (Conv2DTr (None, 28, 28, 1)         145       
=================================================================

反卷积：
第一层： Conv2DTranspose(16, 3, activation='relu')， 表示： 输出的深度是16， 卷积核是 3 * 3， 所以输出的结果是（4 - 1 + 3） * （4 - 1 + 3） * 16， 和卷积操作相反）