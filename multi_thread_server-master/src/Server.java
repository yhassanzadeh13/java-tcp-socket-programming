// echo server

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;


public class Server
{
    private ServerSocket serverSocket;
    public static final int DEFAULT_SERVER_PORT = 4444;
    /**
     * Initiates a server socket on the input port, listens to the line, on receiving an incoming
     * connection creates and starts a ServerThread on the client
     * @param port
     */
    public Server(int port)
    {
        try
        {
            serverSocket = new ServerSocket(port);
            System.out.println("Oppened up a server socket on " + Inet4Address.getLocalHost());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.err.println("Server class.Constructor exception on oppening a server socket");
        }
        while (true)
        {
            ListenAndAccept();
        }
    }

    /**
     * Listens to the line and starts a connection on receiving a request from the client
     * The connection is started and initiated as a ServerThread object
     */
    private void ListenAndAccept()
    {
        Socket s;
        try
        {
            s = serverSocket.accept();
            System.out.println("A connection was established with a client on the address of " + s.getRemoteSocketAddress());
            ServerThread st = new ServerThread(s);
            st.start();

        }

        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println("Server Class.Connection establishment error inside listen and accept function");
        }
    }

}

