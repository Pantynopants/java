package chatclient;

import java.text.SimpleDateFormat;
import java.util.Date;


public class StringUtils {
	
    public static String getCode(String message) {
        String[] messagePiece = message.split("\\|");
        return messagePiece[0];
    }
    
    public static String getDetail(String message) {
        String[] messagePiece = message.split("\\|");
        return messagePiece[1];
    }
    
    public static String getSender(String message) {
        return message.split("\\|")[1];
    }
    
    public static String[] getNameList(String message) {
        String[] messagePiece = message.split("\\|");
        return messagePiece[2].split(",");
    }
    
    
    public static String getContent(String message) {
        return message.split("\\|")[3];
    }
}
