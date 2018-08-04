package shared;

import java.util.List;

import shared.enumeration.TrainColor;
import shared.exception.DeckException;
import shared.model.decks.TrainCard;
import shared.model.decks.TrainDeck;

public class TrainDeckTest {
    public static void main(String[] args){
        TrainDeck deck = new TrainDeck();

        try {
            for (int i = 0; i < 105; i++) {
                deck.drawFaceDownCard();
            }

            List<TrainCard> faceUpCards = deck.getFaceUpTrainCards();

            for (int i = 0; i < 5; i++){
                deck.drawFaceUpCard(faceUpCards.get(0));
            }

            //should be empty
            deck.discardTrainCard(new TrainCard(TrainColor.LOCOMOTIVE));
            deck.discardTrainCard(new TrainCard(TrainColor.LOCOMOTIVE));
            deck.discardTrainCard(new TrainCard(TrainColor.LOCOMOTIVE));
            deck.discardTrainCard(new TrainCard(TrainColor.LOCOMOTIVE));
            deck.discardTrainCard(new TrainCard(TrainColor.LOCOMOTIVE));
            deck.discardTrainCard(new TrainCard(TrainColor.LOCOMOTIVE));

            deck.discardTrainCard(new TrainCard(TrainColor.ORANGE));
            deck.discardTrainCard(new TrainCard(TrainColor.ORANGE));

            deck.discardTrainCard(new TrainCard(TrainColor.GREEN));

            deck.drawFaceUpCard(new TrainCard(TrainColor.GREEN));

            deck.discardTrainCard(new TrainCard(TrainColor.GREEN));
        } catch (DeckException e) {
            System.out.println(e.get_message());
        }

    }
}
