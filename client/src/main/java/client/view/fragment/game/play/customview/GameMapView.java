package client.view.fragment.game.play.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.pjohnst5icloud.tickettoride.R;

import java.util.ArrayList;
import java.util.List;

import shared.configuration.ConfigurationManager;
import shared.enumeration.CityManager;
import shared.enumeration.ListOfRoutes;
import shared.model.City;
import shared.model.Route;

/**
 * Created by jtyler17 on 7/21/18.
 */

public class GameMapView extends FrameLayout {

    private static final double ASPECT_RATIO;
    private static final int CITY_SIZE;
    static {
//        ASPECT_RATIO = (double)ConfigurationManager.getInt("board_width") / (double)ConfigurationManager.getInt("board_height"); // FIXME
        ASPECT_RATIO = 1600.0 / 1088.0;
        CITY_SIZE = 15;
    }

    private boolean _initialized;

    private Presenter _presenter;
    private List<City> _cities;
    private List<Route> _routes;

    private Rect _viewRect;
    private int _height;
    private int _width;

    private Paint _cityPaint;
    private Paint _cityPaintBorder;

//    private List<RouteView> _routeViews;
    private RouteView _selectedRoute;

    public interface Presenter {

    }

    public GameMapView(Context context) {
        this(context, null);
    }
    public GameMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        _initialized = false;

        _cityPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        _cityPaint = new Paint();
        _cityPaint.setColor(0xffff0000);
        _cityPaintBorder = new Paint(Paint.ANTI_ALIAS_FLAG);
        _cityPaintBorder.setColor(0xff101010);
    }

    public void initializeData(Presenter presenter) {
        _presenter = presenter;

        _cities = CityManager.getInstance().getCities();
        _routes = new ListOfRoutes().getRoutes();

        for (Route r : _routes) {
            RouteView rv = new RouteView(getContext()).initialize(r);
            addView(rv);
        }
//        invalidate();
//        requestLayout();
    }

    public void setRouteOnClickListener(View.OnClickListener listener) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).setOnClickListener(listener);
        }
//        for (RouteView r : _routeViews) {
//            r.setOnClickListener(listener);
//        }
    }

    public void routeSelected(RouteView rv) {
        if (_selectedRoute != null) {
            _selectedRoute.setSelected(false);
        }
        _selectedRoute = rv;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!_initialized) {
            initializeGraphics();
        }

        for (City city : _cities) {
            float x = city.getX();
            float y = city.getY();
            canvas.drawCircle(x, y, CITY_SIZE + 2, _cityPaintBorder);
            canvas.drawCircle(x, y, CITY_SIZE, _cityPaint);
        }
    }

    private void initializeGraphics() {
//        if (_presenter == null) { // FIXME: uncomment
//            // data hasn't been initialized yet
//            return;
//        }

        // match height to the maximum of the parent container
        _height = getHeight() - (getPaddingTop() + getPaddingBottom());
        _width = (int)(_height * ASPECT_RATIO);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.map); // FIXME: might not need to do Bitmap -> Drawable
        Bitmap backgroundImage = Bitmap.createScaledBitmap(bitmap, _width, _height, false);
        Drawable backgroundDrawable = new BitmapDrawable(getResources(), backgroundImage);

        // FIXME: do this differently
        CityManager.getInstance().newBoardSize(_width, _height);

        _viewRect = new Rect();
        _viewRect.set(0, 0, _width, _height);

        setBackground(backgroundDrawable);
        _initialized = true;
    }
}
