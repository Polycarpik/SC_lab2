package lab2Files;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Polina on 11-Apr-14.
 */
public class Encryption {

    public Encryption() {
    }

    public Key encrypt(String textfileName) throws FileNotFoundException {
        CryptoHelper ch = new CryptoHelper();
        ArrayList<String> bigrammHolder = ch.getAllSortedBigramms("alphabet"); //get all bigramms
        int module = bigrammHolder.size();                                     //corresponding to them numbers
        Key key = ch.keyGenerator(module);     //generating key

        System.out.println("a = " + key.a + ", b = " + key.b);
        List<String> textInBigramms = ch.parseTextToListOfBigramms(textfileName); //get list of bigramms from text

        List<String> encryptedBigramms = new LinkedList<String>();  //here will be all encrypted bigramms
        for (int i = 0; i < textInBigramms.size(); i++) {      //encryption here
            //Y_i = (a*X_i + b) mod m^2
            encryptedBigramms.add(bigrammHolder.get(((key.a * bigrammHolder
                    .indexOf(textInBigramms.get(i))) + key.b)%module));
        }

        ch.toFile("encrypted_", textfileName, encryptedBigramms); //writing to file
        return key;
    }
}
