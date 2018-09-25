// echo server

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;


public class SingleServer
{
    public static final String DEFAULT_SERVER_ADDRESS = "localhost";
    public static final int DEFAULT_SERVER_PORT = 4444;
    private ServerSocket serverSocket;
    private BufferedReader is;
    private PrintWriter os;
    private Socket s;

    /**
     * Initiates a server socket on the input port and keeps listening on the line
     * @param port
     */
    public SingleServer(int port)
    {
        try
        {
            /*
            Opens up a server socket on the specified port and listens
             */
            serverSocket = new ServerSocket(port);
            System.out.println("Oppened up a server socket on " + port);
        }
        catch (IOException e)
        {
            //e.printStackTrace();
            System.err.println("Cannot open a server socket on port " + port);
        }
        while (true)
        {
            ListenAndAccept();
        }
    }

    /**
     *  Listens to the line and starts a connection on receiving a request with the client
     */
    private void ListenAndAccept()
    {

        try
        {
            /*
            Casts a server socket to an ordinary socket
             */
            s = serverSocket.accept();
            System.out.println("A connection was established with a client on the address of " + s.getRemoteSocketAddress());
            is = new BufferedReader(new InputStreamReader(s.getInputStream()));
            os = new PrintWriter(s.getOutputStream());

            String line = is.readLine();
            while (line.compareTo("QUIT") != 0)
            {

                os.println(line);
                os.flush();
                System.out.println("Client " + s.getRemoteSocketAddress() + " sent : " + line);
                line = is.readLine();
            }

        }
        catch (Exception e)
        {
            //e.printStackTrace();
            System.err.println("Exception on listen and accept function on reading the line");
        } finally
        {
            try
            {
                System.out.println("Closing the connection");
                if (is != null)
                {
                    is.close();
                    System.out.println(" Socket Input Stream Closed");
                }

                if (os != null)
                {
                    os.close();
                    System.out.println("Socket Out Closed");
                }
                if (s != null)
                {
                    s.close();
                    System.out.println("Socket Closed");
                }

            }
            catch (IOException ie)
            {
                System.out.println("Socket Close Error");
            }
        }//end finally
    }

}