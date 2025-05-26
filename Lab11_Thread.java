import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lab11_Thread extends Thread {
    private static final List<String> data = Collections.synchronizedList(new ArrayList<>());
    private final String name;
    private final int runs;

    public Lab11_Thread(String name, int runs) {
        this.name = name;
        this.runs = runs;
    }

    @Override
    public void run() {
        for (int i = 0; i < runs; i++) {
            try {
                Thread.sleep(50);
            } catch (Exception e) {
                e.printStackTrace();
            }
            addItem(name + " " + i);
        }
    }

    public void addItem(String s) {
        System.out.println(s);
        data.add(s);
    }

    public static List<String> getSharedList() {
        synchronized (data) {
            return new ArrayList<>(data);
        }
    }

    public static void clearSharedList() {
        synchronized (data) {
            data.clear();
        }
    }
}
