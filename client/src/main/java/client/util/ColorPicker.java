package client.util;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import com.pjohnst5icloud.tickettoride.R;

import shared.enumeration.CityName;
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
            case LOCOMOTIVE:
                return ResourcesCompat.getColor(res, R.color.magenta, null);
            default:
                return ResourcesCompat.getColor(res, R.color.route_black, null);
        }
    }

    public static int claimedColor(Resources res, PlayerColor color) {
        switch (color) {
            case BLUE:
                return ResourcesCompat.getColor(res, R.color.claimed_blue, null);
            case GREEN:
                return ResourcesCompat.getColor(res, R.color.claimed_green, null);
            case RED:
                return ResourcesCompat.getColor(res, R.color.claimed_red, null);
            case YELLOW:
                return ResourcesCompat.getColor(res, R.color.claimed_yellow, null);
            default:
                return ResourcesCompat.getColor(res, R.color.claimed_black, null);
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

    public static Drawable turnOrderIndicator(Resources res, PlayerColor color) {
        switch (color){
            case BLACK:
                return res.getDrawable(R.drawable.button_grey);
            case BLUE:
                return res.getDrawable(R.drawable.button_blue);
            case GREEN:
                return res.getDrawable(R.drawable.button_green);
            case RED:
                return res.getDrawable(R.drawable.button_red);
            case YELLOW:
                return res.getDrawable(R.drawable.button_yellow);
        }
        return res.getDrawable(R.drawable.button_grey);
    }

    public static String trainCardColorName(Resources res, TrainColor color) {
        switch (color) {
            case RED:
                return res.getString(R.string.color_red);
            case BLUE:
                return res.getString(R.string.color_blue);
            case PINK:
                return res.getString(R.string.pink);
            case BLACK:
                return res.getString(R.string.color_black);
            case GREEN:
                return res.getString(R.string.color_green);
            case WHITE:
                return res.getString(R.string.white);
            case ORANGE:
                return res.getString(R.string.orange);
            case YELLOW:
                return res.getString(R.string.color_yellow);
            case GRAY:
                return res.getString(R.string.gray);
            case LOCOMOTIVE:
                return res.getString(R.string.locomotive);
        }
        return null;
    }

    public static String convertCityNameToString(CityName city) {
        return city.name().replace("_", " ");
    }

    public static int trainCardDrawable(TrainColor color) {
        switch (color){
            case BLACK:
                return R.drawable.train_card_black;
            case BLUE:
                return R.drawable.train_card_blue;
            case GREEN:
                return R.drawable.train_card_green;
            case RED:
                return R.drawable.train_card_red;
            case YELLOW:
                return R.drawable.train_card_yellow;
            case PINK:
                return R.drawable.train_card_purple;
            case WHITE:
                return R.drawable.train_card_white;
            case ORANGE:
                return R.drawable.train_card_orange;
            case LOCOMOTIVE:
                return R.drawable.train_card_locomotive;
        }
        throw new RuntimeException("Unknown train card color: " + color.name());
    }

    public static int spToPx(Resources res, float sp) {
        return (int) (sp * res.getDisplayMetrics().scaledDensity);
    }
}
