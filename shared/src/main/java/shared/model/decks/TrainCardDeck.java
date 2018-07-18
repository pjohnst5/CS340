package shared.model.decks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shared.enumeration.TrainColor;
import shared.exception.DeckException;

public class TrainCardDeck {

    private FaceUpDeck _faceUpDeck;
    private FaceDownDeck _faceDownDeck;

    public TrainCardDeck()
    {
        final int numNormalCards = 96;
        final int numLocomotiveCards = 10;
        final int numFaceUpCards = 5;
        List<TrainCard> cards = new ArrayList<>();

        //Initialize deck
        for (int i = 0; i < numNormalCards; i++)
        {
            if (i >= 0 && i < 12){
                cards.add(new TrainCard(TrainColor.PINK));
            } else if (i >= 12 && i < 24){
                cards.add(new TrainCard(TrainColor.WHITE));
            } else if (i >= 24 && i < 36) {
                cards.add(new TrainCard(TrainColor.BLUE));
            } else if (i >= 36 && i < 48) {
                cards.add(new TrainCard(TrainColor.YELLOW));
            } else if (i >= 48 && i < 60) {
                cards.add(new TrainCard(TrainColor.ORANGE));
            } else if (i >= 60 && i < 72) {
                cards.add(new TrainCard(TrainColor.BLACK));
            } else if (i >= 72 && i < 84) {
                cards.add(new TrainCard(TrainColor.RED));
            } else if (i >= 84 && i < 96) {
                cards.add(new TrainCard(TrainColor.GREEN));
            }
        }
        for (int i = 0; i < numLocomotiveCards; i++)
        {
            cards.add(new TrainCard(TrainColor.LOCOMOTIVE));
        }

        //Shuffle deck
        Collections.shuffle(cards);

        //Take first five and put them face up
        for (int i = 0; i < numFaceUpCards; i++){

        }

    }

    private void reDealCards() {
        //adds all face up cards to face down deck
        _faceDownDeck._cards.addAll(_faceUpDeck._cards);

        //clears face up cards
        _faceUpDeck._cards.clear();

        //sets locomotive count of face up deck to 0
        _faceUpDeck._locoMotiveCount = 0;

        //shuffles face down deck
        Collections.shuffle(_faceDownDeck._cards);

        //Re deals 5 face up cards
        for (int i = 0; i < 5; i++) {

        }
    }

    public FaceUpDeck get_faceUpDeck() {
        return _faceUpDeck;
    }

    public FaceDownDeck get_faceDownDeck() {
        return _faceDownDeck;
    }


    //FaceUpDeck is private and only TrainDeck has it
    private class FaceUpDeck {
        int _locoMotiveCount;
        List<TrainCard> _cards;

        private FaceUpDeck() {
            _cards = new ArrayList<>();
            _locoMotiveCount = 0;
        }

        private void addCard(TrainCard card) throws DeckException
        {
            if (_cards.size() == 5){
                throw new DeckException("Already 5 cards in face up deck");
            }
            _cards.add(card);

            if (card.get_color() == TrainColor.LOCOMOTIVE){
                _locoMotiveCount++;
            }

            //ReDeal cards
            if (_locoMotiveCount == 3) {
                reDealCards();
            }
        }

        private void removeCard(TrainCard card) throws DeckException
        {
            try {
                if (!_cards.remove(card)) {
                    throw new DeckException("Train Card not in face up deck, remove failed");
                }

                //Card was successfully removed, is it locomotive?
                if (card.get_color() == TrainColor.LOCOMOTIVE) {
                    _locoMotiveCount--;

                    //TODO: handle locomotive case
                }
            } catch(ClassCastException e){
                throw new DeckException("Class cast null exception when removing card from face up deck " + e.getMessage());
            } catch(NullPointerException e) {
                throw new DeckException("remove card face up, requested card is null");
            }
        }

    }

    //FaceDownDeck is private and only TrainDeck has it
    private class FaceDownDeck {
        List<TrainCard> _cards;

        private FaceDownDeck() {
            _cards = new ArrayList<>();
        }

        private TrainCard drawCard() throws DeckException {
            if (_cards.size() == 0){
                throw new DeckException("No more cards to draw in Face Down Deck of Train Cards");
            }
            return _cards.remove(0);
        }

        private void addCard(TrainCard card) {
            _cards.add(card);
        }

    }
}
