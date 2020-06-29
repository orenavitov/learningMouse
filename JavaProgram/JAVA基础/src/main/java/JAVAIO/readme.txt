https://www.cnblogs.com/furaywww/p/8849850.html

1. 标准输出流： System.out, 方法有：
    Void print(参数)
    Void println(参数)
2. 标准输入流： System.in, 方法有：
    int read(), 返回值是读入字节的ASCALL值， 若返回值为-1， 表示没有读到任何字节， 读取介数；
    int read(byte[] b), 读入多个字节到缓冲区b中， 返回值是读入的字节数；
System.in 中的in是BufferedInputStream
System.out 中的out是BufferedOutputStream
3. 标准错误流： System.error

IO的分类：
1. 字节输入输出流：
    InputStream
    OutputStream
2. 字符流：
    Reader
    Writer
3. 缓冲流：
    字符缓冲流：BufferedReader, BufferedWriter
    字节缓冲流：BufferedInputStream, BufferedOutputStream
4. 转换流：把字节流转换成字符流（在处理汉字时有用）
    InputStreamReader
    OutputStreamWriter
5. 内存流：
    字节内存流：ByteArrayOutputStream, ByteArrayInputStream
    字符内存流：CharArrayReader, CharArrayWriter
    字符串流：StringReader, StringWriter
6. 合并流：把多个输入/输出流合并成一个， 输入/输出完一个接着输入输出下一个
    SequenceInputStream
    SequenceOutputStream
7. 对象流(用于序列化与反序列化)
    ObjectInputStream
    ObjectOutputStream

