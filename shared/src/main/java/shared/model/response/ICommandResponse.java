package shared.model.response;

import java.util.List;

import shared.Command.ICommand;

public interface ICommandResponse extends IResponse {

    public List<ICommand> getCommands();

}
