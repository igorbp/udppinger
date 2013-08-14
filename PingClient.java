// File name: PingClient.java
import java.util.*;
import java.net.*;
import java.text.*;

public class PingClient
{
    private static final int TOKEN_TIMESTAMP = 2;
    private static final String CRLF = "\r\n";

    public static void main(String[] args) throws Exception
    {
        // Requires host and port number.
        if (args.length != 2)
        {
            System.out.println("You need to inform host and port. (PingClient host port)");
            return;
        } else {
            System.out.println("\nHost: "+args[0]+"\nPort number: "+args[1]+"\n");
        }
       
        // Get host by his name
        InetAddress host = InetAddress.getByName(args[0]);
        
        // Get informed port
        int portNumber = Integer.parseInt(args[1]);
        
        // Create a datagram socket used for sending and recieving packets
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(1000);

        // Start loop to send packets 
        for (int i = 0; i < 20; i++)
        {
                // Create ping message 
                long sent_time = System.currentTimeMillis();
                String ping_message = pingMessage(i);

                // Create send packet
                DatagramPacket send_packet =
                    new DatagramPacket(ping_message.getBytes(), ping_message.length(), host, portNumber);

                //Send ping request
                socket.send(send_packet);
                System.out.print(ping_message);

                //Datagram packet for the server response
                DatagramPacket receive_packet =
                    new DatagramPacket(new byte[1024], 1024);

                //Wait for ping response
                try
                {
                        // Response received
                        socket.receive(receive_packet);
                        long received_time = System.currentTimeMillis();
                        System.out.println("Response received from "+
                            receive_packet.getAddress().getHostAddress() + " "+"(time=" + (received_time - sent_time) + "ms)");
                }
                // Catch timeout exception
                catch (SocketTimeoutException e)
                {
                        System.out.println("Timeout");
                }
                // Catch other exceptions
                catch (Exception e)
                {
                        System.out.println(e.getMessage());
                        return;
                }

        }
    }

    private static String pingMessage(int i)
    {
        return "ping " + i + " ";
    }

}