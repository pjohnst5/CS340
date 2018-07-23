package client.view.fragment.game.play.customview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.pjohnst5icloud.tickettoride.R;

import shared.enumeration.CityManager;
import shared.enumeration.PlayerColor;
import shared.enumeration.TrainColor;
import shared.model.City;
import shared.model.Point;
import shared.model.Route;

/**
 * Created by jtyler17 on 7/21/18.
 */

public class RouteView extends View {
    public static final int LINE_WIDTH = 10;
    public static final float LINE_GAP = 3.0f;
    private static Paint _linePaintNotSelected;
    private static Paint _linePaintSelected;
    private Paint _linePaint;
    private Paint _claimedPaint;
    static {
        _linePaintNotSelected = new Paint(Paint.ANTI_ALIAS_FLAG);
        _linePaintNotSelected.setStyle(Paint.Style.STROKE);
        _linePaintNotSelected.setColor(0xff000000);
        _linePaintNotSelected.setStrokeWidth(LINE_WIDTH + 5.0f);
        _linePaintNotSelected.setStrokeCap(Paint.Cap.ROUND);
        _linePaintSelected = new Paint(Paint.ANTI_ALIAS_FLAG);
        _linePaintSelected.setStyle(Paint.Style.STROKE);
        _linePaintSelected.setColor(0xffffffff);
        _linePaintSelected.setStrokeWidth(LINE_WIDTH + 8.0f);
        _linePaintSelected.setStrokeCap(Paint.Cap.ROUND);
    }


    private Route _route;
    private Path _path;
    private RectF _bounds;
    private Point _c1;
    private Point _c2;
    private ShapeDrawable _line;

    private boolean _selected;
    private boolean _initialized;


    public RouteView(Context context) {
        this(context, null);
    }

    public RouteView(Context context, AttributeSet attrs) {
        super(context, attrs);

        _linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        _linePaint.setStyle(Paint.Style.STROKE);
        _linePaint.setStrokeWidth(LINE_WIDTH);
        _claimedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        _claimedPaint.setStyle(Paint.Style.STROKE);
        _claimedPaint.setStrokeWidth(LINE_WIDTH);

        _initialized = false;
    }

    public RouteView initialize(Route route) {
        _route = route;
        _linePaint.setColor(getTrainColor(_route.get_color()));
        return this;
    }

    private int getTrainColor(TrainColor color) {
        Resources res = getResources();
        switch (color) {
            case RED:
                return ResourcesCompat.getColor(res, R.color.red, null);
            case BLUE:
                return ResourcesCompat.getColor(res, R.color.blue, null);
            case GRAY:
                return ResourcesCompat.getColor(res, R.color.gray, null);
            case PINK:
                return ResourcesCompat.getColor(res, R.color.pink, null);
            case GREEN:
                return ResourcesCompat.getColor(res, R.color.green, null);
            case WHITE:
                return ResourcesCompat.getColor(res, R.color.white, null);
            case ORANGE:
                return ResourcesCompat.getColor(res, R.color.route_orange, null);
            case YELLOW:
                return ResourcesCompat.getColor(res, R.color.route_yellow, null);
            default:
                return ResourcesCompat.getColor(res, R.color.route_black, null);
        }
    }

    private int getPlayerColor(PlayerColor color) {
        Resources res = getResources();
        switch (color) {
            case BLUE:
                return ResourcesCompat.getColor(res, R.color.train_light_blue_disabled, null);
            case GREEN:
                return ResourcesCompat.getColor(res, R.color.train_light_green_disabled, null);
            case RED:
                return ResourcesCompat.getColor(res, R.color.train_light_red_disabled, null);
            case YELLOW:
                return ResourcesCompat.getColor(res, R.color.train_light_yellow_disabled, null);
            default:
                return ResourcesCompat.getColor(res, R.color.train_light_grey_disabled, null);
        }
    }

    public void setSelected(boolean b) {
        _selected = b;
    }

    public Route getRoute() {
        return _route;
    }

    public void redraw() { // FIXME: call after a route has been claimed or selected
        if (_route.isClaimed()) {
            _claimedPaint.setColor(getPlayerColor(_route.get_claimedColor()));
        }
        invalidate();
        requestLayout();
    }


    public void setCoordinates(int c1X, int c1Y, int c2X, int c2Y) {
        _c1 = new Point(c1X, c1Y);
        _c2 = new Point(c2X, c2Y);

        _path = new Path();
        _path.moveTo(_c1.x(), _c1.y());
        _path.lineTo(_c2.x(), _c2.y());
        _bounds = new RectF();
        _path.computeBounds(_bounds, true);

        int numSegments = _route.get_pathLength();
        float lineLength = (float) Math.sqrt(Math.pow(Math.abs(c1X - c2X), 2) + Math.pow(Math.abs(c1Y - c2Y), 2));
        float segmentLength = (lineLength / numSegments);
        if (numSegments > 1) segmentLength -= LINE_GAP * ((numSegments - 1) / (float) numSegments);

        _linePaint.setPathEffect(new DashPathEffect(new float[] {segmentLength, LINE_GAP}, 0));
//        _claimedPaint.setPathEffect(new DashPathEffect(new float[] {segmentLength, LINE_GAP}, 0));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        CityManager cm = CityManager.getInstance();
        City c1 = cm.get(_route.get_source());
        City c2 = cm.get(_route.get_dest());

        Paint selectedPaint = (_selected) ? _linePaintSelected : _linePaintNotSelected;
//        if (!_initialized) {
//            _line = new ShapeDrawable(new PathShape(_path, getWidth(), getHeight()));
//            _line.getPaint().set(_linePaint);
//            _initialized = true;
//        }

        if (_route.isClaimed()) {
            canvas.drawPath(_path, _claimedPaint);
            return;
        }
        canvas.drawPath(_path, selectedPaint);
        canvas.drawPath(_path, _linePaint);
//        _line.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (_bounds.contains(touchX, touchY)) {
                    // line has been clicked
                    GameMapView parent = (GameMapView) getParent();
                    _selected = true;
                    redraw();
                    parent.routeSelected(this);
                }
                break;
        }
        return true;
    }
}
