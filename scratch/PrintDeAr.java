import java.lang.reflect.Field;

public class PrintDeAr {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName("de");
        Field f = clazz.getDeclaredField("ar");
        f.setAccessible(true);
        int[] ar = (int[]) f.get(null);
        if (ar == null) {
            System.out.println("de.ar is null! Calling method to initialize...");
            for (java.lang.reflect.Method m : clazz.getDeclaredMethods()) {
                if (m.getParameterTypes().length == 0 && java.lang.reflect.Modifier.isStatic(m.getModifiers())) {
                    try {
                        m.setAccessible(true);
                        m.invoke(null);
                        ar = (int[]) f.get(null);
                        if (ar != null) break;
                    } catch (Throwable ignored) {}
                }
            }
        }
        for (int i = 0; i < ar.length; i++) {
            System.out.println("ar[" + i + "] = " + ar[i]);
        }
    }
}

