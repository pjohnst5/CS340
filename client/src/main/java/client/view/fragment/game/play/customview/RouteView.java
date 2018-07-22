package client.view.fragment.game.play.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import shared.enumeration.CityManager;
import shared.model.City;
import shared.model.Route;

/**
 * Created by jtyler17 on 7/21/18.
 */

public class RouteView extends View {
    private Route _route;
    private Path _path;
    private RectF _bounds;

    private static Paint _linePaintNotSelected;
    private static Paint _linePaintSelected;
    static {
        _linePaintNotSelected = new Paint(Paint.ANTI_ALIAS_FLAG);
        _linePaintNotSelected.setStyle(Paint.Style.STROKE);
        _linePaintNotSelected.setColor(0xff000000);
        _linePaintNotSelected.setStrokeWidth(10.0f);
        _linePaintSelected = new Paint(Paint.ANTI_ALIAS_FLAG);
        _linePaintNotSelected.setStyle(Paint.Style.STROKE);
        _linePaintSelected.setColor(0xffffffff);
        _linePaintSelected.setStrokeWidth(10.0f);
    }
    private boolean _selected;

    public RouteView(Context context) {
        this(context, null);
    }

    public RouteView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public RouteView initialize(Route route) {
        _route = route;

        return this;
    }

    public void setSelected(boolean b) {
        _selected = b;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        CityManager cm = CityManager.getInstance();
        City c1 = cm.get(_route.get_source());
        City c2 = cm.get(_route.get_dest());
        int numSegments = _route.get_pathLength();

        Paint selectedPaint = (_selected) ? _linePaintSelected : _linePaintNotSelected;
        _path = new Path();
        _path.moveTo(c1.getX(), c1.getY());
        _path.lineTo(c2.getX(), c2.getY());
        canvas.drawPath(_path, selectedPaint);

        _bounds = new RectF();
        _path.computeBounds(_bounds, true);
//        canvas.drawLine(c1.getX(), c1.getY(), c2.getX(), c2.getY(), selectedPaint);
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
                    parent.routeSelected(this);
                }
                break;
        }
        return true;
    }
}
