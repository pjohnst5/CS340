package shared.model;

import java.util.Comparator;
import java.util.Date;

public class Message {
    private String _message;
    private String _displayName;
    private String _gameID;
    private Date _timeStamp;

    public Message() {
        _message = "";
        _displayName = "";
        _gameID = "";
        _timeStamp = new Date();
    }

    public String getMessage()
    {
        return _message;
    }

    public String getDisplayName()
    {
        return _displayName;
    }

    public String getGameID()
    {
        return _gameID;
    }

    public Date getTimeStamp() {
        return _timeStamp;
    }


    public void setMessage(String s)
    {
        _message = s;
    }

    public void setDisplayName(String s)
    {
        _displayName = s;
    }

    public void setGameID(String s)
    {
        _gameID = s;
    }

    public void setTimeStamp() {
        _timeStamp = new Date();
    }

    public void setTimeStamp(Date timeStamp) {
        _timeStamp = timeStamp;
    }

    public static Comparator<Message> getAscendingComparator(){
        return new Message().new AscendingComparator();
    }

    public static Comparator<Message> getDescendingComparator(){
        return new Message().new DescendingComparator();
    }


    public class AscendingComparator implements Comparator<Message>
    {
        // Used for sorting in ascending order of
        // roll number
        public int compare(Message a, Message b)
        {
            return a.getTimeStamp().compareTo(b.getTimeStamp());
        }
    }

    public class DescendingComparator implements Comparator<Message>
    {
        // Used for sorting in ascending order of
        // roll number
        public int compare(Message a, Message b)
        {
            return b.getTimeStamp().compareTo(a.getTimeStamp());
        }
    }
}
