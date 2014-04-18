package lab2Files;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Polina on 11-Apr-14.
 */
public class ObligatoryMath {

    private List<Integer> multiplicities = new LinkedList<Integer>();

    public int gcd(int a, int n) {
        if (a > 1) {
            multiplicities.add((n - n % a) / a);
        }
        if (a == 0) {
            return n;
        }
        return gcd(n % a, a);
    }


    public int getInverseElement(int number, int module) {
        gcd(number, module);
        int[] p = new int[multiplicities.size() + 2];
        p[0] = 0;
        p[1] = 1;
        for (int i = 0; i < multiplicities.size(); i++) {
            p[i + 2] = -multiplicities.get(i) * p[i + 1] + p[i];
        }
        if (p[p.length - 1] < 0) {
            p[p.length - 1] = module + p[p.length - 1];
        }
        return p[p.length - 1];
    }


    public int[] solvingLinearComparisons(int a, int b, int n) {
        int d = gcd(a, n);
        if (d == 1) {
            int[] result = new int[1];
            result[0] = (getInverseElement(a, n) * b) % n;
            return result;
        } else {
            if (b % d == 0) {
                int a1 = a / d;
                int b1 = b / d;
                int n1 = n / d;
                int[] result = new int[d];
                for(int i = 0; i<result.length; i++){
                    result[i] = (((getInverseElement(a1, n1) * b1) % n1) + d*n1)%n;
                }
                return result;
            } else { return null;}
        }

    }


}
