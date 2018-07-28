package shared.model.request;

import shared.model.Player;
import shared.model.decks.TrainCard;

public class FaceUpRequest extends IServiceRequest {

    private TrainCard _faceUpCard;

    public FaceUpRequest(Player player, TrainCard faceUpCard)
    {
        super(player);
        _faceUpCard = faceUpCard;
    }

    public TrainCard get_faceUpCard() {
        return _faceUpCard;
    }
}
