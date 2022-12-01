import java.io.*;
import java.util.*;
public class TestCodes {
    public static void main(String[] args) {
        String message = "4This whole";
        //System.out.println(packet(message));
        Date date = new Date();
        long currentTime = date.getTime(); 
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Date date1 = new Date();
        long newTime = date1.getTime();
        System.out.println((newTime - currentTime) / 1000);
        
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

 