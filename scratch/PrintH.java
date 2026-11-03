import java.lang.reflect.Field;

public class PrintH {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName("cf");
        Field f = clazz.getDeclaredField("h");
        f.setAccessible(true);
        int[] h = (int[]) f.get(null);
        for (int i = 0; i < h.length; i++) {
            System.out.println("h[" + i + "] = " + h[i]);
        }
    }
}

