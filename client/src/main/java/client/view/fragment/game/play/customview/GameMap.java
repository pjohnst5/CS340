package client.view.fragment.game.play.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.pjohnst5icloud.tickettoride.R;

/**
 * Created by jtyler17 on 7/21/18.
 */

public class GameMap extends View {

    public interface Presenter {

    }

    private Presenter _presenter;

    public GameMap(Context context) {
        this(context, null);
    }
    public GameMap(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public void setPresenter(Presenter presenter) {
        _presenter = presenter;
    }
}
