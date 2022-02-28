package gridgame.controller;

import java.awt.*;
import java.util.Random;

/**
 * Class to store the color palette for the game
 */
public class ColorTemplate {

    private Random random;

    private static ColorTemplate colorTemplate;

    private ColorTemplate() {
        random = new Random();
    }

    public static ColorTemplate instance() {
        if(colorTemplate == null) {
            System.out.println("new");
            colorTemplate = new ColorTemplate();
        }
        return colorTemplate;
    }

    public void setSeed(int seed) {
        random = new Random(seed);
    }
    // radioactive blue, mauve, dead salmon, light pink, fuchsia, electric purple, slippery green, yes! beige
    public static final Color[] SERENITY_NOW = {new Color (0x1e90ff) , new Color(0xadb1ed), new Color(0xdd8a8a),new Color(0xf9c1c4), new Color(0xff5252),
            new Color(0xe066ff), new Color (0x00ff7f), new Color(0xc39797)
    };
    public static final Color [] SUMMER_OF_GEORGE = { new Color (0xFF5733) , new Color(0xD1EA46), new Color(0x33FFCA),new Color(0x3342FF ), new Color(0x7D46EA),
            new Color(0x86ACC7), new Color (0x970163 ), new Color(0xD5A84E)
    };

    //Change this line to change the Game Colors
    public static final Color[] ACTIVE_COLORS = ColorTemplate.SERENITY_NOW;

    /** Return a random color **/
    public Color randomColorPicker() {
        return ACTIVE_COLORS[random.nextInt(ACTIVE_COLORS.length)];
    }
}
