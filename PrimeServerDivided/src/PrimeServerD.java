import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class PrimeServerD {

    /**
     * Takes an input number of type BigInteger and calculates the prime factorization of it until the last factor
     * is found. It then prints the factors.
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        try (
                ServerSocket serverSocket =
                        new ServerSocket(Integer.parseInt(args[0]));
                Socket serverSocketA = serverSocket.accept();
                PrintWriter out =
                        new PrintWriter(serverSocketA.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(serverSocketA.getInputStream()));
        ) {
            String inputLine;
            BigInteger inputNum;
            ArrayList<BigInteger> factors = new ArrayList<BigInteger>();
            inputLine = in.readLine();
            inputNum = new BigInteger(inputLine);


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
}