/**
 * Takes an input number of type BigInteger and calculates the prime factorization of it until the last factor
 * is found. It then prints the factors.
 *
 * @param args
 */

import java.net.*;
import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;

public class PrimeServer {

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        try (
                ServerSocket serverSocket =
                        new ServerSocket(Integer.parseInt(args[0]));
                Socket clientSocket = serverSocket.accept();
                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String inputLine;
            BigInteger inputNum;
            ArrayList<BigInteger> factors = new ArrayList<BigInteger>();
            inputLine = in.readLine();
            inputNum = new BigInteger(inputLine);
            factors = factorer(inputNum);


            BigInteger lastFactor = factors.get(factors.size() - 1);

            for (BigInteger f : factors) {
                if (!f.equals(lastFactor)) {
                    out.print(f + " * ");
                } else {
                    out.println(f);
                }
            }

        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + "or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<BigInteger> factorer(BigInteger input) {

        BigInteger inputEdit = input;
        BigInteger result = BigInteger.valueOf(1);

        // Contains a list of the computed factors. Used for printing at the end of the method.
        ArrayList<BigInteger> factors = new ArrayList<BigInteger>();

        /*
         While the results of the factoring isn't null, keep finding factors.
         */
        do {
            // Divides the input number by the previously calculated factor.
            inputEdit = inputEdit.divide(result);

            // The result of the Pollard's rho method.
            result = polRho(inputEdit);

            /*
            If the result isn't null, add it to the list. If it is null, then the factor currently in exampleTested
            is the last possible factor. Add that value to the list of factors.
             */
            if (result != null) {
                factors.add(result);
            } else {
                factors.add(inputEdit);
            }

        } while (result != null);

        return factors;
    }

    /**
     * Our implementation of Pollard's rho algorithm. Used to compute the prime factorization
     * for an input number of type BigInteger.
     * Documentation: https://en.wikipedia.org/wiki/Pollard's_rho_algorithm
     *
     * @param input The number the user wants to find the prime factorization of.
     * @return The prime factorization of the input or null if the computation is a failure.
     */
    public static BigInteger polRho(BigInteger input) {

        // Used to see if the difference between the two is a multiple of the input number.
        BigInteger x = BigInteger.valueOf(2);
        BigInteger y = BigInteger.valueOf(2);

        // The factor.
        BigInteger d = BigInteger.ONE;

        while (d.equals(BigInteger.ONE)) {

            x = g(x, input);
            y = g(g(y, input), input);
            d = gcd((x.subtract(y).abs()), input);
        }

        // If the factor is the same as the input number, computation failed.
        if (d == input) {
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
