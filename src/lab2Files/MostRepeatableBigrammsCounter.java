package lab2Files;

import lab1Heritage.Counter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Polina on 11-Apr-14.
 */
public class MostRepeatableBigrammsCounter {

    private String textName;
    private String text;

    public MostRepeatableBigrammsCounter(String text) {
        this.textName = text;
        getAllNoncrossingBigrammsToFile();
    }

    private void getAllNoncrossingBigrammsToFile(){
        try {
            Counter counter = new Counter(textName);
            counter.noncrossingNgrammCounter(2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public List<String> getFiveMostRepeatableToArray() {
        List<String>  bigramms = new ArrayList<String>(5);
        Scanner in = null;
        try {
            in = new Scanner(new File("noncrossingBigramms_" + textName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 5 && in.hasNext(); i++)        {
            bigramms.add(in.nextLine().substring(0, 2));
        }
        in.close();
        return bigramms;
    }

    public void getFiveMostRepeatable() {
        List<String> elements = getFiveMostRepeatableToArray();
        try {
            PrintWriter out = new PrintWriter(new File("mostRepeatedBirgamms").getAbsoluteFile());
            try {
                for (int i = 0; i < elements.size(); i++) {
                    out.println(elements.get(i));
                }
            } finally {
                out.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        cleanAllTails();
    }

    private void cleanAllTails (){
        File f = new File("noncrossingBigramms_" + textName);
        f.delete();
    }
}
