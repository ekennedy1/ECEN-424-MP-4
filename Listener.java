import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;


public class Listener {

    public static void main(String[] args) throws Exception {

        //Scanner keyboard = new Scanner(System.in);
        //System.out.print("Enter Listener Port Number: ");                                                                       // enter listener's port number
        //int portNum = keyboard.nextInt();

        DatagramSocket dataSocket = new DatagramSocket();                                      // 
        InetAddress address = InetAddress.getByName("127.0.0.1");
        //System.out.println(address.getHostName());
        

        String outgoingMessage = "Listener requests a UDP connection!";                                                          // sending message "Hello"
        byte[] outgoingBytes = outgoingMessage.getBytes();
        
        DatagramPacket dataPacketOut = new DatagramPacket(outgoingBytes, outgoingBytes.length, address, 20);               // for sending data "Hello"
        dataSocket.send(dataPacketOut);                                                                                         // data send

        byte[] incomingBytes = new byte[1024];
        String incomingMessage = ""; 
        while (incomingMessage.length() == 0) {
            DatagramPacket dataPacketIn = new DatagramPacket(incomingBytes, incomingBytes.length);                              // receiving data not need address or port number
            dataSocket.receive(dataPacketIn);                                                                                   // receive data

            incomingMessage = new String(dataPacketIn.getData());
            System.out.println("Received message on listener : " + incomingMessage);
        }
        /* incomingMessage = "";
        while (incomingMessage.length() == 0) {
            DatagramPacket dataPacketIn = new DatagramPacket(incomingBytes, incomingBytes.length);                              // receiving data not need address or port number
            dataSocket.receive(dataPacketIn);                                                                                   // receive data

            incomingMessage = new String(dataPacketIn.getData(), 0, dataPacketIn.getLength());
            System.out.println("Received message on listener 2: " + incomingMessage);
        } */

        //receive frames
        int i = 1;
        incomingMessage = "";
        String sentence = "";
        String ACK = "";
        while (incomingMessage.length() == 0) {
            DatagramPacket dataPacketIn = new DatagramPacket(incomingBytes, incomingBytes.length);                              // receiving data not need address or port number
            dataSocket.receive(dataPacketIn);
            incomingMessage = new String(dataPacketIn.getData(), 0, dataPacketIn.getLength());
            ACK = "ACK" + String.valueOf(i) + "\n";
            System.out.println(ACK.strip());                                    ///////// printing statement
            // send out the ACK1
            outgoingMessage = ACK;                                                          // sending message "Hello"
            outgoingBytes = outgoingMessage.getBytes();
        
            dataPacketOut = new DatagramPacket(outgoingBytes, outgoingBytes.length, address, 20);               // for sending data "Hello"
            dataSocket.send(dataPacketOut);
        }
        
        
        int numberOfFrames = Integer.parseInt(incomingMessage.strip());
        while (i <= (numberOfFrames)) {
            i += 1;
            DatagramPacket dataPacketIn = new DatagramPacket(incomingBytes, incomingBytes.length);  
            dataSocket.receive(dataPacketIn);
            incomingMessage = new String(dataPacketIn.getData(), 0, dataPacketIn.getLength());
            sentence = sentence + incomingMessage;

            ACK = "ACK" + String.valueOf(i) + "\n";
            System.out.println(ACK.strip());                                    ///////// printing statement
            // send out the ACKs
            outgoingMessage = ACK;                                                          // sending message "Hello"
            outgoingBytes = outgoingMessage.getBytes();
        
            dataPacketOut = new DatagramPacket(outgoingBytes, outgoingBytes.length, address, 20);               // for sending data "Hello"
            dataSocket.send(dataPacketOut);

        }
        System.out.println(sentence);
        dataSocket.close();
    }

    public static String packet(String frame) {
        char c;
        String word = "";
        for (int i=1; i < frame.length(); i++ ) {
            c = frame.charAt(i);
            word = word + c;
        }
        return word;
    }
}