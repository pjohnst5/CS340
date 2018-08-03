package shared.model.decks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shared.enumeration.TrainColor;
import shared.exception.DeckException;

public class TrainDeck {

    private FaceUpDeck _faceUpDeck;
    private FaceDownDeck _faceDownDeck;

    //-------------------------------------------Public Functions---------------------------------------------//
    public TrainDeck()
    {
        final int numNormalCards = 96;
        final int numLocomotiveCards = 14;
        final int numFaceUpCards = 5;
        List<TrainCard> tempDeck = new ArrayList<>();

        //Initialize temp deck
        for (int i = 0; i < numNormalCards; i++)
        {
            if (i >= 0 && i < 12){
                tempDeck.add(new TrainCard(TrainColor.PINK));
            } else if (i >= 12 && i < 24){
                tempDeck.add(new TrainCard(TrainColor.WHITE));
            } else if (i >= 24 && i < 36) {
                tempDeck.add(new TrainCard(TrainColor.BLUE));
            } else if (i >= 36 && i < 48) {
                tempDeck.add(new TrainCard(TrainColor.YELLOW));
            } else if (i >= 48 && i < 60) {
                tempDeck.add(new TrainCard(TrainColor.ORANGE));
            } else if (i >= 60 && i < 72) {
                tempDeck.add(new TrainCard(TrainColor.BLACK));
            } else if (i >= 72 && i < 84) {
                tempDeck.add(new TrainCard(TrainColor.RED));
            } else if (i >= 84 && i < 96) {
                tempDeck.add(new TrainCard(TrainColor.GREEN));
            }
        }
        for (int i = 0; i < numLocomotiveCards; i++)
        {
            tempDeck.add(new TrainCard(TrainColor.LOCOMOTIVE));
        }

        //Shuffle temp deck
        Collections.shuffle(tempDeck);

        //Instantiate new face up and face down decks
        _faceDownDeck = new FaceDownDeck();
        _faceUpDeck = new FaceUpDeck();

        //Sets tempDeck as face down deck
        for (int i = 0; i < tempDeck.size(); i++){
            _faceDownDeck.addCard(tempDeck.get(i));
        }

        //clears tempDeck
        tempDeck.clear();

        //Take first five face down cards and put them face up
        for (int i = 0; i < numFaceUpCards; i++){
            try {
                _faceUpDeck.addCard(_faceDownDeck.drawCard());
            } catch(DeckException e){
                System.out.println("Something cray cray happened when making a new TrainDeck: " + e.get_message());
            }
        }
        if (_faceUpDeck._locoMotiveCount >= 3){
            reDealCards();
        }

    }

    public TrainCard drawFaceDownCard() throws DeckException
    {
        return _faceDownDeck.drawCard();
    }

    public TrainCard drawFaceUpCard(TrainCard card) throws DeckException
    {
        _faceUpDeck.removeCard(card);
        if (_faceDownDeck._cards.size() != 0){
            _faceUpDeck.addCard(_faceDownDeck.drawCard());
        }
        if (_faceUpDeck._locoMotiveCount >= 3) {
            reDealCards();
        }
        return card;
    }

    public void discardTrainCard(TrainCard card) throws DeckException {
        if (_faceUpDeck._cards.size() + _faceDownDeck._cards.size() == 110)
        {
            throw new DeckException("There are already 110 cards in the train deck, can't add another");
        }
        if (_faceUpDeck._cards.size() < 5){
            _faceUpDeck.addCard(card);
        } else {
            _faceDownDeck.addCard(card);
            if (_faceUpDeck._locoMotiveCount >= 3){
                reDealCards();
            }
        }
    }

    public void discardTrainCards(List<TrainCard> cards) throws DeckException {
        for(int i = 0; i < cards.size(); i++){
            discardTrainCard(cards.get(i));
        }
    }

    public int sizeOfFaceDownDeck(){
        return _faceDownDeck.sizeOfFaceDownDeck();
    }

    public List<TrainCard> getFaceUpTrainCards(){
        return _faceUpDeck.getCards();
    }

    //---------------------------------------------------------------------------------------------------//


    private void reDealCards() {
        if ((_faceDownDeck._nonLocomotiveCount + _faceUpDeck._nonLocomotiveCount) < 3) { //this is to avoid infinite loop, there has to be at least 3 non-locomotive cards in the deck to reshuffle
            return;
        }
        System.out.println("Re-dealing cards, locomotive count is " + _faceUpDeck._locoMotiveCount);

        //adds all face up cards to face down deck
        for (int i = 0; i < _faceUpDeck._cards.size(); i++){
            try {
                if (_faceDownDeck._cards.size() ==110){
                    throw new DeckException("There were 110 train cards in face down deck already");
                }
                _faceDownDeck.addCard(_faceUpDeck._cards.get(i));
            } catch (DeckException e) {
                System.out.println("Something cray cray happened when redealing the train deck: " + e.get_message());
            }

        }

        //clears face up cards
        _faceUpDeck._cards.clear();

        //sets counts of face up deck to 0
        _faceUpDeck._locoMotiveCount = 0;
        _faceUpDeck._nonLocomotiveCount = 0;


        //shuffles face down deck
        Collections.shuffle(_faceDownDeck._cards);

        //Re deals 5 face up cards
        for (int i = 0; i < 5; i++) {
            try {
                _faceUpDeck.addCard(_faceDownDeck.drawCard());
            } catch (DeckException e) {
                System.out.println("Something cray cray happened when redealing the train deck: " + e.get_message());
            }
        }
        if (_faceUpDeck._locoMotiveCount >= 3) {
            reDealCards();
        }
    }



    //FaceUpDeck is private and only TrainDeck has it
    private class FaceUpDeck {
        int _locoMotiveCount;
        List<TrainCard> _cards;
        int _nonLocomotiveCount;

        private FaceUpDeck() {
            _cards = new ArrayList<>();
            _locoMotiveCount = 0;
            _nonLocomotiveCount = 0;
        }

        public List<TrainCard> getCards(){
            return _cards;
        }

        private void addCard(TrainCard card) throws DeckException
        {
            if (_cards.size() == 5){
                throw new DeckException("Already 5 cards in face up deck");
            }

            _cards.add(card);

            if (card.get_color() == TrainColor.LOCOMOTIVE){
                _locoMotiveCount++;
            } else {
                _nonLocomotiveCount++;
            }

        }

        private void removeCard(TrainCard card) throws DeckException
        {
            boolean found = false;
            for (int i = 0; i < _cards.size(); i++) {
                if (_cards.get(i).get_color() == card.get_color()){
                    _cards.remove(i);
                    found = true;
                    break;
                }
            }

            if (!found){
                throw new DeckException("Card was not in face up deck, couldn't remove it");
            }

            //Card was successfully removed, is it locomotive?
            if (card.get_color() == TrainColor.LOCOMOTIVE) {
                _locoMotiveCount--;
            } else {
                _nonLocomotiveCount--;
            }
        }

    }



    //FaceDownDeck is private and only TrainDeck has it
    private class FaceDownDeck {
        List<TrainCard> _cards;
        int _nonLocomotiveCount;

        private FaceDownDeck() {
            _cards = new ArrayList<>();
            _nonLocomotiveCount = 0;
        }

        private TrainCard drawCard() throws DeckException {
            if (_cards.size() == 0){
                throw new DeckException("No more cards to draw in Face Down Deck of Train Cards");
            }
            if (_cards.get(0).get_color() != TrainColor.LOCOMOTIVE){
                _nonLocomotiveCount--;
            }
            return _cards.remove(0);
        }

        private void addCard(TrainCard card) {
            if (card.get_color() != TrainColor.LOCOMOTIVE){
                _nonLocomotiveCount++;
            }
            _cards.add(card);
        }

        public int sizeOfFaceDownDeck(){
            return this._cards.size();
        }

    }
}
