package shared.model.decks;

import shared.enumeration.TrainColor;

public class TrainCard implements ICard {

    private TrainColor _color;

    public TrainCard(TrainColor color) {
        _color = color;
    }

    public TrainColor get_color()
    {
        return _color;
    }


}
