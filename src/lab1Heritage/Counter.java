package lab1Heritage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by Polina on 3/11/14.
 */
public class Counter {

    private String textName;
    private String text = "";
    private double entropy;

    private class Element {

        private Element(String key, Integer value) {
            this.key = key;
            this.value = value;
        }

        Double probability;
        Integer value;
        String key;

    }

    public Counter(String textName) throws FileNotFoundException {
        this.textName = textName;
        this.text = "";
        Scanner in = new Scanner(new File(textName));
        while (in.hasNext()) {
            this.text += in.nextLine();
        }
        in.close();
        entropy = helper();
    }

    private HashMap<String, Integer> ngrammCounter(int step, int gramm) {
        HashMap<String, Integer> holder = new HashMap<String, Integer>();
        int length = this.text.length();

        for (int i = 0; i + 1 < length; i += step) {
            String bigramm = text.substring(i, i + gramm);
            if (!holder.containsKey(bigramm)) {
                holder.put(bigramm, 1);
            } else {
                int a = holder.remove(bigramm);
                holder.put(bigramm, a + 1);
            }

        }
        return holder;
    }

    private List<Element> hashMapToArrayOfElements(HashMap<String, Integer> holder) {
        int total = 0;
        List<Element> elements = new ArrayList<Element>();
        Set<String> bigramms = holder.keySet();
        for (String a : bigramms) {
            total += holder.get(a);
            Element e = new Element(a, holder.get(a));
            elements.add(e);
        }
        elements.add(new Element("total", total + 1));
        return elements;
    }

    private void countingProbability(List<Element> elements) {
        int total = elements.get(elements.size() - 1).value;
        for (int i = 0; i < elements.size() - 1; i++) {

            Element a = new Element(elements.get(i).key, elements.get(i).value);
            a.probability = (double) a.value / total;
            elements.set(i, a);
        }
        elements.remove(elements.size() - 1);
        Collections.sort(elements, new Comparator<Element>() {
            public int compare(Element o1, Element o2) {
                return -(o1.probability.compareTo(o2.probability));
            }
        });
    }

    private double entropyCounter(List<Element> elements, int gramm) {
        double entropy = 0;
        for (int i = 0; i < elements.size(); i++) {
            entropy += elements.get(i).probability * (Math.log(elements.get(i).probability) / Math.log(2));
        }
        return (-entropy / gramm);
    }


    private double helper(){
        List<Element> elements = hashMapToArrayOfElements(ngrammCounter(1, 2));
        countingProbability(elements);
        return  entropyCounter(elements,2);
    }

    public double getEntropy() {
        return entropy;
    }

    private void toFile(String addon, String fileName, List<Element> elements, int gramm) {
        try {
            PrintWriter out = new PrintWriter(new File(addon + fileName).getAbsoluteFile());
            try {
                for (int i = 0; i < elements.size(); i++) {
                    out.println(elements.get(i).key + " = " + elements.get(i).probability);
                }
                out.println("entropy = " + entropyCounter(elements, gramm));
            } finally {
                out.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void letterCounter() {
        List<Element> elements = hashMapToArrayOfElements(ngrammCounter(1, 1));
        countingProbability(elements);
        toFile("letters_", textName, elements, 1);
    }

    public void crossingNgrammCounter(int n) {
        List<Element> elements = hashMapToArrayOfElements(ngrammCounter(1, n));
        countingProbability(elements);
        toFile("crossingNgramms_", textName, elements, n);
    }

    public void noncrossingNgrammCounter(int n) {
        List<Element> elements = hashMapToArrayOfElements(ngrammCounter(n, n));
        countingProbability(elements);
        toFile("noncrossingNgramms_", textName, elements, n);
    }

    public List<String> getExhistingNgramms(int n) {
        List<Element> elements = hashMapToArrayOfElements(ngrammCounter(1, n));
        List<String> list = new ArrayList<String>(elements.size());
        for (int i = 0; i < elements.size() - 1; i++) {
            list.add(elements.get(i).key);
        }
        return list;
    }

    public List<String> getAllNgrammsFromAlphabet(String fileWithAlphabetName, int n) throws FileNotFoundException {
        List<String> alphabet = new ArrayList<String>();
        Scanner scanner = new Scanner(new File(fileWithAlphabetName));
        while (scanner.hasNext()) {
            alphabet.add(scanner.next());
        }
        scanner.close();
        List<String> allNgramms = alphabet;
        while (n > 1) {
            List<String> temp = new ArrayList<String>();
            for (int i = 0; i < allNgramms.size(); i++) {
                for (int j = 0; j < alphabet.size(); j++) {
                    temp.add(allNgramms.get(i) + alphabet.get(j));
                }
            }
            allNgramms = temp;
            n--;
        }
        return allNgramms;
    }

    public List<String> getNonexhistingNgrammCounter(String alphabetName, int n) throws FileNotFoundException {
        List<String> exhistingNgramms = getExhistingNgramms(n);
        List<String> nonExgistingNgramms = getAllNgrammsFromAlphabet(alphabetName, n);
        for (int i = 0; i < exhistingNgramms.size(); i++) {
            if (nonExgistingNgramms.contains(exhistingNgramms.get(i))) {
                nonExgistingNgramms.remove(exhistingNgramms.get(i));
            }
        }
        return nonExgistingNgramms;
    }

}
