package shared.model;

import java.util.Comparator;
import java.util.Date;

public class Message {
    private String _message;
    private Player _player;
    private Date _timeStamp;

    public Message() {
        _message = "";
        _player = null;
        _timeStamp = new Date();
    }

    public String getMessage()
    {
        return _message;
    }

    public Player getPlayer()
    {
        return _player;
    }

    public String getDisplayName() {
        return _player.getDisplayName();
    }

    public String getGameID()
    {
        return _player.getGameID();
    }

    public Date getTimeStamp() {
        return _timeStamp;
    }


    public void setMessage(String s)
    {
        _message = s;
    }

    public void setPlayer(Player player) {
        _player = player;
    }
    public void setTimeStamp() {
        _timeStamp = new Date();
    }

    public void setTimeStamp(Date timeStamp) {
        _timeStamp = timeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Message)) {
            return false;
        }
        Message other = (Message) o;
        if (!this._timeStamp.equals(other._timeStamp)) {
            return false;
        }
        if (!this._player.getPlayerID().equals(other._player.getPlayerID())) {
            return false;
        }
        return true;
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
