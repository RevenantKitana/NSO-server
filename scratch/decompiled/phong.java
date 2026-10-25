/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class phong {
    private static byte[] b(String string) {
        File file = new File(string);
        try {
            Throwable throwable = null;
            Object var3_5 = null;
            try (FileInputStream fileInputStream = new FileInputStream(file);){
                byte[] byArray = new byte[(int)file.length()];
                fileInputStream.read(byArray);
                return byArray;
            }
            catch (Throwable throwable2) {
                if (throwable == null) {
                    throwable = throwable2;
                } else if (throwable != throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return null;
        }
    }

    public static String a(String string) {
        return string;
    }

    public static void main(String[] stringArray) {
        String string = "C:\\.phong\\logo2.png";
        byte[] byArray = phong.b(string);
        int n2 = 0;
        while (n2 < byArray.length) {
            System.out.print(String.valueOf(byArray[n2]) + ",");
            ++n2;
        }
    }
}

