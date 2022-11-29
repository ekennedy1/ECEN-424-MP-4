import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.management.timer.Timer;

public class Talker {

    public static void main(String argv[]) throws Exception{
    
        boolean stop = true;
        int portNumber = 0;
        int listPort = 0;
        String[] frame = new String[6];
        String sentence = "This whole sentence is precisely fifty characters.";

        byte[] incomingBytes = new byte[1024];
        byte[] incomingBytes2 = new byte[1024];                                                                         
        byte[] outgoingBytes = new byte[1024];
        String incomingMessage = "";
        String outgoingMessage = "";

        InetAddress address = InetAddress.getLocalHost();

        do{
            System.out.println("Welcome to the Talker designed by Group 12.");
            System.out.println("Enter a vaild server command in the form of /Server <Port number> to open a port or Q to quit.");

            Scanner sc= new Scanner(System.in); 
            String str = sc.nextLine();
            String strParts[] = str.split(" ");

            switch(strParts[0]){
                case "/Server":
                case "/server":
                    
                    try{ 
                        portNumber = Integer.parseInt(strParts[1]);
                    }
                    catch(NumberFormatException er){
                        System.out.println("Please enter a valid Command");
                        break; 
                    }
                    stop = false;
                    break;

                case "Q":
                case "q":

                    stop = false;
                    break;

                default:

                    System.out.println("Please enter a valid Command");

                    break;
            }

        }while(stop);

        DatagramSocket dataSocket = new DatagramSocket(portNumber);  
        System.out.println("Talker waiting on Listener..."); 

     
        DatagramPacket dataPacketIn = new DatagramPacket(incomingBytes, incomingBytes.length);                                   
        dataSocket.receive(dataPacketIn);

        incomingBytes = dataPacketIn.getData();
        incomingMessage = new String(incomingBytes);
        System.out.println(incomingMessage);

        listPort = dataPacketIn.getPort();

        outgoingMessage = "Connection was made";                                                         
        outgoingBytes = outgoingMessage.getBytes();

        DatagramPacket dataPacketOut = new DatagramPacket(outgoingBytes, outgoingBytes.length, address, listPort);               
        dataSocket.send(dataPacketOut);
        
        dataSocket.setSoTimeout(2000);

        String testString = "123456789 123456789 123456789 123456789 1234567890";
        int numMessages = roundUp(testString.length(), 10);
        String testSub[] = new String[numMessages];

        outgoingMessage = String.valueOf(numMessages);                                                         
        outgoingBytes = outgoingMessage.getBytes();

        for(int i = 0; i < numMessages + 1; i++){
            DatagramPacket dataPacketOut2 = new DatagramPacket(outgoingBytes, outgoingBytes.length, address, listPort);               
            dataSocket.send(dataPacketOut2);

            DatagramPacket dataPacketIn2 = new DatagramPacket(incomingBytes2, incomingBytes2.length);                                   
            dataSocket.receive(dataPacketIn2);

            incomingBytes2 = dataPacketIn2.getData();
            incomingMessage = new String(incomingBytes2);
            System.out.println(incomingMessage);

            String testS = incomingMessage.trim();

            if(testS.equals("ACK" + i)){
                if (i < 5){
                    testSub[i] = testString.substring(i*10,(i + 1)*10);

                    outgoingMessage = testSub[i];                                                         
                    outgoingBytes = outgoingMessage.getBytes();
                }
                
            }else{
                i--;
            }
            

            
            
        }

          

        dataSocket.close();
    }

    public static int roundUp(int num, int divisor) {
        return (num + divisor - 1) / divisor;
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
    
