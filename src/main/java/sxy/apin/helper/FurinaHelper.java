package sxy.apin.helper;

import com.badlogic.gdx.graphics.Color;

public class FurinaHelper {
    public static final String MOD_ID = "ApinFurina";
    // 89, 138, 194
    public static final Color MY_COLOR = new Color(89.0F / 255.0F, 138.0F / 255.0F, 194.0F / 255.0F, 1.0F);
    public static String makeCardID(String id) {
        return MOD_ID + ":" + id;
    }

    public static String makeRelicID(String id) {
        return MOD_ID + ":" + id;
    }

    public static String makePowerID(String id) {
        return MOD_ID + ":" + id;
    }
}
