package ch.tron.game.model;

import java.awt.*;

public class GameColors {

    private static final Color[] COLORS_10;

    static {
        COLORS_10 = new Color[10];
        COLORS_10[0] = new Color(44,123,246);
        COLORS_10[1] = new Color(155,86,163);
        COLORS_10[2] = new Color(229,93,156);
        COLORS_10[3] = new Color(237,95,93);
        COLORS_10[4] = new Color(233,135,58);
        COLORS_10[5] = new Color(243,185,75);
        COLORS_10[6] = new Color(120,183,86);
        COLORS_10[7] = new Color(140,140,140);
        COLORS_10[8] = new Color(67,70,74);
        COLORS_10[9] = new Color(28,29,30);
    }

    public static Color[] getColors10() {
        return COLORS_10;
    }
}
