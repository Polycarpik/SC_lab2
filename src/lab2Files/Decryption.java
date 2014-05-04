package lab2Files;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Polina on 11-Apr-14.
 */
public class Decryption {

    public void decrypt(String textfileName, Key key) throws FileNotFoundException {
        CryptoHelper ch = new CryptoHelper();
        ArrayList<String> bigrammHolder = ch.getAllSortedBigramms("alphabet"); //get all bigramms with
        int module = bigrammHolder.size();                                     //corresponding to them numbers

        List<String> textInBigramms = ch.parseTextToListOfBigramms(textfileName); //get list of bigramms from text

        List<String> decryptedBigramms = new LinkedList<String>();  //here will be all decrypted bigramms
        ObligatoryMath om = new ObligatoryMath();
        int inverseA = om.getInverseElement(key.a, module);
        int y; int x;
        for (int i = 0; i < textInBigramms.size(); i++) {
            y = bigrammHolder.indexOf(textInBigramms.get(i));
            x = inverseA * (y - key.b) % module;
            decryptedBigramms.add(bigrammHolder.get(x));
        }

        ch.toFile("decrypted_", textfileName, decryptedBigramms);


    }

}
