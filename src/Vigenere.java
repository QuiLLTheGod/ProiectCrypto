/**
 * Created by Paul on 5/11/2018.
 */
public class Vigenere {
    public String encrypt(String text, final String key) {
        String res = "";
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                res += (char) ((c + key.charAt(j) - 2 * 'A') % 26 + 'A');
                j = ++j % key.length();
            }
            else res += (char) c;
        }
        return res;
    }

    public String decrypt(String text, final String key) {
        String res = "";
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                res += (char) ((c - key.charAt(j) + 26) % 26 + 'A');
                j = ++j % key.length();
            }
            else res += (char) c;
        }
        return res;
    }
}
