
import chardet

def fun():
    sourceFile = open(r"C:\Users\mihao\Desktop\_LOD_SharedStashSave.sss", "rb")
    # targetFile = open(r"C:\Users\mihao\Desktop\result.txt", "wb")
    # for line in sourceFile.readlines():
    #     bytes = bytearray(line)
    #     targetFile.write(bytes)
    result = chardet.detect(sourceFile.read())
    print(result)
if __name__ == '__main__':
    fun()
