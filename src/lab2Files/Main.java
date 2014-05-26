package lab2Files;

import java.io.FileNotFoundException;
import java.util.List;


/**
 * Created by Polina on 11-Apr-14.
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        String name = "newTest.txt";
//        Encryption encryption = new Encryption();
//       Key key = encryption.encrypt(name);
//        MostRepeatableBigrammsCounter mrbc = new MostRepeatableBigrammsCounter(name);
//        mrbc.getFiveMostRepeatable();


        Key a = new Key(711, 412); //Sanyok test
        Key b = new Key(13, 151);  // my variant



        CryptoHelper ch = new CryptoHelper();
        List list = ch.parseTextToListOfBigramms("test");
        CriteriaOfMeaningfulText comt = new CriteriaOfMeaningfulText();
        comt.entropyCheck(list);

        Decryption d = new Decryption();
//        d.decryptWithKey("decrypted", name, b);
//        d.decryptWithoutKey(name);

    }

}
