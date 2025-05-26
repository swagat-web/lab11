import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class Lab11_Tests {

    @Test
    public void test1() {
        // Clear shared list before starting
        Lab11_Thread.clearSharedList();

        Lab11_Thread threadA = new Lab11_Thread("A1", 100);
        Lab11_Thread threadB = new Lab11_Thread("B1", 100);

        threadA.start();
        threadB.start();

        try {
            threadA.join();
            threadB.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> data = Lab11_Thread.getSharedList();
        long countA = data.stream().filter(s -> s.startsWith("A1")).count();
        long countB = data.stream().filter(s -> s.startsWith("B1")).count();

        assertEquals(100, countA);
        assertEquals(100, countB);
    }

    @Test
    public void test2() {
        Lab11_Thread.clearSharedList();

        Lab11_Thread threadA = new Lab11_Thread("A2", 500);
        Lab11_Thread threadB = new Lab11_Thread("B2", 500);

        threadA.start();
        threadB.start();

        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> data = Lab11_Thread.getSharedList();
        assertTrue("Expected at least 10 entries after 500ms", data.size() >= 10);
    }

    @Test
    public void test3() {
        Lab11_Thread.clearSharedList();

        Lab11_Thread threadA = new Lab11_Thread("A3", 10);
        Lab11_Thread threadB = new Lab11_Thread("B3", 10);

        threadA.start();

        try {
            threadA.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        threadB.start();

        try {
            threadB.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> data = Lab11_Thread.getSharedList();
        long countA = data.stream().filter(s -> s.startsWith("A3")).count();
        long countB = data.stream().filter(s -> s.startsWith("B3")).count();

        assertEquals(10, countA);
        assertEquals(10, countB);

        int firstBIndex = -1;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).startsWith("B3")) {
                firstBIndex = i;
                break;
            }
        }

        // All A3 entries should appear before first B3
        for (int i = 0; i < firstBIndex; i++) {
            assertTrue(data.get(i).startsWith("A3"));
        }
    }
}
