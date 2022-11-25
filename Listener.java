import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;


public class Listener {

    public static void main(String[] args) throws Exception {

        //Scanner keyboard = new Scanner(System.in);
        //System.out.print("Enter Listener Port Number: ");                                           // enter listener's port number
        //int portNum = keyboard.nextInt();

        DatagramSocket dataSocket = new DatagramSocket();                                      // 
        InetAddress address = InetAddress.getByName("127.0.0.1");
        //System.out.println(address.getHostName());
        

        String message = "Listener says Hello";                                                      // sending message "Hello"
        byte[] b = message.getBytes();
        
        DatagramPacket dataPacket = new DatagramPacket(b, b.length, address, 20);               // for sending data "Hello"
        dataSocket.send(dataPacket);                                                                 // data send

        byte[] b1 = new byte[1024]; 
        DatagramPacket dataPacket1 = new DatagramPacket(b1, b1.length);                              // receiving data not need address or port number
        dataSocket.receive(dataPacket1);                                                             // receive data

        String receivedMessage = new String(dataPacket1.getData());
        System.out.println("Received message on listener : " + receivedMessage);
        dataSocket.close();
    }

}