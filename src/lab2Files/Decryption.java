package lab2Files;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Polina on 11-Apr-14.
 */
public class Decryption {

    public void decrypt(String textfileName,Key key) throws FileNotFoundException {
        CryptoHelper ch = new CryptoHelper();
        ArrayList<String> bigrammHolder = ch.getAllSortedBigramms("alphabet"); //get all bigramms with corresponding to them numbers
        int module = bigrammHolder.size();

        List<String> textInBigramms = ch.parseTextToListOfBigramms(textfileName); //get list of bigramms from text

        List<String> decryptedBigramms = new LinkedList<String>();  //here will be all decrypted bigramms
        ObligatoryMath om = new ObligatoryMath();
        for (int i = 0; i < textInBigramms.size(); i++)
            decryptedBigramms.add(bigrammHolder.get(((om.getInverseElement(key.a, module)) * ((bigrammHolder.indexOf(textInBigramms.get(i))) - key.b)) % module));

        ch.toFile("decrypted_",textfileName,decryptedBigramms);



    }

}
