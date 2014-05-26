package lab2Files;

import lab1Heritage.Counter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Polina on 26-Apr-14.
 */
public class CriteriaOfMeaningfulText {

    public boolean makeAllCheks(List<String> text) throws FileNotFoundException {
        if (nonexhistingNgrammCheck(text) && frequentNgrammCheck(text)
                && uniformityCheck(text) && entropyCheck(text))
            return true;
        else return false;
    }

    public boolean nonexhistingNgrammCheck(List<String> text) throws FileNotFoundException {
        CryptoHelper ch = new CryptoHelper();
        List list = ch.parseTextToListOfBigramms("nonExhistingBigramms");

        for (int i = 0; i < text.size(); i++)
            for (int j = 0; j < list.size(); j++) {
                if (text.get(i).equals(list.get(j))) {
//                    System.out.println("found this " + list.get(j));
                    return false;
                }
            }
        return true;
    }

    public boolean frequentNgrammCheck(List<String> text) {
        return true;
    }

    public boolean uniformityCheck(List<String> text) {
        return true;
    }

    public boolean entropyCheck(List<String> text) throws FileNotFoundException {       //very-very bad code i'm sorry
        CryptoHelper ch = new CryptoHelper();
        ch.toFile("", "someTextName", text); //making file to feed to Counter class

        Counter counter = new Counter("someTextName");     // now have file with counted entropy
        counter.noncrossingNgrammCounter(2);              // and bigramm probabilities

        Scanner in = new Scanner(new File("noncrossingNgramms_someTextName"));     //extracting line with entropy from file
        String str1;
        String entropyLine ="";
        while (in.hasNext()) {
            entropyLine = in.nextLine();
        }
        in.close();

        System.out.println(entropyLine.charAt(10)); //check


        File f = new File("someTextName");     // deleting now not important file
        f.delete();
        File f1 = new File("noncrossingNgramms_someTextName");
        f1.delete();


        return true;
    }

}
