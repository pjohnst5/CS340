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
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.pjohnst5icloud.tickettoride.R;

import java.util.List;

import shared.enumeration.CityManager;
import shared.enumeration.ListOfRoutes;
import shared.model.City;
import shared.model.Route;

/**
 * Created by jtyler17 on 7/21/18.
 */

public class GameMapView extends FrameLayout {

    private static final double ASPECT_RATIO;
    private static final int CITY_RADIUS;
    private static final int CITY_RADIUS_SM;
    static {
//        ASPECT_RATIO = (double)ConfigurationManager.getInt("board_width") / (double)ConfigurationManager.getInt("board_height"); // FIXME
        ASPECT_RATIO = 1600.0 / 1088.0;
        CITY_RADIUS = 15;
        CITY_RADIUS_SM = 7;
    }

    public enum MapSize {
        MEDIUM,
        SMALL
    }

    private MapSize _mapSize;
    private boolean _initialized;

    private HostFragment _host;
    private List<City> _cities;
    private List<Route> _routes;

    private Rect _viewRect;
    private int _height;
    private int _width;
    private int _cityRadius;

    private Paint _cityPaint;
    private Paint _cityPaintBorder;

    private RouteView _selectedRoute;

    public interface HostFragment {
        public void RouteSelected(Route route);
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
        _cityRadius = CITY_RADIUS;
        _mapSize = MapSize.MEDIUM;
    }

    public void initializeData(HostFragment host, List<City> cities, List<Route> routes) {
        _host = host;

//        _cities = CityManager.getInstance().getCities();
//        _routes = new ListOfRoutes().getRoutes();
        _cities = cities;
        _routes = routes;

        for (Route r : _routes) {
            RouteView rv = new RouteView(getContext()).initialize(r);
            addView(rv);
        }
    }

    public void routeSelected(RouteView rv) {
        boolean newRoute = false;
        if (_selectedRoute != null) {
            if (_selectedRoute != rv) {
                _selectedRoute.setSelected(false);
                _selectedRoute.redraw();
                newRoute = true;
            }
        }
        if (rv != null) {
            rv.setSelected(true);
            rv.redraw();
            _selectedRoute = rv;
        }
        if (newRoute) {
            _host.RouteSelected(_selectedRoute.getRoute());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!_initialized) {
            initializeGraphics();
        }

        // draw claimed routes first
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            RouteView rv = (RouteView) getChildAt(i);
            if (rv.getRoute().isClaimed()) {
                rv.drawRoute(canvas);
            }
        }

        // draw routes
        for (int i = 0; i < childCount; i++) {
            RouteView rv = (RouteView) getChildAt(i);
            if (rv != _selectedRoute) {
                if (!rv.getRoute().isClaimed()) {
                    rv.drawRoute(canvas);
                }
            }
        }

        // draw cities
        for (City city : _cities) {
            float x = city.getX();
            float y = city.getY();
            canvas.drawCircle(x, y, _cityRadius + 2, _cityPaintBorder);
            canvas.drawCircle(x, y, _cityRadius, _cityPaint);
        }

        // if a route is selected, draw it last
        if (_selectedRoute != null) {
            _selectedRoute.drawRoute(canvas);
        }
    }

    public void redraw() {
        invalidate();
        requestLayout();

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            ((RouteView) getChildAt(i)).redraw();
        }
    }

    private void initializeGraphics() {
        if (_host == null) {
            // data hasn't been initialized yet
            return;
        }

        // match height to the maximum of the parent container
        _height = getHeight() - (getPaddingTop() + getPaddingBottom());
        _width = (int)(_height * ASPECT_RATIO);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.map); // FIXME: might not need to do Bitmap -> Drawable
        Bitmap backgroundImage = Bitmap.createScaledBitmap(bitmap, _width, _height, false);
        Drawable backgroundDrawable = new BitmapDrawable(getResources(), backgroundImage);

        if (_height < 800) {
            _cityRadius = CITY_RADIUS_SM;
            _mapSize = MapSize.SMALL;
        }
        RouteView.setLineWidth(_mapSize);

        // redo all the city and route coordinates from the resized board
        CityManager.getInstance().newBoardSize(_width, _height);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            calculateRouteCoordinates((RouteView) getChildAt(i));
        }

        _viewRect = new Rect();
        _viewRect.set(0, 0, _width, _height);

        setBackground(backgroundDrawable);
        _initialized = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchEventDown(event);
                break;
        }
        return true;
    }

    private void touchEventDown(MotionEvent event) {
        // find the closest route to touch point
        float touchX = event.getX();
        float touchY = event.getY();

        RouteView closestRouteView = null;
        int closestViewDistance = -1;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            RouteView rv = (RouteView) getChildAt(i);
            if (rv.getRoute().isClaimed()) {
                continue;
            }
            int dist = rv.getDistance(touchX, touchY);
            if (dist >= 0) {
                if (closestRouteView == null) {
                    closestRouteView = rv;
                    closestViewDistance = dist;
                } else {
                    if (dist < closestViewDistance) {
                        // cases where routes are the same distance are negligible
                        closestRouteView = rv;
                        closestViewDistance = dist;
                    }
                }
            }
        }

        // closest route is selected
        if (closestRouteView == null) {
            // there's no route that's close by
            return;
        }
        routeSelected(closestRouteView);
    }

    private void calculateRouteCoordinates(RouteView routeView) {
        CityManager cm = CityManager.getInstance();
        Route route = routeView.getRoute();
        City c1 = cm.get(route.get_source());
        City c2 = cm.get(route.get_dest());
        int xVector = Math.abs(c1.getX() - c2.getX());
        int yVector = Math.abs(c1.getY() - c2.getY());
        double c1Angle = Math.atan((double)yVector / xVector);
        double c2Angle = Math.atan((double)xVector / yVector);
        int cityDistance = _cityRadius + 2;

        double c1XVector = cityDistance * Math.cos(c1Angle);
        double c1YVector = cityDistance * Math.sin(c1Angle);
        if (c1.getX() > c2.getX()) c1XVector = -c1XVector;
        if (c1.getY() > c2.getY()) c1YVector = -c1YVector;
        int routeX1 = (int) Math.round(c1.getX() + c1XVector);
        int routeY1 = (int) Math.round(c1.getY() + c1YVector);

        double c2XVector = cityDistance * Math.sin(c2Angle);
        double c2YVector = cityDistance * Math.cos(c2Angle);
        if (c1.getX() < c2.getX()) c2XVector = -c2XVector;
        if (c1.getY() < c2.getY()) c2YVector = -c2YVector;
        int routeX2 = (int) Math.round(c2.getX() + c2XVector);
        int routeY2 = (int) Math.round(c2.getY() + c2YVector);

        int index = route.getDoubleRoute();
        if (index == 0) {
            routeView.setCoordinates(routeX1, routeY1, routeX2, routeY2);
            return;
        }

        // this is a double route; calculate offset
        double offsetLength = ((double)RouteView.getLineWidth() / 2.0) + 2.0;
        int xOffset = (int) Math.round(offsetLength * Math.sin(c1Angle));
        int yOffset = (int) Math.round(offsetLength * Math.cos(c1Angle));
        if (c1.getX() > c2.getX() && c1.getY() > c2.getY()) xOffset = -xOffset;
        if (c1.getX() < c2.getX() && c1.getY() < c2.getY()) yOffset = -yOffset;
        if (index == 1) {
            routeView.setCoordinates(routeX1 + xOffset, routeY1 + yOffset,
                    routeX2 + xOffset, routeY2 + yOffset);
        } else if (index == 2) {
            routeView.setCoordinates(routeX1 - xOffset, routeY1 - yOffset,
                    routeX2 - xOffset, routeY2 - yOffset);
        }
    }
}
