import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import javafx.scene.control.RadioButton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

/**
 * Created by Paul on 5/11/2018.
 */
public class GInterface {
    private JPanel panelMain;
    private JButton buttonEncrypt;
    private JTextField textFieldMes;
    private JTextField textFieldKey;
    private JTextField textFieldEnc;
    private JRadioButton radioButtonVingere;
    private JRadioButton radioButtonCesar;
    private JRadioButton radioButton3;
    private JRadioButton radioButtonRSA;
    private JButton buttonDecrypt;
    private ButtonGroup buttonGroup = new ButtonGroup();
    private String mMessage= "";
    private String mEncrypted= "";
    private String mKey= "";
    private String mDecrypted= "";
    private RSA rsa;
    private byte[] bytesRSA;
    private static final int CESAR_KEY = 7;

    public GInterface(){
        buttonGroup.add(radioButtonCesar);
        buttonGroup.add(radioButtonVingere);
        buttonGroup.add(radioButtonRSA);
        buttonGroup.add(radioButton3);
        radioButtonCesar.setSelected(true);

        textFieldEnc.setEnabled(false);

        buttonEncrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doAction(true, getSelectedButtonText());
            }
        });
        buttonDecrypt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doAction(false, getSelectedButtonText());
            }
        });
    }
    public String getSelectedButtonText() {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }
    public void updateUI(boolean encrypt){
            textFieldEnc.setEnabled(true);

        if(!textFieldEnc.getText().isEmpty()){
            textFieldMes.setText(textFieldEnc.getText());
        }
    }

    public void doAction(boolean encrypt, String buttonText){
        updateUI(encrypt);
        mMessage = textFieldMes.getText();
        switch (buttonText){
            case "Cesar":
                System.out.print("Merge");
                Cesar cesar = new Cesar();
                if(encrypt)
                    mEncrypted = cesar.crypt(mMessage, CESAR_KEY);
                else
                    mEncrypted = cesar.decrypt(mMessage, CESAR_KEY);
                break;
            case "Vinegere":
                Vigenere vigenere = new Vigenere();
                mKey = textFieldKey.getText().toUpperCase();
                if(encrypt)
                    mEncrypted = vigenere.encrypt(mMessage, mKey);
                else
                    mEncrypted = vigenere.decrypt(mMessage, mKey);
                break;
            case "RSA":
                if(encrypt){
                    rsa = new RSA();
                    bytesRSA = rsa.encrypt(mMessage);
                    mEncrypted = RSA.byteToString(bytesRSA);
                }
                else
                    mEncrypted = rsa.decrypt(bytesRSA);
                break;
        }
        textFieldEnc.setText(mEncrypted);
        mMessage = mEncrypted;
    }

    public static void main(String args[]){
        JFrame frame = new JFrame("Proiect");
        frame.setContentPane(new GInterface().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
