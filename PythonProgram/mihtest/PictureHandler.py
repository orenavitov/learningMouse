from PIL import Image
import numpy as np
import matplotlib.pyplot as plt
import math
picture_path = r"C:\Users\mihao\Desktop\米昊的东西\图片\test.jpg"
save_picture_path = r"C:\Users\mihao\Desktop\米昊的东西\图片\_test.jpg"
# 读取并显示图片
def test(path):
    img = Image.open(path)
    width = img.size[0]
    height = img.size[1]
    print("width:{0}".format(width))
    print("height:{0}".format(height))
    plt.figure("test")
    plt.imshow(img)
    plt.show()



# 翻转灰度值并显示
def test1(path):
    img = np.array(Image.open(path))
    width = img.shape[0]
    height = img.shape[1]
    channel = img.shape[2]
    for w in range(width):
        for h in range(height):
            pixel = img[w, h]
            for i in range(channel):
                pixel[i] = 255 - pixel[i]
    img = Image.fromarray(img)
    plt.figure("test")
    plt.imshow(img)
    plt.show()
    img.save(save_picture_path)

# 对数处理, 如果对数前没有系数， 图片会很暗
def test2(path, save_path):
    img = np.array(Image.open(path))
    width = img.shape[0]
    height = img.shape[1]
    channel = img.shape[2]
    for w in range(width):
        for h in range(height):
            pixel = img[w, h]
            for i in range(channel):
                pixel[i] = int(math.e * math.log(pixel[i] + 1, math.e))
    img = Image.fromarray(img)
    plt.figure("test")
    plt.imshow(img)
    plt.show()
    img.save(save_path)

# 幂次变换
def test3(path, save_path):
    img = np.array(Image.open(path))
    width = img.shape[0]
    height = img.shape[1]
    channel = img.shape[2]
    for w in range(width):
        for h in range(height):
            pixel = img[w, h]
            for i in range(channel):
                pixel[i] = pixel[i] ** 10
    img = Image.fromarray(img)
    plt.figure("test")
    plt.imshow(img)
    plt.show()
    img.save(save_path)

def test4(path):
    img = np.array(Image.open(path))
    width = img.shape[0]
    height = img.shape[1]
    channel = img.shape[2]
    pictures = []
    for c in range(8):
        picture = np.zeros_like(img, dtype=int)
        for w in range(width):
            for h in range(height):
                pixel = img[w, h]
                for i in range(channel):
                    b_pixel_i = bin(pixel[i])[2:]
                    length = len(b_pixel_i)
                    if c < length:
                        picture[w][h][i] = int(b_pixel_i[-1 - c], base=10)
                    else:
                        picture[w][h][i] = 0
        pictures.append(picture)
    save_path = r"C:\Users\mihao\Desktop\米昊的东西\图片\_test2"
    for i, picture in enumerate(pictures):
        picture = Image.fromarray(picture)
        plt.figure("test{0}".format(i))
        plt.imshow(img)
        plt.show()
        save_path_ = save_path
        save_path_ = "{0}{1}{2}".format(save_path_, i, ".jpg")
        picture.save(save_path_)

if __name__ == '__main__':
    test4(picture_path)