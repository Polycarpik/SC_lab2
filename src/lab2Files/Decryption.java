package lab2Files;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Polina on 11-Apr-14.
 */
public class Decryption {

    public void decryptWithKey(String textfileName, Key key) throws FileNotFoundException {
        CryptoHelper ch = new CryptoHelper();
        ArrayList<String> bigrammHolder = ch.getAllSortedBigramms("alphabet"); //get all bigramms with
        int module = bigrammHolder.size();                                     //corresponding to them numbers

        List<String> textInBigramms = ch.parseTextToListOfBigramms(textfileName); //get list of bigramms from text

        List<String> decryptedBigramms = new LinkedList<String>();  //here will be all decrypted bigramms
        ObligatoryMath om = new ObligatoryMath();
        int inverseA = om.getInverseElement(key.a, module);
        int y;
        int x;
        for (int i = 0; i < textInBigramms.size(); i++) {
            y = bigrammHolder.indexOf(textInBigramms.get(i));
            x = inverseA * (y - key.b) % module;
            if (x < 0) {
                x = x + module;
            }
            decryptedBigramms.add(bigrammHolder.get(x));
        }

//
//        String b = "";                                             //delete when found bug
//        for(int i = 0; i < decryptedBigramms.size(); i++){
//            b += decryptedBigramms.get(i);
//        }
//        System.out.println(b);



//        ch.toFile("decrypted_" + addon, textfileName, decryptedBigramms);
    }

    public void decryptWithoutKey(String textfileName) throws FileNotFoundException {    //makes some shit
        CryptoHelper ch = new CryptoHelper();
        ObligatoryMath om = new ObligatoryMath();
        ArrayList<String> bigrammHolder = ch.getAllSortedBigramms("alphabet");
        int module = bigrammHolder.size();
        List<String> mrblist = ch.parseTextToListOfBigramms("mostRepeatedNgramms");
//        MostRepeatableBigrammsCounter mrb = new MostRepeatableBigrammsCounter(textfileName);
        List<String> mrbInText = ch.parseTextToListOfBigramms("mostRepeatedNgrammsEn"); //mrb.getFiveMostRepeatableToArrayList();

        int a = ((bigrammHolder.indexOf(mrbInText.get(1)) - bigrammHolder.indexOf(mrbInText.get(2)))*(om.getInverseElement(bigrammHolder.indexOf(mrblist.get(1))
        - bigrammHolder.indexOf(mrblist.get(2)),module))%module);
        int b = (bigrammHolder.indexOf(mrbInText.get(2)) - a*bigrammHolder.indexOf(mrblist.get(2)))%module;


//            a = ((y1 - y2) * (om.getInverseElement(x1 - x2, module))) % module;
//            b = (y1 - a * x1) % module;
//            if(a < 0) a = module + a;
//            if(b < 0) b = module + b;
            System.out.println("a " + a + "     b " + b);
            decryptWithKey(textfileName, new Key(a, b));


    }


}
