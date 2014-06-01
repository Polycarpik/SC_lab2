package lab2Files;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Polina on 11-Apr-14.
 */
public class Decryption {

    private CriteriaOfMeaningfulText comt = new CriteriaOfMeaningfulText();
    private CryptoHelper ch = new CryptoHelper();
    private ArrayList<String> bigrammHolder;
    private int module;

    public Decryption() throws FileNotFoundException {
        bigrammHolder = ch.getAllSortedBigramms("alphabet");
        module = bigrammHolder.size();
    }

    public void decryptWithKey(String addon, String textfileName, Key key) throws FileNotFoundException {
        List<String> textInBigramms = ch.parseTextToListOfBigramms(textfileName); //get list of bigramms from text

        List<String> decryptedBigramms = new LinkedList<String>();  //here will be all decrypted bigramms
        ObligatoryMath om = new ObligatoryMath();
        int inverseA = om.getInverseElement(key.a, module);
        int y;
        int x;
        for (int i = 0; i < textInBigramms.size(); i++) {
            y = bigrammHolder.indexOf(textInBigramms.get(i));
            x = (inverseA * (y - key.b)) % module;
            if (x < 0) {
                x = x + module;
            }
            decryptedBigramms.add(bigrammHolder.get(x));
        }


        if (!comt.makeAllCheks(decryptedBigramms)) {
            return;
        }

        System.out.println("a = " + key.a + "     b = " + key.b);

        String b = "";                                             //delete when found bug
        for (int i = 0; i < decryptedBigramms.size(); i++) {
            b += decryptedBigramms.get(i);
        }
        System.out.println(b);


//        ch.toFile(addon + "_decrypted_", textfileName, decryptedBigramms);
    }

    public void decryptWithoutKey(String textfileName) throws FileNotFoundException {    //makes some shit
        ObligatoryMath om = new ObligatoryMath();
        List<String> mrblist = ch.parseTextToListOfBigramms("mostRepeatedNgramms");   //list with most repeated bigramms
        MostRepeatableBigrammsCounter mrb = new MostRepeatableBigrammsCounter(textfileName);
        List<String> mrbInText = mrb.getMostRepeatableToArrayList(5);   //list of most repeated bigramms in encrypted text

        int x1;
        int x2;
        int y1;
        int y2;
        int[] a;
        int b;
        List<Integer> keys = new ArrayList<Integer>();
        for (int i = 0; i < mrblist.size(); i++)
            for (int j = i + 1; j < mrblist.size(); j++)
                for (int m = 0; m < mrblist.size(); m++)
                    for (int l = 0; l < mrblist.size(); l++) {
                        x1 = bigrammHolder.indexOf(mrblist.get(i));
                        x2 = bigrammHolder.indexOf(mrblist.get(j));
                        y1 = bigrammHolder.indexOf(mrbInText.get(m));
                        y2 = bigrammHolder.indexOf(mrbInText.get(l));
                        a = om.solvingLinearComparisons(x1 - x2, y1 - y2, module);
                        if (a == null) continue;
                        for (int k = 0; k < a.length; k++) {
                            b = (y1 - a[k] * x1) % module;
                            if (a[k] < 0) a[k] = module + a[k];
                            if (b < 0) b = module + b;
                            if (keys.contains(a[k])) {
                                continue;
                            } else keys.add(a[k]);
                            decryptWithKey(mrblist.get(i), textfileName, new Key(a[k], b));
                        }
                    }
    }


}



