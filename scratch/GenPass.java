import at.favre.lib.crypto.bcrypt.BCrypt;

public class GenPass {
    public static void main(String[] args) {
        String pass = args.length > 0 ? args[0] : "admin123";
        String hash = BCrypt.withDefaults().hashToString(12, pass.toCharArray());
        System.out.println("HASH:" + hash);
        boolean verify = BCrypt.verifyer().verify(pass.toCharArray(), hash).verified;
        System.out.println("VERIFY:" + verify);
    }
}

