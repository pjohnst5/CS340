package shared.model.response;

import java.util.List;

import shared.command.ICommand;

public interface ICommandResponse extends IResponse {

    public List<ICommand> getCommands();

}
