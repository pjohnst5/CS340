package shared.Command;

import shared.serialization.IGsonSerializable;

public interface ICommand {

    public Object execute();

}
