

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;


public class MerkleHellman {

    private LinkedList<BigInteger> linkedListWeight;
    private LinkedList<BigInteger> publicKey;
    private LinkedList<BigInteger> encryptedList;
    private LinkedList<String> bitsList;

    private String decryptedMessage = "";
    public String stringEncryptedMessage = "";
    private BigInteger encryptedMessage ;
    private int sum = 0;
    private static final int LENGTH = 8;
    private static final BigInteger ONE =  new BigInteger("1");
    private static final BigInteger ZERO =  new BigInteger("0");

    public byte[] bytes;
    private BigInteger q, r;
    private Random random;

    public String getEncryptedMessage(){
        return this.stringEncryptedMessage;
    }

    public MerkleHellman(){
        linkedListWeight = new LinkedList<>();
        publicKey = new LinkedList<>();
        bitsList = new LinkedList<>();
        encryptedList = new LinkedList<>();

        random = new Random();
        q = new BigInteger("0");
        r = new BigInteger("0");
        encryptedMessage = new BigInteger("0");
        generatePrivateKey();
        generatePublicKey();

    }

    private boolean gcd(BigInteger p, BigInteger f){
        BigInteger temp ;
        while(f.compareTo(ZERO) == 1) {
            temp = f;
            f = p.mod(f);
            p = temp;
            if(f.compareTo(ZERO) == 0)
                return true;
        }
        return false;
    }

    private void toBits(String text){
        for(int i = 0; i<text.length();i++){
            String charbits = "0" + Integer.toBinaryString(text.charAt(i));
            bitsList.add(charbits);

        }
    }

    private void generatePrivateKey(){
        int x = 0;
        BigInteger s;
        for(int i = 0; i<LENGTH;i++){
            do
                x = random.nextInt(5 ) + sum;
            while (x == 0);
            linkedListWeight.add(new BigInteger("" + (x + sum)));
            sum = x + sum;
        }
        q = new BigInteger("" +sum + random.nextInt(sum));
        do
            r = new BigInteger("" + random.nextInt(sum));
        while (q.gcd(r).compareTo(ZERO)==0);

    }

    public void generatePublicKey(){
        for(int i = 0; i<LENGTH;i++){
            publicKey.add((r.multiply(linkedListWeight.get(i))).mod(q));
        }
    }

    public void encript(String text){
        transformText(text);
        toBits(text);


        for(String s: bitsList){
            for(int i = 0;i<LENGTH;i++){
                if(s.charAt(i) == '1'){
                    encryptedMessage = encryptedMessage.add(publicKey.get(i));
                }
            }
            encryptedList.add(encryptedMessage);
            stringEncryptedMessage += encryptedMessage.toString();
            encryptedMessage = BigInteger.valueOf(0);
        }
    }

    private void decript(){
        for(BigInteger bigInteger: encryptedList){
            dec(bigInteger);
        }
    }

    public void dec(BigInteger c){
        BigInteger s = r.modInverse(q);

        BigInteger c2 = (c.multiply(s)).mod(q);
        String bite = "";
        BigInteger diff = c2;
        LinkedList<BigInteger> bigIntegers = new LinkedList<>();
        Iterator x = linkedListWeight.descendingIterator();
        while(x.hasNext()){
            BigInteger bigInteger = (BigInteger) x.next();
            if(bigInteger.compareTo(new BigInteger("0")) ==0)
                break;
            if((bigInteger.compareTo(diff) != 1)){
                diff = diff.subtract(bigInteger);
                bite = new StringBuilder(bite).append("1").toString();
                bigIntegers.add(diff);
            }
            else
                bite += "0";
        }
        bite = new StringBuilder(bite).reverse().toString();
        bite = bite.substring(1);
        int parseInt = Integer.parseInt(bite,2);
        decryptedMessage += (char) parseInt;
    }


    private BigInteger transformText(String text){
        bytes = text.getBytes();
        return new BigInteger(text.getBytes(StandardCharsets.UTF_8));
    }

}
