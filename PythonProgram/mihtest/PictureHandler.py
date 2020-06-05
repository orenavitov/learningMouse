from PIL import Image
import numpy as np
import matplotlib.pyplot as plt
import math
picture_path = r"C:\Users\mihao\Desktop\米昊的东西\图片\test.jpg"
save_picture_path = r"C:\Users\mihao\Desktop\米昊的东西\图片\_test.jpg"
# 读取并显示图片
def Test(path):
    img = Image.open(path)
    width = img.size[0]
    height = img.size[1]
    print("width:{0}".format(width))
    print("height:{0}".format(height))
    plt.figure("test")
    plt.imshow(img)
    plt.show()



# 翻转灰度值并显示
def Test1(path):
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
def Test2(path, save_path):
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
def Test3(path, save_path):
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

def Test4(path):
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

def Test5(path, save_path):
    img = np.array(Image.open(path))
    width = img.shape[0]
    height = img.shape[1]
    channel = img.shape[2]
    delay = [0.3, 0.59, 0.11]
    for w in range(width):
        for h in range(height):
            pixel = img[w, h]
            gray = 0
            for i in range(channel):
                gray = gray +pixel[i] * delay[i]
            for i in range(channel):
                pixel[i] = gray
    img = Image.fromarray(img)
    plt.figure("test")
    plt.imshow(img)
    plt.show()
    img.save(save_path)

def Test6(path, save_path):
    filter_size = 3
    padding_size = (filter_size - 1)
    img = np.array(Image.open(path))
    width = img.shape[0]
    height = img.shape[1]
    channel = img.shape[2]
    copy_image = np.zeros([width + padding_size, height + padding_size, channel], dtype = 'uint8')
    copy_image_ = np.zeros([width, height, channel], dtype = 'uint8')
    for w in range(width):
        for h in range(height):
            pixel = img[w, h]
            for c in range(channel):
                copy_image[w + int(padding_size / 2)][h + int(padding_size / 2)][c] = pixel[c]
    for w in range(width):
        for h in range(height):
            src_x = int(w + padding_size / 2)
            src_y = int(h + padding_size / 2)
            pixel = copy_image[w, h]
            for c in range(channel):
                start_x = int(src_x - padding_size / 2)
                end_x = int(src_x + padding_size / 2)
                start_y = int(src_y - padding_size / 2)
                end_y = int(src_y + padding_size / 2)
                neighbors = copy_image[start_x : end_x + 1, start_y : end_y + 1, c]
                neighbor_sum = np.sum(neighbors) - copy_image[w][h][c]
                copy_image_[w][h][c] = int(neighbor_sum / (filter_size ** 2 - 1))
    copy_image_ = Image.fromarray(copy_image_)
    plt.figure("test")
    plt.imshow(copy_image_)
    plt.show()
    copy_image_.save(save_path)
    print("end")


if __name__ == '__main__':
    path = r'C:\Users\mihao\Desktop\米昊的东西\图片\test.jpg'
    save_path = r'C:\Users\mihao\Desktop\米昊的东西\图片\test6.jpg'
    Test6(path, save_path)