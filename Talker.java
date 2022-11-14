import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Talker {

    public static void main(String a[]) throws Exception{

        DatagramSocket dataSocket = new DatagramSocket(20); 
        byte[] b = new byte[1024];
        DatagramPacket dataPacket = new DatagramPacket(b, b.length);
        dataSocket.receive(dataPacket);

        String message = new String(dataPacket.getData());
        System.out.println("Message on Talker side : "+ message);

        String newMessage = "Message Received on Talker!";
        byte[] b1 = newMessage.getBytes();
        InetAddress address = InetAddress.getLocalHost();
        DatagramPacket dataPacket1 = new DatagramPacket(b1, b1.length, address, dataPacket.getPort());
        dataSocket.send(dataPacket1);
        dataSocket.close();
    }
}