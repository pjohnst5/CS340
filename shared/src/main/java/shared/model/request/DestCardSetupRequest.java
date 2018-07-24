package shared.model.request;

import shared.model.Player;
import shared.model.wrapper.ThreeDestCardWrapper;

public class DestCardSetupRequest extends IServiceRequest {

    private ThreeDestCardWrapper _keepCards;
    private ThreeDestCardWrapper _discardCards;

    public DestCardSetupRequest(Player player, ThreeDestCardWrapper keep, ThreeDestCardWrapper discard) {
        super(player);

        _keepCards = keep;
        _discardCards = discard;
    }

    public ThreeDestCardWrapper getDiscardCards() {
        return _discardCards;
    }

    public ThreeDestCardWrapper getKeepCards() {
        return _keepCards;
    }
}
