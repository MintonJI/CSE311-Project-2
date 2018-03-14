import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static org.junit.Assert.*;

public class PrimeFactorTest {

    // Tests for factoring a normal input.
    @Test
    public void inputNormal() {
        ArrayList<BigInteger> expectedFactors = new ArrayList<BigInteger>();
        expectedFactors.add(BigInteger.valueOf(3));
        expectedFactors.add(BigInteger.valueOf(3));
        expectedFactors.add(BigInteger.valueOf(3607));
        expectedFactors.add(BigInteger.valueOf(3803));

        ArrayList<BigInteger> actualFactors = PrimeFactor.factorer(BigInteger.valueOf(123456789));

        assertEquals(expectedFactors, actualFactors);
    }

    // Testing for factoring a negative input.
    @Test
    public void inputNegative() {
        ArrayList<BigInteger> expectedFactors = new ArrayList<BigInteger>();
        expectedFactors.add(BigInteger.valueOf(-3));
        expectedFactors.add(BigInteger.valueOf(3));
        expectedFactors.add(BigInteger.valueOf(3607));
        expectedFactors.add(BigInteger.valueOf(3803));

        ArrayList<BigInteger> actualFactors =
                PrimeFactor.factorer(BigInteger.valueOf(123456789).negate());

        assertEquals(expectedFactors, actualFactors);
    }

    // Testing for inputting zero into the factorer.
    @Test(expected = ArithmeticException.class)
    public void inputZero() {
        ArrayList<BigInteger> actualFactors = PrimeFactor.factorer(BigInteger.ZERO);
    }
}