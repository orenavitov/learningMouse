package BalkingTest;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class BalkingData {

    private String fileName;
    private String content;
    private boolean changed;

    public BalkingData(String fileName, String content) {
        this.fileName = fileName;
        this.content = content;
        this.changed = true;
    }

    public synchronized void change(String content) {
        this.content = content;
        changed = true;
    }

    public synchronized void save() {
        if (!changed) {
            return;
        }
        try {
            doSave();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.changed = false;
    }

    private void doSave() throws IOException {
        System.out.println(Thread.currentThread().getName() + " Save!");
        try(Writer writer = new FileWriter(fileName, true)) {
            writer.write(content);
            writer.write("\n");
            writer.flush();
        }
    }

}
