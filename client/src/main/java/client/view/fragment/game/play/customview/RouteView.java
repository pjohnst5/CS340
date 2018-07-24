package client.view.fragment.game.play.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import client.util.ColorPicker;
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
    private Paint _boundsPaint; // testing
    static {
        _lineWidth = LINE_WIDTH;
        _linePaintNotSelected = new Paint(Paint.ANTI_ALIAS_FLAG);
        _linePaintNotSelected.setStyle(Paint.Style.STROKE);
        _linePaintNotSelected.setColor(0xff000000);
        _linePaintNotSelected.setStrokeWidth(_lineWidth + 5.0f);
        _linePaintNotSelected.setStrokeCap(Paint.Cap.ROUND);
        _linePaintSelected = new Paint(Paint.ANTI_ALIAS_FLAG);
        _linePaintSelected.setStyle(Paint.Style.STROKE);
        _linePaintSelected.setColor(0xffddddff);
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
        _boundsPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        _boundsPaint.setStyle(Paint.Style.STROKE);
        _boundsPaint.setStrokeWidth(2.0f);
    }

    public RouteView initialize(Route route) {
        _route = route;
        int color = ColorPicker.getRouteColor(getResources(), _route.get_color());
        _linePaint.setColor(color);
        _boundsPaint.setColor(color);
        return this;
    }

    public void setSelected(boolean b) {
        _selected = b;
    }

    public Route getRoute() {
        return _route;
    }

    public void redraw() { // FIXME: call after a route has been claimed or selected
        if (_route.isClaimed()) {
            _claimedPaint.setColor(ColorPicker.getRouteColor(getResources(), _route.get_claimedColor()));
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

        // compute bounds
        int width = Math.abs(c1X - c2X);
        int height = Math.abs(c1Y - c2Y);
        float pathCenterX = (float)(c1X + c2X) / 2.0f;
        float pathCenterY = (float)(c1Y + c2Y) / 2.0f;
        float radius = Math.max(width, height) / 2;
        radius += (float)_lineWidth / 2;
        _bounds = new RectF(pathCenterX - radius, pathCenterY - radius,
                pathCenterX + radius, pathCenterY + radius);

        int numSegments = _route.get_pathLength();
        float lineLength = (float) Math.sqrt(Math.pow(Math.abs(c1X - c2X), 2) + Math.pow(Math.abs(c1Y - c2Y), 2));
        float segmentLength = (lineLength / numSegments);
        if (numSegments > 1) segmentLength -= LINE_GAP * ((numSegments - 1) / (float) numSegments);

        _linePaint.setPathEffect(new DashPathEffect(new float[] {segmentLength, LINE_GAP}, 0));

        _linePaint.setStrokeWidth(_lineWidth);
        _claimedPaint.setStrokeWidth(_lineWidth);
        _claimedPaint.setPathEffect(new DashPathEffect(new float[] {segmentLength, LINE_GAP}, 0));
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
//        canvas.drawRect(_bounds, _boundsPaint); // uncomment to see route bounding boxes
    }

    public int getDistance(float touchX, float touchY) {
        // can't select claimed routes
        if (_route.isClaimed()) {
            return -1;
        }
        if (!_bounds.contains(touchX, touchY)) {
            return -1;
        }
        // calculate distance from the line
        int xVector = Math.abs(_c1.x() - _c2.x());
        int yVector = Math.abs(_c1.y() - _c2.y());
        double upperAngle = Math.atan((double)yVector / xVector);
        double lowerAngle = Math.atan((double)xVector / yVector);

        float originX = Math.min(_c1.x(), _c2.x());
        float originY;
        float xDiff = touchX - originX;
        float yDiff;
        if ((_c1.x() < _c2.x() && _c1.y() > _c2.y()) || (_c1.x() > _c2.x() && _c1.y() < _c2.y())) {
            // bottom left to top right
            originY = Math.max(_c1.y(), _c2.y());
            yDiff = originY - touchY;
        } else {
            // top left to bottom right
            originY = Math.min(_c1.y(), _c2.y());
            yDiff = touchY - originY;
        }

        double matchingX = yDiff * Math.tan(lowerAngle);
//        double matchingY = xDiff * Math.tan(upperAngle);

        double xDist = Math.abs(xDiff - matchingX);
//        double yDist = Math.abs(yDiff - matchingY);
//        double distance = Math.pow(xDist, 2) + Math.pow(yDist, 2);
        double distance = xDist * Math.sin(upperAngle);
        return (int) Math.round(distance);
    }
}
