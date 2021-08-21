package FilterTheSpireHistory.patches;

import FilterTheSpireHistory.ui.FilteredHistoryButton;
import FilterTheSpireHistory.ui.RelicFilterScreen;
import FilterTheSpireHistory.utils.ExtraColors;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.runHistory.RunHistoryScreen;

public class RunHistoryScreenPatch {
    private static FilteredHistoryButton relicFilterButton = new FilteredHistoryButton(256, 700, "Relic Filter");
    private static FilteredHistoryButton cardFilterButton = new FilteredHistoryButton(256, 600, "Card Filter");
    private static RelicFilterScreen screen = new RelicFilterScreen();

    @SpirePatch(clz= RunHistoryScreen.class, method="render")
    public static class RenderFilteredButton {
        public static void Postfix(RunHistoryScreen __instance, SpriteBatch sb) {
            relicFilterButton.render(sb);
            cardFilterButton.render(sb);

            if (screen.isShowing){
                screen.render(sb);
            }
        }
    }

    @SpirePatch(clz= RunHistoryScreen.class, method="update")
    public static class UpdateFilteredButton {
        public static void Postfix(RunHistoryScreen __instance) {
            relicFilterButton.update();
            cardFilterButton.update();
            screen.update();

            if (relicFilterButton.hb.clickStarted) {
                screen.isShowing = true;
            }
            if (screen.isShowing){
                screen.enableHitboxes(true);
            }

            if (cardFilterButton.hb.clicked) {
                // card filter button clicked
            }
        }
    }

    @SpirePatch(clz= RunHistoryScreen.class, method="hide")
    public static class HideFilteredButton {
        public static void Postfix(RunHistoryScreen __instance) {
            relicFilterButton.hide();
            cardFilterButton.hide();
        }
    }

    @SpirePatch(clz= RunHistoryScreen.class, method="open")
    public static class ShowFilteredButton {
        public static void Postfix(RunHistoryScreen __instance) {
            relicFilterButton.show();
            cardFilterButton.show();
        }
    }
}
