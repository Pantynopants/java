
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.util.ArrayList;

public class UserInfo {
    public static String userName;
    public static String loginTime;
    public static String[] userList;
    public static ArrayList<Message> messageQuene;
    public static SimpleAttributeSet title;
    public static SimpleAttributeSet content;
    
    public static void initAttr() {
        title = new SimpleAttributeSet();
        content = new SimpleAttributeSet();
        StyleConstants.setFontSize(title, 16);
        StyleConstants.setFontSize(content, 20);
    }
}
