package shared.model;

public class GameAction {

    private String _displayName;
    private String _actionDescription;

    public GameAction(String displayName, String description)
    {
        _displayName = displayName;
        _actionDescription = description;
    }

    public String get_displayName()
    {
        return _displayName;
    }

    public String get_actionDescription()
    {
        return _actionDescription;
    }
}
