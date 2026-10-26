import java.io.*;

public class PrintEmEUsage {
    public static void main(String[] args) throws Exception {
        String[] classes = {"ec", "em", "j", "ap", "de", "cf"};
        for (String c : classes) {
            System.out.println("================ " + c + " ================");
            Process p = Runtime.getRuntime().exec(new String[]{"javap", "-c", "-p", "-cp", ".client/NSO.jar", c});
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            String curMethod = "";
            while ((line = r.readLine()) != null) {
                if (line.contains("(") && line.contains(")") && (line.contains("public") || line.contains("private") || line.contains("static") || line.contains("final"))) {
                    curMethod = line.trim();
                } else if (line.contains("em.e:Z") || line.contains("Field e:Z")) {
                    System.out.println("  " + curMethod + " :: " + line.trim());
                }
            }
        }
    }
}
