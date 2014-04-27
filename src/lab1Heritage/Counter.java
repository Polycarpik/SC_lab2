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
    private String text;

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
    }

    private HashMap<String, Integer> counter(int step, int gramm) {
        HashMap<String, Integer> holder = new HashMap<String, Integer>();
        int length = this.text.length();
        for (int i = 0; i + 1 < length; i += step) {
            String bigramm = this.text.substring(i, i + gramm);
            if (!holder.containsKey(bigramm)) {
                holder.put(bigramm, 1);
            } else {
                int a = holder.remove(bigramm);
                holder.put(bigramm, a + 1);
            }

        }
        return holder;
    }

//    public Object[] getArrayFromHashMap(HashMap<String, Integer> holder) {
//        Set<Map.Entry<String, Integer>> representationsHashSet = new HashSet<Map.Entry<String, Integer>>();
//        representationsHashSet = holder.entrySet();
//        Object[] a = representationsHashSet.toArray();
//        for (int i = 0; i < a.length; i++) {
//            System.out.println(a[i]);
//        }
//        return a;
//    }

    private List<Element> hashMapToArrayOfElements(HashMap<String, Integer> holder) {
        int total = 0;
        List<Element> elements = new ArrayList<Element>();
        Set<String> bigramms = holder.keySet();
        for (String a : bigramms) {
            total += holder.get(a);
            Element e = new Element(a, holder.get(a));
            elements.add(e);
        }
        elements.add(new Element("total",total + 1));
        return elements;
    }

    private void countingProbability(List<Element> elements) {
        int total = elements.get(elements.size()-1).value;
        for (int i = 0; i < elements.size() - 1; i++) {

            Element a = new Element(elements.get(i).key, elements.get(i).value);
            a.probability = (double) a.value / total;
            elements.set(i, a);
        }
//        System.out.println("Total is " + total);
        elements.remove(elements.size()-1);
        Collections.sort(elements, new Comparator<Element>() {
            public int compare(Element o1, Element o2) {
                return -(o1.probability.compareTo(o2.probability));
            }
        });
//        for (int i = 0; i < elements.size(); i++) {
//            System.out.println(elements.get(i).key + " = " + elements.get(i).value);
//            System.out.println(elements.get(i).key + " = " + elements.get(i).probability);
//
//        }
    }

    private double entropyCounter(List<Element> elements, int gramm) {
        double entropy = 0;
        for (int i = 0; i < elements.size(); i++) {
            entropy += elements.get(i).probability * (Math.log(elements.get(i).probability) / Math.log(2));
        }
        return (-entropy / gramm);
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
        List<Element> elements = hashMapToArrayOfElements(counter(1, 1));
        countingProbability(elements);
        toFile("letters_", textName, elements, 1);
    }

    public void crossingNgrammCounter(int n){
        List<Element> elements = hashMapToArrayOfElements(counter(1, n));
        countingProbability(elements);
        toFile("crossingNgramms_", textName, elements, n);
    }
    public void noncrossingNgrammCounter(int n){
        List<Element> elements = hashMapToArrayOfElements(counter(n, n));
        countingProbability(elements);
        toFile("noncrossingNgramms_", textName, elements, n);
    }

    public List<String> getExhistingNgramms (int n) {
        List<Element> elements = hashMapToArrayOfElements(counter(1,n));
        List<String> list = new ArrayList<String>(elements.size());
        for( int i = 0; i< elements.size() - 1; i++){
            list.add(elements.get(i).key);
        }
        return list;
    }

    public void getNonexhistingNgrammCounter (int n) { //not sure it works properly
        List<String> letters = getExhistingNgramms(1);
        List<String> ngramms = getExhistingNgramms(n);
        List<String> res = new ArrayList<String>();
        for (int i = 0; i < n; i++){
            for (int j = 0; j < letters.size(); j++){
                res.add(letters.get(i) + letters.get(j));
            }
        }
        for (int i = 0; i< ngramms.size(); i++){
            System.out.println(ngramms.get(i));
//            if(res.contains(ngramms.get(i))){
//                res.remove(ngramms.get(i));
//            }
        }
        for(int i = 0; i< res.size(); i++){
            System.out.println(res.get(i) + "   " + ngramms.get(i));
        }
    }

}
