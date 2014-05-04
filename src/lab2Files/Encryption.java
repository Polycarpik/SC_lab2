package lab2Files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Polina on 11-Apr-14.
 */
public class Encryption {

    public Encryption() {
    }

    private String fileToString(String textfileName) throws FileNotFoundException {
        String text = "";
        Scanner in = new Scanner(new File(textfileName));
        while (in.hasNext()) {
            text += in.nextLine();
        }
        in.close();
        return text;
    }


    public void encrypt(String textfileName) throws FileNotFoundException {
        CryptoHelper ch = new CryptoHelper();
        ArrayList<String> bigrammHolder = ch.getAllSortedBigramms("alphabet"); //get all bigramms with corresponding to them numbers
        int module = bigrammHolder.size();
        Key key = ch.keyGenerator(module);     //generating key

        String a = fileToString(textfileName);
        List<String> textInBigramms = new ArrayList<String>();
        for (int i = 0; i + 1 < a.length(); i += 2) {
            textInBigramms.add(a.substring(i, i + 2));      //parsing text to non-crossing bigramms
        }

        List<String> encryptedBigramms = new LinkedList<String>();  //here will be all encrypted bigramms
        for (int i = 0; i < textInBigramms.size(); i++) {
            //Y_i = (a*X_i + b) mod m^2
            encryptedBigramms.add(bigrammHolder.get(((key.a * bigrammHolder.indexOf(textInBigramms.get(i))) + key.b)%module));
        }

        String b = "";                                             //everything written next is output to file
        for(int i = 0; i < encryptedBigramms.size(); i++){
            b += encryptedBigramms.get(i);
        }
        try {
            PrintWriter out = new PrintWriter(new File("encrypted_" + textfileName).getAbsoluteFile());
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
