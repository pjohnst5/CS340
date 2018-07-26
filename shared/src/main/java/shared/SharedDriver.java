package shared;

import shared.exception.DeckException;
import shared.model.decks.TrainDeck;

public class SharedDriver {

    public static void main(String[] args)
    {
        TrainDeck deck = new TrainDeck();
        try {
            deck.phase2DrawFaceUp();
        } catch(DeckException e)
        {
            System.out.println(e.get_message());
        }
    }
}
