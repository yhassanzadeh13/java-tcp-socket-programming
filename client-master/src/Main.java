import java.util.Scanner;

public class Main
{
    public static void main(String args[])
    {
        ConnectionToServer connectionToServer = new ConnectionToServer(ConnectionToServer.DEFAULT_SERVER_ADDRESS, ConnectionToServer.DEFAULT_SERVER_PORT);
        connectionToServer.Connect();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a message for the echo");
        String message = scanner.nextLine();
        while (!message.equals("QUIT"))
        {
            System.out.println("Response from server: " + connectionToServer.SendForAnswer(message));
            message = scanner.nextLine();
        }
        connectionToServer.Disconnect();
    }
}
