import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class PrimeServerA {

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
        final String HOSTNAME = "ceclnx01.cec.miamiOH.edu";

        try (
                ServerSocket serverSocket =
                        new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                Socket serverSocketB = new Socket(HOSTNAME, 1025);
                Socket serverSocketC = new Socket(HOSTNAME, 1026);
                Socket serverSocketD = new Socket(HOSTNAME, 1027);

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

                /*
                We're going to divide the original integer by three and send each pair of vars
                to their respective server.
                For example, client input integer is "123456789". Divided by three, it's 41152263.
                Server B will find all factors between 1 and 41152263.
                Server C will find all factors between 41152264 and 82304526.
                Server D will find all factors between 82304527 and 123456789.
                 */
            BigInteger startNumB = BigInteger.ONE;
            BigInteger endNumB = inputNum.divide(BigInteger.valueOf(3));
            BigInteger startNumC = endNumB.add(BigInteger.ONE);
            BigInteger endNumC = endNumB.add(endNumB);
            BigInteger startNumD = endNumC.add(BigInteger.ONE);
            BigInteger endNumD = inputNum;


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