package client.util;

import android.content.res.Resources;
import android.support.v4.content.res.ResourcesCompat;

import com.pjohnst5icloud.tickettoride.R;

import shared.enumeration.PlayerColor;
import shared.enumeration.TrainColor;

public class ColorPicker {
    public static int getRouteColor(Resources res, TrainColor color) {
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

    public static int getRouteColor(Resources res, PlayerColor color) {
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

    public static int getPlayerColor(Resources res, PlayerColor color) {
        switch (color) {
            case BLUE:
                return ResourcesCompat.getColor(res, R.color.blue, null);
            case GREEN:
                return ResourcesCompat.getColor(res, R.color.green, null);
            case RED:
                return ResourcesCompat.getColor(res, R.color.red, null);
            case YELLOW:
                return ResourcesCompat.getColor(res, R.color.yellow, null);
            default:
                return ResourcesCompat.getColor(res, R.color.route_black, null);
        }
    }
}
