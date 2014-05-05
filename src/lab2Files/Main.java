package lab2Files;

import java.io.FileNotFoundException;

/**
 * Created by Polina on 11-Apr-14.
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
//        Encryption encryption = new Encryption();
//        encryption.encrypt("test");

//        MostRepeatableBigrammsCounter mrbc = new MostRepeatableBigrammsCounter("test");
//        mrbc.getFiveMostRepeatable();

        Decryption d = new Decryption();
        d.decryptWithoutKey("encrypted_test");

    }

}
