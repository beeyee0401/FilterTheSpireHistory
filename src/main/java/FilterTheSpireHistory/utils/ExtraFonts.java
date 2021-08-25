package FilterTheSpireHistory.utils;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;

import java.util.HashMap;

import static com.megacrit.cardcrawl.ui.panels.ExhaustPanel.fontScale;

public class ExtraFonts {
    private static BitmapFont LARGE_NUMBER_FONT;
    private static BitmapFont CONFIG_TITLE_FONT;

    public static BitmapFont configTitleFont() {
        if (CONFIG_TITLE_FONT == null) {
            CONFIG_TITLE_FONT = prepFont(34.0f,
                    ExtraColors.TEXT_BORDER_COLOR,
                    2.0f,
                    Settings.QUARTER_TRANSPARENT_BLACK_COLOR.cpy(),
                    (int)(3.0f * Settings.xScale),
                    (int)(3.0f * Settings.yScale)
            );
        }

        return CONFIG_TITLE_FONT;
    }

    public static BitmapFont largeNumberFont() {
        if (LARGE_NUMBER_FONT == null) {
            LARGE_NUMBER_FONT = prepFont(210.0f,
                    ExtraColors.PINK_BORDER_COLOR,
                    8.0f,
                    Settings.QUARTER_TRANSPARENT_BLACK_COLOR.cpy(),
                    (int)(3.0f * Settings.xScale),
                    (int)(3.0f * Settings.yScale)
            );
        }
        return LARGE_NUMBER_FONT;
    }

    private static BitmapFont prepFont(float size,
                                       Color borderColor,
                                       float borderWidth,
                                       Color shadowColor,
                                       int shadowOffsetX,
                                       int shadowOffsetY
    ) {
        FreeTypeFontGenerator g;

        HashMap<String, FreeTypeFontGenerator> generators = (HashMap<String, FreeTypeFontGenerator>) ReflectionHacks.getPrivateStatic(FontHelper.class, "generators");
        //FileHandle fontFile = (FileHandle) ReflectionHacks.getPrivateStatic(FontHelper.class, "fontFile");

        FileHandle fontFile = Gdx.files.internal("font/Kreon-Bold.ttf");

        if (generators.containsKey(fontFile.path())) {
            g = (FreeTypeFontGenerator)generators.get(fontFile.path());
        } else {
            System.out.println("ERROR: this shouldn't occur!");
            // TODO: throw an exception I guess

            g = new FreeTypeFontGenerator(fontFile);
            generators.put(fontFile.path(), g);
        }

        if (Settings.BIG_TEXT_MODE) {
            size *= 1.2F;
        }

        return prepFont(g, size, borderColor, borderWidth, shadowColor, shadowOffsetX, shadowOffsetY);
    }


    private static BitmapFont prepFont(FreeTypeFontGenerator g,
                                       float size,
                                       Color borderColor,
                                       float borderWidth,
                                       Color shadowColor,
                                       int shadowOffsetX,
                                       int shadowOffsetY
    ) {
        FreeTypeFontGenerator.FreeTypeFontParameter p = new FreeTypeFontGenerator.FreeTypeFontParameter();
        p.characters = "";
        p.incremental = true;
        p.size = Math.round(size * fontScale * Settings.scale);

        p.gamma = (float) 0.9;
        p.spaceX = 0;
        p.spaceY = 0;

        p.borderColor = borderColor;
        p.borderStraight = false;
        p.borderWidth = borderWidth;
        p.borderGamma = (float) 0.9;

        p.shadowColor = shadowColor;
        p.shadowOffsetX = shadowOffsetX;
        p.shadowOffsetY = shadowOffsetY;

        p.minFilter = Texture.TextureFilter.Nearest;
        p.magFilter = Texture.TextureFilter.MipMapLinearNearest;

        g.scaleForPixelHeight(p.size);
        BitmapFont font = g.generateFont(p);
        font.setUseIntegerPositions(true);
        font.getData().markupEnabled = true;
        if (LocalizedStrings.break_chars != null) {
            font.getData().breakChars = LocalizedStrings.break_chars.toCharArray();
        }

        return font;
    }

}
