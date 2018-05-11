import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import javafx.scene.control.RadioButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

/**
 * Created by Paul on 5/11/2018.
 */
public class GInterface implements ActionListener {
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
    private JTextArea textAreaTrace;
    private ButtonGroup buttonGroup = new ButtonGroup();
    private String mMessage= "";
    private String mEncrypted= "";
    private String mKey= "";
    private String mOldMessage= "";
    private RSA rsa;
    private byte[] bytesRSA;
    private String trace = "";
    private static final int CESAR_KEY = 7;
    private static int encCounter = 0;

    public GInterface() {
        buttonGroup.add(radioButtonCesar);
        buttonGroup.add(radioButtonVingere);
        buttonGroup.add(radioButtonRSA);
        buttonGroup.add(radioButton3);
        radioButtonCesar.setSelected(true);
        rsa = new RSA();
        textFieldEnc.setEnabled(false);

        setListeners();
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

    public void setListeners(){
        buttonDecrypt.addActionListener(this);
        buttonEncrypt.addActionListener(this);
        radioButton3.addActionListener(this);
        radioButtonCesar.addActionListener(this);
        radioButtonRSA.addActionListener(this);
        radioButtonVingere.addActionListener(this);
    }

    public void updateUI(){

        if(!(mOldMessage.compareTo(textFieldMes.getText())== 0)){
            mOldMessage = textFieldMes.getText();
            encCounter = 0;
        }
        if(encCounter == 0){
            mMessage = textFieldMes.getText();
        }
        else{
            mMessage = mEncrypted;
        }
    }


    public void doAction(boolean encrypt, String buttonText){


        updateUI();
        switch (buttonText){
            case "Cesar":
                System.out.print("Merge");
                Cesar cesar = new Cesar();
                if(encrypt){
                    mEncrypted = cesar.crypt(mMessage, CESAR_KEY);
                }
                else{
                    mEncrypted = cesar.decrypt(mMessage, CESAR_KEY);
                }
                break;
            case "Vinegere":
                Vigenere vigenere = new Vigenere();
                mKey = textFieldKey.getText().toUpperCase();
                mMessage = mMessage.toUpperCase();
                if(encrypt)
                    mEncrypted = vigenere.encrypt(mMessage, mKey);
                else
                    mEncrypted = vigenere.decrypt(mMessage, mKey);
                break;
            case "RSA":
                if(encrypt){
                    if(encCounter == 0)
                        bytesRSA = rsa.encrypt(mMessage);
                    else
                        bytesRSA = rsa.encryptBytes(bytesRSA);
                    mEncrypted = RSA.byteToString(bytesRSA);
                }
                else {
                    bytesRSA = rsa.decryptedBytes(bytesRSA);
                    mEncrypted = RSA.byteToString(bytesRSA);
                }
                break;
        }

        textFieldEnc.setText(mEncrypted);
        if(encrypt) {
            trace += "Encripting with " + buttonText + System.lineSeparator();
            encCounter++;
        }else {
            trace += "Decripting with " + buttonText + System.lineSeparator();
            encCounter--;
        }
        textAreaTrace.setText(trace);
    }

    public static void main(String args[]){
        JFrame frame = new JFrame("Proiect");
        frame.setContentPane(new GInterface().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JButton){
            JButton button = (JButton) e.getSource();
            switch (button.getText()){
                case "Encrypt":
                    doAction(true, getSelectedButtonText());
                    break;
                case "Decrypt":
                    doAction(false, getSelectedButtonText());
                    break;
            }
        }
        else{
            JRadioButton button = (JRadioButton) e.getSource();
            switch (button.getText()){
                case "Cesar":
                case "Vinegere":
                case "RSA":
                    encCounter = 0;
                    break;
            }
        }

    }
}
