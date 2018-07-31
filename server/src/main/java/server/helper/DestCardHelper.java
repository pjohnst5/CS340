package server.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import shared.enumeration.CityName;
import shared.model.Route;
import shared.model.decks.DestCard;

public class DestCardHelper {
    List<DestCard> _cardsOfPlayer;
    Map<UUID, Route> _claimedByPlayer;

    public DestCardHelper(Map<UUID, Route> claimedByPlayer, List<DestCard> cardsOfPlayer) {
        _cardsOfPlayer = cardsOfPlayer;
        _claimedByPlayer = claimedByPlayer;
    }

    public List<DestCard> updateDestCards() {
        for (int i = 0; i < _cardsOfPlayer.size(); i++) {
            CityName src = _cardsOfPlayer.get(i).get_cities().getKey().get_name();
            CityName dst = _cardsOfPlayer.get(i).get_cities().getValue().get_name();

            List<UUID> _children = getChildren(src);

            for (int j = 0; j < _children.size(); j++) {
                Recursive(_children.get(j), dst, i);
            }
            resetRoutes();
        }
        return _cardsOfPlayer;
    }

    private void Recursive(UUID r, CityName dst, int index) {
        List<UUID> _children = new ArrayList<>();

        if (_claimedByPlayer.get(r).get_dest() == dst || _claimedByPlayer.get(r).get_source() == dst) {
            _cardsOfPlayer.get(index).set_completed(true);
            return;
        }

        if (_claimedByPlayer.get(r).wasSrc()) {
            _children = getChildren(_claimedByPlayer.get(r).get_dest());
        } else {
            _children = getChildren(_claimedByPlayer.get(r).get_source());
        }

        for (int i = 0; i < _children.size(); i++){
            Recursive(_children.get(i), dst, index);
        }
    }


    private List<UUID> getChildren(CityName src) {
        List<UUID> result = new ArrayList<>();

        for (Map.Entry<UUID, Route> entry : _claimedByPlayer.entrySet())
        {
            if (!entry.getValue().visited()) {
                if (entry.getValue().get_source() == src){
                    entry.getValue().set_wasSrc(true); //The source city was actually the source city
                    entry.getValue().setVisited(true);
                    result.add(entry.getKey());
                } else if (entry.getValue().get_dest() == src) {
                    entry.getValue().set_wasSrc(false); //The dest city was actually the source city
                    entry.getValue().setVisited(true);
                    result.add(entry.getKey());
                }
            }

        }
        return result;
    }

    private void resetRoutes() {
        for (Map.Entry<UUID, Route> entry : _claimedByPlayer.entrySet())
        {
            entry.getValue().setVisited(false);
            entry.getValue().set_wasSrc(false);
        }
    }
}
