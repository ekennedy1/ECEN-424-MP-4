import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Talker {

    public static void main(String a[]) throws Exception{

        //Scanner keyboard = new Scanner(System.in);
        //System.out.print("Enter Talker Port Number: ");                                                  // enter Talker's port number
        //int portNum = keyboard.nextInt();

        String sentence = "This whole sentence is precisely fifty characters.";
        DatagramSocket dataSocket = new DatagramSocket(20);                                           // created with port num
        byte[] b = new byte[1024];                                                                         // for receiving data
        
        while (true) {
            DatagramPacket dataPacket = new DatagramPacket(b, b.length);                                   // receiving a packet
            dataSocket.receive(dataPacket);

            String message = new String(dataPacket.getData());
            System.out.println("Message Received on Talker side : "+ message);                             // printout the message

            InetAddress IPAddress = dataPacket.getAddress();                                               // IP extraction
            int port = dataPacket.getPort();                                                               // port num extraction
            System.out.println(IPAddress.getHostName());                                                   // print IP + port num

            String newMessage = "Message Received on Talker!";
            byte[] b1 = newMessage.getBytes();
            DatagramPacket dataPacket1 = new DatagramPacket(b1, b1.length, IPAddress, port);
            dataSocket.send(dataPacket1);
            //dataSocket.close();

        }

        
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