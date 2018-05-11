import java.io.UnsupportedEncodingException;

/**
 * Created by Paul on 5/11/2018.
 */
public class Cesar {

    final static String ALFABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    final static byte[] ALFABYTES = ALFABET.getBytes();
    private static String string;
    private static byte[] bytes;
    private static int[] ints;
    public void intTransform(String toTransform){
        ints = new int[toTransform.length()];
        toTransform = toTransform.toUpperCase();
        bytes = toTransform.getBytes();
        for(int i = 0;i< toTransform.length();i++){
            for(int j = 0; j<ALFABET.length();j++){
                if(bytes[i] == ALFABYTES[j]){
                    ints[i] = j;
                    break;
                }
            }
        }
    }

    public String crypt(String toEncrypt, int key){
        intTransform(toEncrypt);
        for(int i = 0; i< toEncrypt.length();i++){
            bytes[i] = ALFABYTES[(ints[i] + key) % ALFABET.length()];
        }
        try {
            string = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return string;
    }
    public  String decrypt(String toDecrypt, int key){
        intTransform(toDecrypt);
        for(int i = 0;i<toDecrypt.length();i++){
            bytes[i] = ALFABYTES[(ints[i] + ALFABET.length() -key) % ALFABET.length()];
        }
        try {
            string = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return string;
    }
}
