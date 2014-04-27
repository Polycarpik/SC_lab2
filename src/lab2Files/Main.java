package lab2Files;

import lab1Heritage.Counter;

import java.io.FileNotFoundException;

/**
 * Created by Polina on 11-Apr-14.
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        Counter counter = new Counter("text.txt");
        counter.getNonexhistingNgrammCounter(2);

//        MostRepeatableBigrammsCounter mrbc = new MostRepeatableBigrammsCounter("text");
//        mrbc.getFiveMostRepeatable();
    }

}
