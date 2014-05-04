package lab2Files;

import java.io.FileNotFoundException;

/**
 * Created by Polina on 11-Apr-14.
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
//        Encryption encryption = new Encryption();
//        encryption.encrypt("text1");

        Decryption d = new Decryption();
        Key key = new Key(468,130);
        d.decrypt("encrypted_text1",key);

    }

}
