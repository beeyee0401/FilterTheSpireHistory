package FilterTheSpireHistory.patches;

import FilterTheSpireHistory.mainMenu.FilteredHistoryButton;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.runHistory.RunHistoryScreen;

public class RunHistoryScreenPatch {
    private static FilteredHistoryButton button = new FilteredHistoryButton();

    @SpirePatch(clz= RunHistoryScreen.class, method="render")
    public static class RenderFilteredButton {
        public static void Postfix(RunHistoryScreen __instance, SpriteBatch sb) {
            button.render(sb);
        }
    }

    @SpirePatch(clz= RunHistoryScreen.class, method="update")
    public static class UpdateFilteredButton {
        public static void Postfix(RunHistoryScreen __instance) {
            button.update();
        }
    }

    @SpirePatch(clz= RunHistoryScreen.class, method="hide")
    public static class HideFilteredButton {
        public static void Postfix(RunHistoryScreen __instance) {
            button.hide();
        }
    }

    @SpirePatch(clz= RunHistoryScreen.class, method="open")
    public static class ShowFilteredButton {
        public static void Postfix(RunHistoryScreen __instance) {
            button.show();
        }
    }
}
