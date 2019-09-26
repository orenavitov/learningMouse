import tensorflow as tf

if __name__ == '__main__':
    input_matrix = tf.constant(value = [
        [
        [
            [10, 13, 16],
            [11, 14, 15],
            [16, 17, 18]
        ],
        [
            [10, 13, 16],
            [11, 14, 15],
            [16, 17, 18]
        ],
        [
            [10, 13, 16],
            [11, 14, 15],
            [16, 17, 18]
        ]
    ]
    ], name = "input_matrix", dtype = tf.float32)
    input_filter = tf.constant(value = [
        [
            [
                [1],
                [1],
                [1],
            ],
            [
                [2],
                [2],
                [2]
            ]
        ],
        [
            [
                [3],
                [3],
                [3]
            ],
            [
                [4],
                [4],
                [4]
            ]
        ]
    ], name = 'input_filter', dtype = tf.float32)
    result = tf.nn.conv2d(input_matrix, input_filter, strides = [1, 1, 1, 1], padding = 'VALID')
    with tf.Session() as sess:
        print(sess.run(result))