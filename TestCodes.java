import java.io.*;
import java.util.*;
public class TestCodes {
    public static void main(String[] args) {
        String message = "This whole sentence is precisely fifty characters.";
        String ACK = "ACK" + String.valueOf(5) + "\n";
        System.out.println(ACK.strip());
        
    }


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
    
}

 