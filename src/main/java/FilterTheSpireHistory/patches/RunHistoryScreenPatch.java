package FilterTheSpireHistory.patches;

import FilterTheSpireHistory.FilterTheSpireHistory;
import FilterTheSpireHistory.ui.ActionButton;
import FilterTheSpireHistory.ui.CardFilterScreen;
import FilterTheSpireHistory.ui.RelicFilterScreen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.runHistory.RunHistoryScreen;
import com.megacrit.cardcrawl.screens.stats.RunData;

import java.util.ArrayList;

public class RunHistoryScreenPatch {
    private static ActionButton relicFilterButton = new ActionButton(256, 400, "Relic Filter");
    private static ActionButton cardFilterButton = new ActionButton(256, 300, "Card Filter");
    private static RelicFilterScreen relicScreen = new RelicFilterScreen();
    private static CardFilterScreen cardScreen = new CardFilterScreen();

    @SpirePatch(clz= RunHistoryScreen.class, method="render")
    public static class RenderFilteredButton {
        public static void Postfix(RunHistoryScreen __instance, SpriteBatch sb) {
            relicFilterButton.render(sb);
            cardFilterButton.render(sb);

            if (relicScreen.isShowing){
                relicScreen.render(sb);
            }

            if (cardScreen.isShowing){
                cardScreen.render(sb);
            }
        }
    }

    @SpirePatch(clz= RunHistoryScreen.class, method="update")
    public static class PreventHitboxesPatch {
        public static SpireReturn Prefix(RunHistoryScreen __instance) {
            relicFilterButton.update();
            cardFilterButton.update();

            if (relicFilterButton.hb.clickStarted) {
                relicScreen.isShowing = true;
                relicScreen.initialRelics.clear();
                relicScreen.initialRelics.addAll(relicScreen.selectedRelics);
            }
            if (relicScreen.isShowing){
                relicScreen.update();
                relicScreen.enableHitboxes(true);
                return SpireReturn.Return(null);
            }

            if (cardFilterButton.hb.clicked) {
                cardScreen.isShowing = true;
                cardScreen.initialCards.clear();
                cardScreen.initialCards.addAll(cardScreen.selectedCards);
            }
            if (cardScreen.isShowing){
                cardScreen.update();
                return SpireReturn.Return(null);
            }

            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz= RunHistoryScreen.class, method="hide")
    public static class HideFilteredButton {
        public static void Postfix(RunHistoryScreen __instance) {
            relicFilterButton.hide();
            cardFilterButton.hide();
            relicScreen.clearSelections();
        }
    }

    @SpirePatch(clz= RunHistoryScreen.class, method="open")
    public static class ShowFilteredButton {
        public static void Postfix(RunHistoryScreen __instance) {
            relicFilterButton.show();
            cardFilterButton.show();
        }
    }

    @SpirePatch(clz= RunHistoryScreen.class, method="resetRunsDropdown")
    public static class FilterRunsPatch {
        @SpireInsertPatch(
            rloc = 303-229,
            localvars = {"filteredRuns"}
        )
        public static void Insert(RunHistoryScreen __instance, ArrayList<RunData> filteredRuns) {
            if (relicScreen.selectedRelics.size() > 0){
                for (String relicId: relicScreen.selectedRelics) {
                    filteredRuns.removeIf(r -> !r.relics.contains(relicId));
                }
            }
        }
    }
}
