/**
    Java ECHO client with UDP sockets example
    Silver Moon (m00n.silv3r@gmail.com)
*/

/*
 * http://www.binarytides.com/udp-socket-programming-in-java/
 */
 
import java.io.*;
import java.net.*;
 
public class udp_client
{
    public static void main(String args[])
    {
        DatagramSocket sock = null;
        int port = 7777;
        String s;
         
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
         
        try
        {
            sock = new DatagramSocket();
             
            InetAddress host = InetAddress.getByName("localhost");
             
            while(true)
            {
                //take input and send the packet
                echo("Enter message to send : ");
                s = (String)cin.readLine();
                byte[] b = s.getBytes();
                 
                DatagramPacket  dp = new DatagramPacket(b , b.length , host , port);
                sock.send(dp);
                 
                //now receive reply
                //buffer to receive incoming data
                byte[] buffer = new byte[65536];
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                sock.receive(reply);
                 
                byte[] data = reply.getData();
                s = new String(data, 0, reply.getLength());
                 
                //echo the details of incoming data - client ip : client port - client message
                echo(reply.getAddress().getHostAddress() + " : " + reply.getPort() + " - " + s);
            }
        }
         
        catch(IOException e)
        {
            System.err.println("IOException " + e);
        }
    }
     
    //simple function to echo data to terminal
    public static void echo(String msg)
    {
        System.out.println(msg);
    }
}