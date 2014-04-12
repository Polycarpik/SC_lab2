package lab2Files;

/**
 * Created by Polina on 11-Apr-14.
 */
public class ObligatoryMath {

    public int GCD(int a, int n) {
        if (n == 0) return a;
        return GCD(n, a % n);
    }

    public void extendedGCD(){}

    public void getInverseElement(){}

    public void solvineLinearComparisons(){}


}
