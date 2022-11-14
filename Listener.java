import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class Listener {

    public static void main(String[] args) throws Exception {

        DatagramSocket dataSocket = new DatagramSocket();

        String message = "Hello";
        byte[] b = message.getBytes();
        InetAddress address = InetAddress.getLocalHost();
        
        DatagramPacket dataPacket = new DatagramPacket(b, b.length, address, 20);               // for sending data
        dataSocket.send(dataPacket);                                                                 // data send

        byte[] b1 = new byte[1024]; 
        DatagramPacket dataPacket1 = new DatagramPacket(b1, b1.length);                              // receiving data not need address or port number
        dataSocket.receive(dataPacket1);                                                             // receive data

        String receivedMessage = new String(dataPacket1.getData());
        System.out.println("Received message on listener : " + receivedMessage);
        dataSocket.close();
    }

}