package shared.model;

import shared.exception.NotEnoughTrainCarsException;

public class TrainCars {
    private int _numberOfCars = 45;
    private boolean _isFinalRound = false;

    public void removeCars(int numberOfCars) throws NotEnoughTrainCarsException {
        if(this._numberOfCars >= numberOfCars) {
            this._numberOfCars -= numberOfCars;
            if(this._numberOfCars <= 2){
                this._isFinalRound = true;
            }
        }
        else
            throw new NotEnoughTrainCarsException();
    }

    public int getCount() {
        return _numberOfCars;
    }

    public boolean isFinalRound(){
        return this._isFinalRound;
    }
}
