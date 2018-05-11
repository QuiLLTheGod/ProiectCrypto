import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * Created by Paul on 5/11/2018.
 */
public class RSA {
    private final static BigInteger one      = new BigInteger("1");
    private final static SecureRandom random = new SecureRandom();
    private final static int N = 100;

    private BigInteger privateKey;
    private BigInteger publicKey;
    private BigInteger modul;

    private byte[] bytes;

    RSA() {
        // PASUL 1 SE GENEREAZA 2 NUMERE PRIME
        BigInteger p = BigInteger.probablePrime(N/2, random);
        BigInteger q = BigInteger.probablePrime(N/2, random);

        // SE CALCULEAZA PHI = (P-1)(Q-1)
        BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));

        modul    = p.multiply(q);
        publicKey  = new BigInteger("65537");
        privateKey = publicKey.modInverse(phi);

    }


    byte[] encrypt(String message) {
        // SE CALCULEAZA M^PUBLIC mod modul
        return stringToBigInteger(message).modPow(publicKey, modul).toByteArray();
    }

    String  decrypt(byte[] bytes) {
        // SE CALCULEAZA M^PRIVATE mod modul
        return byteToString(new BigInteger(bytes).modPow(privateKey, modul).toByteArray());
    }
    public BigInteger stringToBigInteger(String string){
        bytes = string.getBytes(StandardCharsets.UTF_8);
        return new BigInteger(bytes);
    }

    public static String byteToString(byte[] bytes){
        try {
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "error";
    }

}
