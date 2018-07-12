package shared.Command;

import shared.serialization.IGsonSerializable;

public interface ICommand extends IGsonSerializable {

    public Object execute();

}
