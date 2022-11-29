import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class ListenerR {
    

    public static void main(String argv[]) throws Exception{

        boolean stop = true;
        int portNumber = 0;
        Random rand = new Random();

        byte[] incomingBytes = new byte[1024];
        byte[] incomingBytes2 = new byte[1024]; 
        byte[] incomingBytes3 = new byte[1024];                                                                        
        byte[] outgoingBytes = new byte[1024];
        String incomingMessage = "";
        String incomingMessage2 = "";
        String outgoingMessage = "";
                          
        InetAddress address = InetAddress.getLocalHost();

        do{
            System.out.println("Welcome to the Listener designed by Group 12.");
            System.out.println("Enter a vaild command in the form of /Client <server ip> <server port> to connect to a active server or Q to quit.");

            Scanner sc= new Scanner(System.in); 
            String str = sc.nextLine();
            String strParts[] = str.split(" ");

            switch(strParts[0]){
                case "/Client":
                case "/client":
                    if(strParts.length < 3){
                        System.out.println("Please enter a valid Command");
                        break; 
                    }
                    try{ 
                        portNumber = Integer.parseInt(strParts[2]);
                    }
                    catch(NumberFormatException er){
                        System.out.println("Please enter a valid Command");
                        break; 
                    }

                    stop = false;

                case "Q":
                case "q":

                    stop = false;
                    break;

                default:

                    System.out.println("Please enter a valid Command");
                    break;
            }

        }while(stop);

        DatagramSocket dataSocket = new DatagramSocket(); 

        outgoingMessage = "Listener requests a UDP connection";                                                         
        outgoingBytes = outgoingMessage.getBytes();

        DatagramPacket dataPacketOut = new DatagramPacket(outgoingBytes, outgoingBytes.length, address, portNumber);               
        dataSocket.send(dataPacketOut);

        DatagramPacket dataPacketIn = new DatagramPacket(incomingBytes, incomingBytes.length);                                   // receiving a packet
        dataSocket.receive(dataPacketIn);

        DatagramPacket dataPacketIn2 = new DatagramPacket(incomingBytes2, incomingBytes2.length);                                   // receiving a packet
        dataSocket.receive(dataPacketIn2);

        incomingBytes = dataPacketIn.getData();
        incomingMessage = new String(incomingBytes);
        System.out.println(incomingMessage);

        incomingBytes2 = dataPacketIn2.getData();
        incomingMessage2 = new String(incomingBytes2);
        System.out.println("ID0: " + incomingMessage2);

        int numMessages = Integer.valueOf(incomingMessage2.trim());

        outgoingMessage = "ACK0";                                                         
        outgoingBytes = outgoingMessage.getBytes();

        DatagramPacket dataPacketOut2 = new DatagramPacket(outgoingBytes, outgoingBytes.length, address, portNumber);               
        dataSocket.send(dataPacketOut2);

        for(int i = 0; i < numMessages; i++){
            DatagramPacket dataPacketIn3 = new DatagramPacket(incomingBytes3, incomingBytes3.length);                                   // receiving a packet
            dataSocket.receive(dataPacketIn3);

            incomingBytes3 = dataPacketIn3.getData();
            incomingMessage = new String(incomingBytes3);

            System.out.println("ID" + (i + 1) + ": " + incomingMessage);

            int rand_int1 = rand.nextInt(2);

            System.out.println(rand_int1);

            if(rand_int1 == 1){
                outgoingMessage = "ACK" + (i + 1);                                                         
                outgoingBytes = outgoingMessage.getBytes();

                DatagramPacket dataPacketOut3 = new DatagramPacket(outgoingBytes, outgoingBytes.length, address, portNumber);               
                dataSocket.send(dataPacketOut3);

            }else{
                i--;
            }

        }


        dataSocket.close();

        
    }
}
