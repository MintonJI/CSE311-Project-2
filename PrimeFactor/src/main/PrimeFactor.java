package main;

import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PrimeFactor {


    /**
     * Asks the user to input a number that they want to factor. The input will be passed over
     * to the factorer function. Afterward, all of the factors will be printed to the console.
     * @param args
     */
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        BigInteger input = BigInteger.ONE;

        boolean inputError = false;

        // If the user doesn't input a number, they will be given an error message.
        do {
            try {
                System.out.println("Enter an integer: ");
                input = reader.nextBigInteger();

                inputError = false;
            } catch (InputMismatchException e) {
                System.err.println("This isn't an integer.\n");
                inputError = true;
                String badInput = reader.nextLine();
                continue;
            }
        } while (inputError);

        // The list of factors.
        ArrayList<BigInteger> factors = new ArrayList<>();
        factors = factorer(input);

        /*
        Prints all the factors of the input number.
         */
        System.out.print(input + ": = ");
        BigInteger lastFactor = factors.get(factors.size() - 1);
        for (BigInteger f : factors) {

            if (!f.equals(lastFactor)) {
                System.out.print(f + " * ");
            } else {
                System.out.println(f);
            }
        }
    }

    /**
     * Takes an input number of type BigInteger and calculates the prime factorization of
     * it until the last factor is found. It then prints the factors.
     *
     * @param input The user-inputted BigInteger they want to factor.
     * @return
     */
    public static ArrayList<BigInteger> factorer(BigInteger input) {
        BigInteger inputCalculations = input;
        BigInteger result = BigInteger.ONE;

        // Contains a list of the computed factors. Used for printing at the end of the method.
        ArrayList<BigInteger> factors = new ArrayList<>();

        /*
         While the results of the factoring isn't null, keep finding factors.
         */
        do {
            // Divides the input number by the previously calculated factor.
            inputCalculations = inputCalculations.divide(result);

            // The result of the Pollard's rho method.
            result = polRho(inputCalculations);

            /*
            If the result isn't null, add it to the list. If it is null, then the factor currently in inputCalculations
            is the last possible factor. Add that value to the list of factors.
             */
            if (result != null) {
                factors.add(result);
            } else {
                factors.add(inputCalculations);
            }

        } while (result != null);

        return factors;
    }

    /**
     * Our implementation of Pollard's rho algorithm. Used to compute the prime factorization
     * for an input number of type BigInteger.
     * Documentation: https://en.wikipedia.org/wiki/Pollard's_rho_algorithm
     * @param input The number the user wants to find the prime factorization of.
     * @return The prime factorization of the input or null if the computation is a failure.
     */
    public static BigInteger polRho(BigInteger input) {

        // Used to see if the difference between the two is a multiple of the input number.
        BigInteger x = BigInteger.TWO;
        BigInteger y = BigInteger.TWO;

        // The factor.
        BigInteger d = BigInteger.ONE;

        while (d.equals(BigInteger.ONE)) {

            x = g(x, input);
            y = g(g(y, input), input);
            d = gcd((x.subtract(y).abs()), input);
        }

        // If the factor is the same as the input number, computation failed.
        if (d.equals(input)) {
            return null;
        } else {
            return d;
        }
    }

    /**
     * Part of Pollard's rho algorithm.
     * Documentation same as above.
     *
     * @param x
     * @param input
     * @return
     */
    public static BigInteger g(BigInteger x, BigInteger input) {
        return (x.pow(2).add(BigInteger.ONE).remainder(input));
    }

    /**
     * Part of Pollard's rho algorithm.
     *
     * @param a
     * @param input
     * @return
     */
    public static BigInteger gcd(BigInteger a, BigInteger input) {
        return input.equals(BigInteger.ZERO) ? a : gcd(input, a.remainder(input));
    }

}
