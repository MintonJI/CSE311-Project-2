
import java.math.BigInteger;
import java.math.BigDecimal;
public class PrimeFactor {


    public static BigDecimal factor(BigDecimal input) {
        BigDecimal output;

        BigDecimal inputSquare = sqRoot(input);

    }

    /**
     *
     * @param num is the BigDecimal number for which we are going to find the square root
     * @return  the BigDecimal number which is an approximation to the square root of num
     * 			The accuracy of this approximation is determined by the value of the delta
     * 			This method uses an iterative method that is analogous to Netwton's method of finding roots.
     * @author  James Kiper. PhD
     * @date 	February 22, 2018
     *
     */

    public static BigDecimal sqRoot( BigDecimal num) {
        BigDecimal approx1;
        BigDecimal approx2;
        approx1 = num;
        approx2 = new BigDecimal("1.0");
        BigDecimal two = new BigDecimal("2.0");
        BigDecimal delta = new BigDecimal("0.5");
        while (  ( approx1.subtract(approx2)).abs().compareTo(delta) > 0 ) {
            approx1 = (approx1.add(approx2).divide(two, 2));
            approx2 = (num.divide(approx1, 2));
        }
        return approx2;
    }


}
