import java.io.*;

public class CheckDialogCallsInEm {
    public static void main(String[] args) throws Exception {
        Process p = Runtime.getRuntime().exec(new String[]{"tools/jdk/bin/javap.exe", "-cp", ".client/NSO.jar", "-c", "-p", "em"});
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        String curMethod = "";
        while ((line = br.readLine()) != null) {
            if (line.contains("(") && line.contains(")") && (line.contains("public") || line.contains("protected") || line.contains("private") || line.contains("void"))) {
                curMethod = line.trim();
            }
            if (line.contains("Field d:Las;")) {
                System.out.println("Method " + curMethod + " accesses em.d: " + line.trim());
            }
        }
    }
}
