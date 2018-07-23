package client.view.fragment.game.play.customview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.pjohnst5icloud.tickettoride.R;

import shared.enumeration.PlayerColor;
import shared.enumeration.TrainColor;
import shared.model.Point;
import shared.model.Route;

/**
 * Created by jtyler17 on 7/21/18.
 */

public class RouteView extends View {
    public static final int LINE_WIDTH = 15;
    public static final int LINE_WIDTH_SM = 6;
    public static int _lineWidth;
    public static final float LINE_GAP = 3.0f;
    private static Paint _linePaintNotSelected;
    private static Paint _linePaintSelected;
    private Paint _linePaint;
    private Paint _claimedPaint;
    static {
        _lineWidth = LINE_WIDTH;
        _linePaintNotSelected = new Paint(Paint.ANTI_ALIAS_FLAG);
        _linePaintNotSelected.setStyle(Paint.Style.STROKE);
        _linePaintNotSelected.setColor(0xff000000);
        _linePaintNotSelected.setStrokeWidth(_lineWidth + 5.0f);
        _linePaintNotSelected.setStrokeCap(Paint.Cap.ROUND);
        _linePaintSelected = new Paint(Paint.ANTI_ALIAS_FLAG);
        _linePaintSelected.setStyle(Paint.Style.STROKE);
        _linePaintSelected.setColor(0xffffffff);
        _linePaintSelected.setStrokeWidth(_lineWidth + 8.0f);
        _linePaintSelected.setStrokeCap(Paint.Cap.ROUND);
    }


    private Route _route;
    private Path _path;
    private RectF _bounds;
    private Point _c1;
    private Point _c2;

    private boolean _selected;

    public static void setLineWidth(GameMapView.MapSize mapSize) {
        switch (mapSize) {
            case SMALL:
                _lineWidth = LINE_WIDTH_SM;
                break;
            default:
                _lineWidth = LINE_WIDTH;
                break;
        }
        _linePaintNotSelected.setStrokeWidth(_lineWidth + 5.0f);
        _linePaintSelected.setStrokeWidth(_lineWidth + 8.0f);
    }

    public static int getLineWidth() {
        return _lineWidth;
    }


    public RouteView(Context context) {
        this(context, null);
    }

    public RouteView(Context context, AttributeSet attrs) {
        super(context, attrs);

        _linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        _linePaint.setStyle(Paint.Style.STROKE);
        _linePaint.setStrokeWidth(_lineWidth);
        _claimedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        _claimedPaint.setStyle(Paint.Style.STROKE);
        _claimedPaint.setStrokeWidth(_lineWidth);
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

        _linePaint.setStrokeWidth(_lineWidth);
        _claimedPaint.setStrokeWidth(_lineWidth);
//        _claimedPaint.setPathEffect(new DashPathEffect(new float[] {segmentLength, LINE_GAP}, 0));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    // Use our own defined method rather than onDraw, so we can call it from GameMapView in the
    //  order we want
    public void drawRoute(Canvas canvas) {
        Paint selectedPaint = (_selected) ? _linePaintSelected : _linePaintNotSelected;

        if (_route.isClaimed()) {
            canvas.drawPath(_path, _claimedPaint);
            return;
        }
        canvas.drawPath(_path, selectedPaint);
        canvas.drawPath(_path, _linePaint);
    }

    public int getDistance(float touchX, float touchY) {
        if (!_bounds.contains(touchX, touchY)) {
            return -1;
        }
        // calculate distance from the line
        int xVector = Math.abs(_c1.x() - _c2.x());
        int yVector = Math.abs(_c1.y() - _c2.y());
        double upperAngle = Math.atan((double)yVector / xVector);
        double lowerAngle = Math.atan((double)xVector / yVector);

//        float xDiff = touchX - _bounds.left;
//        float yDiff = touchY - _bounds.top; // FIXME: what about lines from bottom left to top right?
        float xDiff;
        float yDiff;
        if ((_c1.x() < _c2.x() && _c1.y() > _c2.y()) || (_c1.x() > _c2.x() && _c1.y() < _c2.y())) {
            xDiff = touchX - _bounds.left;
            yDiff = _bounds.bottom - touchY; // FIXME: I think it's all different
        } else {
            xDiff = touchX - -_bounds.left;
            yDiff = touchY - _bounds.top;
        }

        double matchingX = yDiff / Math.cos(lowerAngle);
        double matchingY = xDiff / Math.cos(upperAngle);

        double xDist = Math.abs(xDiff - matchingX);
        double yDist = Math.abs(yDiff - matchingY);
        double distance = Math.pow(xDist, 2) + Math.pow(yDist, 2);
        return (int) Math.round(distance);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        float touchX = event.getX();
//        float touchY = event.getY();
//
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                if (_bounds.contains(touchX, touchY)) {
//                    // line has been clicked
//                    GameMapView parent = (GameMapView) getParent();
//                    _selected = true;
//                    redraw();
//                    parent.routeSelected(this);
//                }
//                break;
//        }
//        return true;
//    }
}
