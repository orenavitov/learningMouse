package test.nioTest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelTest {
    public static void main(String args[]) throws IOException {
        RandomAccessFile  aFile = new RandomAccessFile("C:\\Users\\mih\\Desktop\\aFile.txt", "rw");

        FileChannel inChannel = aFile.getChannel();
        ByteBuffer buf = ByteBuffer.allocate(48);
        RandomAccessFile bFile = new RandomAccessFile("C:\\Users\\mih\\Desktop\\bFile.txt", "rw");
        FileChannel outChannel = bFile.getChannel();
        if (inChannel.read(buf) != -1) {
            while (buf.position() > 0) {
                long position = bFile.length();
                bFile.seek(position);
                outChannel.write(buf);

                buf.clear();
                inChannel.read(buf);
            }
        };
        inChannel.close();
        outChannel.close();
        aFile.close();
        bFile.close();
    }
}
