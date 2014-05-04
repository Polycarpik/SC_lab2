package lab2Files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Polina on 04-May-14.
 */
public class CryptoHelper {


    public ArrayList<String> getAlphabetFromfileToArrayList(String fileWithAlphabetName) throws FileNotFoundException {
        ArrayList<String> alphabet = new ArrayList<String>();
        Scanner scanner = new Scanner(new File(fileWithAlphabetName));    //alphabet to List
        while (scanner.hasNext()) {
            alphabet.add(scanner.next());
        }
        scanner.close();
        return alphabet;
    }

    public ArrayList<String> getAllSortedBigramms(String fileWithAlphabetName) throws FileNotFoundException {
        List<String> alphabet = getAlphabetFromfileToArrayList(fileWithAlphabetName);
        int m = alphabet.size();              //amount of letters in alphabet
        ArrayList<String> allNgramms = new ArrayList<String>(m*m);

        for (int i = 0; i < alphabet.size(); i++) {
            for (int j = 0; j < alphabet.size(); j++) {
                 allNgramms.add(i*m + j, alphabet.get(i) + alphabet.get(j)); //sorting bigramms
            }                                                                //by the corredponding number
        }
        return allNgramms;
    }

    public Key keyGenerator (int module){
        Key key = new Key(new Random().nextInt(module), new Random().nextInt(module));
        return key;
    }

    public List<String> parseTextToListOfBigramms(String textfileName) throws FileNotFoundException {
        String text = "";
        Scanner in = new Scanner(new File(textfileName));      //reading from file
        while (in.hasNext()) {
            text += in.nextLine();
        }
        in.close();
        List<String> textInBigramms = new ArrayList<String>();
        for (int i = 0; i + 1 < text.length(); i += 2) {
            textInBigramms.add(text.substring(i, i + 2));      //parsing text to non-crossing bigramms
        }
        return textInBigramms;
    }

    public void toFile(String addon, String textfileName, List<String> list) {
        String b = "";                                             //everything written next is output to file
        for(int i = 0; i < list.size(); i++){
            b += list.get(i);
        }
        try {
            PrintWriter out = new PrintWriter(new File(addon + textfileName).getAbsoluteFile());
            try {
                out.print(b);
            } finally {
                out.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

