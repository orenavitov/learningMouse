package ClassLoaderTest;

import java.io.*;

public class MyClassLoader extends ClassLoader {

//    private static final String DEFAULT_PATH = System.getProperty("user.dir") + "\\";

    private String classLoaderName;

    private String dir;

    private String classPath;

    private final String name = "MyClassLoader";

    public MyClassLoader() {
        super();
    }

    public MyClassLoader(String classPath) {
//        super();
        this.classPath = classPath;

    }

//    public MyClassLoader(String classLoaderName) {
//        super();
//        this.classLoaderName = classLoaderName;
//    }

    public MyClassLoader(String classLoaderName, ClassLoader parent) {
        super(parent);
        this.classLoaderName = classLoaderName;
    }


    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getClassLoaderName() {
        return classLoaderName;
    }

    @Override
    protected Class<?> findClass(String name) {
        try {

            File classFile = new File(classPath);
            if (!classFile.exists()) {
                throw new ClassNotFoundException(dir + name + " not found!");
            }
            byte[] classBytes = loadClassBytes(classFile);
            if (classBytes == null || classBytes.length == 0) {
                throw new ClassNotFoundException("Load " + dir + "/" + name + " error!");
            }
            return defineClass(name, classBytes, 0, classBytes.length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] loadClassBytes(File classFile) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             FileInputStream fis = new FileInputStream(classFile)) {
            byte[] buffer = new byte[1024];
            int lenght = 0;
            while ((lenght = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, lenght);
            }
            baos.flush();

            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public String getName() {
        return name;
    }
}
