import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;
import java.util.Scanner;

public class Talker {

    public static void main(String a[]) throws Exception{

        //Scanner keyboard = new Scanner(System.in);
        //System.out.print("Enter Talker Port Number: ");                                                  // enter Talker's port number
        //int portNum = keyboard.nextInt();

        String sentence = "This whole sentence is precisely fifty characters.";
        DatagramSocket dataSocket = new DatagramSocket(20);                                           // created with port num
        byte[] incomingBytes = new byte[1024];                                                                         // for receiving data
        byte[] outgoingBytes = new byte[1024];
        String incomingMessage = "";
        String outgoingMessage = "";
        String[] frame = new String[6];
        InetAddress IPListener = null;
        int port = 0;
        System.out.println("Talker waiting on Listener..."); 


        while (incomingMessage.length() == 0) {
            DatagramPacket dataPacketIn = new DatagramPacket(incomingBytes, incomingBytes.length);                                   // receiving a packet
            dataSocket.receive(dataPacketIn);

            incomingMessage = new String(dataPacketIn.getData());
            System.out.println(incomingMessage);                                                                   // printout the message

            IPListener = dataPacketIn.getAddress();                                               // IP extraction
            port = dataPacketIn.getPort();                                                               // port num extraction
            //System.out.println(IPAddress.getHostName());                                                   // print IP + port num

            outgoingMessage = "Talker requests a UDP connection!";
            outgoingBytes = outgoingMessage.getBytes();
            DatagramPacket dataPacketOut = new DatagramPacket(outgoingBytes, outgoingBytes.length, IPListener, port);
            dataSocket.send(dataPacketOut);

        }
        /* incomingMessage = "";
        while ((true)) {
            outgoingMessage = "Talker talks";
            outgoingBytes = outgoingMessage.getBytes();
            DatagramPacket dataPacketOut = new DatagramPacket(outgoingBytes, outgoingBytes.length, IPListener, port);
            dataSocket.send(dataPacketOut);

        } */


        //frame the sentance into a String Array
        frame = Frames(sentence);

        int nextAckNum = 0;
        int ack = 0;
        int previousAck = -1;
        Date date0 = new Date();
        long starTime = date0.getTime();
        long finishTime = starTime;
        while (nextAckNum < 6) {
            outgoingMessage = frame[nextAckNum];                                         // send the ith frame
            outgoingBytes = outgoingMessage.getBytes();
            DatagramPacket dataPacketOut = new DatagramPacket(outgoingBytes, outgoingBytes.length, IPListener, port);
            dataSocket.send(dataPacketOut);

            // receiving ACK
            DatagramPacket dataPacketIn = new DatagramPacket(incomingBytes, incomingBytes.length);                                   // receiving a packet
            dataSocket.receive(dataPacketIn);

            incomingMessage = new String(dataPacketIn.getData(), 0, dataPacketIn.getLength());
            try {
                nextAckNum = nextFrameFromACK(incomingMessage);
                ack = (nextAckNum-1);
                System.out.println("ACK received: " + String.valueOf(ack));           ////////////////printing statement
            } 
            catch (NumberFormatException e) {
                System.out.println("ACK not received");
                continue;
            }

        }
        dataSocket.close();
        
    }
    //This function accepts a 50-character String and break it down to 5 frames + zero frame with number of frames
    public static String[] Frames(String message) {
        int num = message.length();
    
            int j = 0;
            boolean startNewPacket = false;
            String s = "";
            char c;
            String[] frame = new String[6];
            frame[0] = String.valueOf((frame.length - 1));
            for (int i=0; i<num; i++) {
                startNewPacket = (((i+1) % 10) == 0);
    
                c = message.charAt(i);
                s = s + c;  
    
                if (startNewPacket) {
                    j += 1;
                    frame[j] = s;
                    s = "";
    
                } 
                
            }
            return frame;
    }
    
    // this function accepts a String type ACK (example - "ACK5\n") and return the next frame index int
    public static int nextFrameFromACK(String ACK) {

        int i = 0;
        boolean isNum = false;
        String num = "";
        int Num = 0;
        char c = '0';
        while (c != '\n') {
            c = ACK.charAt(i);

            if (isNum) {num = num + c; }
            if (c == 'K') {isNum = true;}
            i += 1;

        }
        num = num.strip();
        Num = Integer.parseInt(num);
        return Num;
    }
}