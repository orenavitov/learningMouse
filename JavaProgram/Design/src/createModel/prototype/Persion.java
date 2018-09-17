package createModel.prototype;

import java.io.*;

/**
 * 原型模式
 */
public class Persion implements Cloneable{

    private static final long serialFVersionUID = 1L;
    private String name;

    /**
     * 浅clone
     * @return
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException {
        Persion persion = (Persion) super.clone();
        return persion;
    }

    /**
     * 深clone
     * @return
     * @throws IOException
     * @throws CloneNotSupportedException
     * @throws ClassNotFoundException
     */
    public Object deepClone() throws IOException, CloneNotSupportedException, ClassNotFoundException {
        /* 写入当前对象的二进制流 */
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this);

        /* 读出二进制流产生的新对象 */
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return ois.readObject();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
