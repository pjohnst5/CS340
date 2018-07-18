package shared.model.request;

import java.util.List;

import shared.exception.DeckException;
import shared.model.decks.DestCard;

public class DestCardRequest extends IServiceRequest {
    private List<DestCard> _chosen;
    private List<DestCard> _discarded;

    public DestCardRequest(List<DestCard> chosen, List<DestCard> discarded) throws DeckException
    {
        if (chosen == null || chosen.size() == 0){
            throw new DeckException("Must choose one card, DeckCardRequest Constructor");
        }

        if (chosen.size() > 3){
            throw new DeckException("Somehow, you chose more than three dest cards, DestCardRequest constructor");
        }

        if (discarded == null || discarded.size() == 3){
            throw new DeckException("Too many discarded cards, DestCardRequest constructor");
        }

        if (discarded.size() > 3){
            throw new DeckException("Somehow you discarded more than three cards, DestCardRequest constructor");
        }

        _chosen = chosen;
        _discarded = discarded;
    }

    public List<DestCard> get_chosen()
    {
        return _chosen;
    }

    public List<DestCard> get_discarded()
    {
        return _discarded;
    }
}
