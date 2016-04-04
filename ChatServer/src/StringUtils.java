
public class StringUtils {

	public StringUtils() {
	}
	

	public static String getCode(String message) {
	        String[] messagePiece = message.split("\\|");
	        return messagePiece[0];
	}
	
	public static String getContent(String message) {
	        String[] messagePiece = message.split("\\|");
	        if(messagePiece.length != 2) {
	            String response = "";
	            for(int i = 1; i < messagePiece.length; i++) {
	                response += messagePiece[i] + "|";
	            }
	            return response.substring(0, response.length() - 1);
	        } else {
	            return messagePiece[1];
	        }
	    }

	public static String[] getNameAndPassword(String message) {
	        return message.split(",");
	    }
	


}
