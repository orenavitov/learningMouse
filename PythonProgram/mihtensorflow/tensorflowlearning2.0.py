'''
@Time: 2019/11/14 19:12
@Author: mih
@Des: 
'''
import tensorflow as tf
from tensorflow import keras
from tensorflow.keras import layers
import matplotlib.pyplot as plt
import numpy

def tensorTest():

    pass

def buildModelTest():
    model = tf.keras.Sequential()
    model.add(layers.Dense(32, activation='relu'))
    model.add(layers.Dense(32, activation='relu'))
    model.add(layers.Dense(10, activation='softmax'))
    return model

def createModelTest():
    inputs = tf.keras.Input(shape=(784,), name='img')
    h1 = layers.Dense(32, activation='relu')(inputs)
    h2 = layers.Dense(32, activation='relu')(h1)
    outputs = layers.Dense(10, activation='softmax')(h2)
    model = tf.keras.Model(inputs=inputs, outputs=outputs, name='mnist model')

    model.summary()
    keras.utils.plot_model(model, 'mnist_model.png')
    keras.utils.plot_model(model, 'model_info.png', show_shapes=True)

def plotModel():
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

    autoencoder.fit()
if __name__ == '__main__':
    print(tf.__version__)
    plotModel()


