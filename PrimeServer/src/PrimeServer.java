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

    /**
     * Establishes a socket connection with a client (using a user-specified port) and
     * accepts user input (a BigInteger) from the client (PrimeClient.java). When the server
     * receives the input, it will compute the prime factorization and send the output
     * to the client.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        try (
                ServerSocket serverSocket =
                        new ServerSocket(Integer.parseInt(args[0]));

                // Waits until connection is established with the client.
                Socket clientSocket = serverSocket.accept();

                // Initializes PrintWriter object for output.
                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);

                // Initializes BufferedReader objects for input.
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()))
        ) {
            String inputLine;
            BigInteger inputNum;

            // A list of all of the factors of the input.
            ArrayList<BigInteger> factors;
            inputLine = in.readLine();
            inputNum = new BigInteger(inputLine);

            // Receives a list of factors from the factorer.
            factors = factorer(inputNum);

            // Gets the lastFactor of the list. Used for formatting during printing.
            BigInteger lastFactor = factors.get(factors.size() - 1);

            // Prints all the factors of the list fot the client.
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

    /**
     * This method adds the computed factors to the final list of factors.
     * @param input The client's BigInteger object that will be factored.
     * @return
     */
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
     * @return
     */
    public static BigInteger g(BigInteger x, BigInteger input) {
        return (x.pow(2).add(BigInteger.ONE).remainder(input));
    }

    /**
     * Part of Pollard's rho algorithm. Computes the Greatest Common Denominator of the input.
     *
     * @param a
     * @param input
     * @return
     */
    public static BigInteger gcd(BigInteger a, BigInteger input) {
        return input.equals(BigInteger.ZERO) ? a : gcd(input, a.remainder(input));
    }
}
