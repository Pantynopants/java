
public class AgendaInfo {
    private String username;
    private String password;
    private int start;
    private int end;
    private String title;
    private String otherusername;
    private int meetingID = 0;//no need to set static here

    public AgendaInfo(String username, String password, String otherusername, int start, int end, String title,int meetingID) {
        this.username = username;
        this.password = password;
        this.start = start;
        this.end = end;
        this.title = title;
        this.otherusername = otherusername;
        this.meetingID=meetingID;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "title:"+title+" "+ "hostuser:"  + username + " " + "otheruser:"+otherusername+" "+"Starthour:"+start+" "+"endhour:"+end;
    }

    public String getPassword() {
        return password;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public String getTitle() {
        return title;
    }

    public String getOtherusername() {
        return otherusername;
    }

    public int getMeetingID() {
        return meetingID;
    }
}
