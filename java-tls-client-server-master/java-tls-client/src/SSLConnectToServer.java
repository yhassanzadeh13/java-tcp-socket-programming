import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;
import java.security.KeyStore;

/**
 * Copyright [2017] [Yahya Hassanzadeh-Nazarabadi]

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

/**
 * This class handles and establishes an SSL connection to a server
 */
public class SSLConnectToServer
{
    /*
    Name of key store file
     */
    private final String KEY_STORE_NAME =  "clientkeystore";
    /*
    Password to the key store file
     */
    private final String KEY_STORE_PASSWORD = "storepass";
    private SSLSocketFactory sslSocketFactory;
    private SSLSocket sslSocket;
    private BufferedReader is;
    private PrintWriter os;

    protected String serverAddress;
    protected int serverPort;

    public SSLConnectToServer(String address, int port)
    {

        serverAddress = address;
        serverPort = port;
        /*
        Loads the keystore's address of client
         */
        System.setProperty("javax.net.ssl.trustStore", KEY_STORE_NAME);

        /*
        Loads the keystore's password of client
         */
        System.setProperty("javax.net.ssl.trustStorePassword", KEY_STORE_PASSWORD);
    }

    /**
     * Connects to the specified server by serverAddress and serverPort
     */
    public void Connect()
    {
        try
            {
                sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
                sslSocket = (SSLSocket) sslSocketFactory.createSocket(serverAddress, serverPort);
                sslSocket.startHandshake();
                is=new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
                os= new PrintWriter(sslSocket.getOutputStream());
                System.out.println("Successfully connected to " + serverAddress + " on port " + serverPort);
            }
        catch (Exception e)
            {
                e.printStackTrace();
            }
    }


    /**
     * Disconnects form the specified server
     */
    public void Disconnect()
    {
        try
            {
                is.close();
                os.close();
                sslSocket.close();
            }
        catch (IOException e)
            {
                e.printStackTrace();
            }
    }

    /**
     * Sends a message as a string over the secure channel and receives
     * answer from the server
     * @param message input message
     * @return response from server
     */
    public String SendForAnswer(String message)
    {
        String response = new String();
        try
        {
            os.println(message);
            os.flush();
            response = is.readLine();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            System.out.println("ConnectionToServer. SendForAnswer. Socket read Error");
        }
        return response;
    }


}
