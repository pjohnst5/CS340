package shared.model;

import java.util.Comparator;
import java.util.Date;

public class GameAction {

    private String _displayName;
    private String _actionDescription;
    private Date _timestamp;

    private GameAction(){}

    public GameAction(String displayName, String description)
    {
        _displayName = displayName;
        _actionDescription = description;
        _timestamp = new Date();
    }

    public String get_displayName()
    {
        return _displayName;
    }

    public String get_actionDescription()
    {
        return _actionDescription;
    }

    public Date getTimestamp() { return _timestamp; }

    public void setTimestamp(){
        _timestamp = new Date();
    }

    public void setTimestamp(Date timestamp){
        _timestamp = timestamp;
    }

    public static Comparator<GameAction> getAscendingComparator(){
        return new GameAction().new AscendingComparator();
    }

    public static Comparator<GameAction> getDescendingComparator(){
        return new GameAction().new DescendingComparator();
    }

    public class AscendingComparator implements Comparator<GameAction> {
        public int compare(GameAction a, GameAction b){
            return a.getTimestamp().compareTo(b.getTimestamp());
        }
    }

    public class DescendingComparator implements Comparator<GameAction>{
        public int compare(GameAction a, GameAction b){
            return b.getTimestamp().compareTo(a.getTimestamp());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof GameAction){
            GameAction other = (GameAction)o;
            return (this.get_displayName().equals(other.get_displayName()) &&
                    this.get_actionDescription().equals(other.get_actionDescription()) &&
                    this.getTimestamp().equals(other.getTimestamp()));
        }

        return false;
    }
}
