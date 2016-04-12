package chatclient;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.omg.PortableInterceptor.INACTIVE;

public class ConvertExpression {
	
	public static ArrayList<ExpressionIndex> check(String input){
		
		ArrayList<ExpressionIndex> indexs = new ArrayList<ExpressionIndex>();
		
		String convertExpression[] ={"[11]",
				"[1112]",
				"[22]",
				"[33]",
				"[343]",
				"[3454]",
				"[4343]",
				"[44]",
				"[4546r]",
				"[45g]",
				"[46435]",
				"[545634]",
				"[55]",
				"[5567g1]",
				"[56g56]",
				"[5g5]",
				"[5g5d]",
				"[6565]",
				"[65f]",
				"[gg]",
				"[hfe]",
				"[hgfr]",
				"[r4gr4]",
				"[rev]",
				"[rt4]"};
		
		int x = 0, y = 0;
		
		while(x != -1){
			x = input.indexOf("[");
			y = input.indexOf("]");
			if(y!=-1 && x!=-1){
				String bq1 = input.substring(x, y+1);
				for(int i = 0; i < convertExpression.length; i++){
					if(bq1.equals(convertExpression[i])){
						
						ExpressionIndex ex = new ExpressionIndex();
						ex.setFirstindex(x);
						ex.setExpressioncode(bq1);
						
						StringBuffer input1 = new StringBuffer(input);
						input1.delete(x, y+1);
						input = input1.toString();
						ex.setText(input);
						indexs.add(ex);
					}
				}
			}

		}
		return indexs;
	}
	
	//change all text content to code
	public static String sendmessage(ArrayList<ExpressionIndex> in, String text){
		StringBuffer aa =  new StringBuffer(text);
		int j = 0;
		for(int i = 0; i < in.size(); i++){
			
			aa.insert(in.get(i).getFirstindex()+j-i, in.get(i).getExpressioncode());
			
			aa.delete(in.get(i).getFirstindex()+j-i+in.get(i).getExpressioncode().length(),
					  in.get(i).getFirstindex()+j-i+in.get(i).getExpressioncode().length()+1);	
			
			j = j+in.get(i).getExpressioncode().length();
		}
		text = aa.toString();
		return text;
	}
	
	// change the send message to the messagecontent
	public void recievemessage(MainFrame myFrame, String text) { 
		try {
			ArrayList<ExpressionIndex> indexs = check(text);
			Document doc = myFrame.messageContent.getDocument();
			
			if (indexs.size() == 0) {
				doc.insertString(doc.getLength(), text, MainFrame.textattrset);
			} else {
				int existstring = doc.getLength();
				
				doc.insertString(doc.getLength(), indexs.get(indexs.size() - 1).getText(), MainFrame.textattrset);
				
				for (int i = indexs.size() - 1; i >= 0; i--) {
					myFrame.messageContent.setCaretPosition(existstring + indexs.get(i).getFirstindex());
					myFrame.messageContent.insertIcon(new ImageIcon("src/image/" + indexs.get(i).getExpressioncode() + ".gif"));
				}
			}

		} catch (BadLocationException e) {
			e.printStackTrace();
		}

	}

}
