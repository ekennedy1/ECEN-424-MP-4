import java.io.*;
import java.util.*;
public class TestCodes {
    public static void main(String[] args) {
        String message = "This whole sentence is precisely fifty characters.";
        String[] frame = new String[6];
        frame = Frames(message); 

        for (int i=0; i<6; i++) {
            System.out.println(frame[i]);
        }
        
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

 