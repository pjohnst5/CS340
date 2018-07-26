package shared.model.request;

import shared.model.Player;
import shared.model.wrapper.ThreeDestCardWrapper;

public class DestCardRequest extends IServiceRequest {

    private ThreeDestCardWrapper _keepCards;
    private ThreeDestCardWrapper _discardCards;

    public DestCardRequest(Player player, ThreeDestCardWrapper keep, ThreeDestCardWrapper discard) {
        super(player);
        _keepCards = keep;
        _discardCards = discard;
    }

    public ThreeDestCardWrapper get_discardCards() {
        return _discardCards;
    }

    public ThreeDestCardWrapper get_keepCards() {
        return _keepCards;
    }
}
