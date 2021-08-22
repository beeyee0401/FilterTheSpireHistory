package FilterTheSpireHistory.ui;

import FilterTheSpireHistory.utils.ExtraColors;
import FilterTheSpireHistory.utils.ExtraFonts;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class RelicFilterScreen {
    private TreeSet<String> relics = new TreeSet<>();
    private HashMap<String, RelicUIObject> relicUIObjects = new HashMap<>();
    private Texture TEX_BG = new Texture("images/config_screen_bg.png");
    private FilteredHistoryButton returnButton = new FilteredHistoryButton(256, 250, "Return");
    public ArrayList<String> selectedRelics = new ArrayList<>();
    public boolean isShowing = false;

    public RelicFilterScreen() {
        setup();
    }

    private void populateRelics() {
        ArrayList<String> relics = new ArrayList<>();
        AbstractRelic.RelicTier[] tiers = new AbstractRelic.RelicTier[] {
                AbstractRelic.RelicTier.COMMON,
                AbstractRelic.RelicTier.UNCOMMON,
                AbstractRelic.RelicTier.RARE,
                AbstractRelic.RelicTier.BOSS,
                AbstractRelic.RelicTier.SHOP,
                AbstractRelic.RelicTier.SPECIAL
        };

        AbstractPlayer.PlayerClass[] classes = new AbstractPlayer.PlayerClass[]{
                AbstractPlayer.PlayerClass.IRONCLAD,
                AbstractPlayer.PlayerClass.THE_SILENT,
                AbstractPlayer.PlayerClass.DEFECT,
                AbstractPlayer.PlayerClass.WATCHER
        };

        for (AbstractRelic.RelicTier tier: tiers) {
            for (AbstractPlayer.PlayerClass c: classes){
                RelicLibrary.populateRelicPool(relics, tier, c);
            }
        }

        this.relics.addAll(relics);
    }

    private void makeUIObjects() {
        // Note: relic textures are 128x128 originally, with some internal spacing
        float left = 410.0f;
        float top = 587.0f;

        float spacing = 84.0f;

        int ix = 0;
        int iy = 0;
        final int perRow = 5;

        for (String id : relics) {
            float tx = left + ix * spacing;
            float ty = top - iy * spacing;

            relicUIObjects.put(id, new RelicUIObject(this, id, tx, ty));

            ix++;
            if (ix > perRow) {
                ix = 0;
                iy++;
            }
        }
    }

    private void setup() {
        populateRelics();
        makeUIObjects();
    }

    public void renderForeground(SpriteBatch sb) {
        sb.setColor(Color.WHITE);

        for (RelicUIObject x : relicUIObjects.values())
            x.render(sb);

        // Title text
        float titleLeft = 386.0f;
        float titleBottom = 819.0f;
        FontHelper.renderFontLeftDownAligned(sb, ExtraFonts.configTitleFont(), "Neow Boss Swaps", titleLeft * Settings.scale, titleBottom * Settings.scale, Settings.GOLD_COLOR);

        float infoLeft = 1120.0f;
        float infoTopMain = 667.0f;
        float infoTopControls = 472.0f;

        FontHelper.renderSmartText(sb,
                FontHelper.tipBodyFont,
                "This filter allows you to choose which Boss Relics will appear from Neow's swap option. If no relics are selected, it will choose from the entire pool.",
                infoLeft * Settings.scale,
                infoTopMain * Settings.scale,
                371.0f * Settings.scale,
                30.0f * Settings.scale,
                Settings.CREAM_COLOR);

        FontHelper.renderSmartText(sb,
                FontHelper.tipBodyFont,
                "Controls: NL Click to toggle NL Right+Click to select just one NL NL Shift+Click to select all NL Shift+Right+Click to clear all NL Alt+Click to invert all",
                infoLeft * Settings.scale,
                infoTopControls * Settings.scale,
                371.0f * Settings.scale,
                30.0f * Settings.scale,
                Color.GRAY);
    }

    public void enableHitboxes(boolean enabled) {
        for (RelicUIObject obj : relicUIObjects.values()) {
            if (enabled)
                obj.enableHitbox();
            else
                obj.disableHitbox();
        }

        if (enabled && isShowing){
            this.returnButton.show();
        } else{
            this.returnButton.hide();
            isShowing = false;
        }
    }

    public void render(SpriteBatch sb) {
        this.returnButton.render(sb);

        sb.setColor(ExtraColors.SCREEN_DIM);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0, 0, Settings.WIDTH, Settings.HEIGHT);

        // Draw our screen texture in the center
        sb.setColor(Color.WHITE);
        sb.draw(TEX_BG,
                (Settings.WIDTH - (TEX_BG.getWidth() * Settings.scale)) * 0.5f,
                (Settings.HEIGHT - (TEX_BG.getHeight() * Settings.scale)) * 0.5f,
                TEX_BG.getWidth() * Settings.scale,
                TEX_BG.getHeight() * Settings.scale
        );

        renderForeground(sb);
    }

    public void update() {
        this.returnButton.update();
        for (RelicUIObject x : relicUIObjects.values())
            x.update();

        if (this.returnButton.hb.clickStarted){
            enableHitboxes(false);
            CardCrawlGame.mainMenuScreen.runHistoryScreen.refreshData();
        }
    }

    // --------------------------------------------------------------------------------

    public void clearAll() {
        for (RelicUIObject obj : relicUIObjects.values()) {
            obj.isEnabled = false;
        }

        refreshFilters();
    }

    private void select(String id) {
        if (relicUIObjects.containsKey(id)) {
            relicUIObjects.get(id).isEnabled = true;
            refreshFilters();
        }
    }

    public void selectOnly(String id) {
        if (relicUIObjects.containsKey(id)) {
            clearAll();
            relicUIObjects.get(id).isEnabled = true;
            refreshFilters();
        }
    }

    public void invertAll() {
        for (RelicUIObject obj : relicUIObjects.values()) {
            obj.isEnabled = !obj.isEnabled;
        }

        refreshFilters();
    }

    public void selectAll() {
        for (RelicUIObject obj : relicUIObjects.values()) {
            obj.isEnabled = true;
        }

        refreshFilters();
    }

    // --------------------------------------------------------------------------------

    public ArrayList<String> getEnabledRelics() {
        ArrayList<String> list = new ArrayList<>();

        for (RelicUIObject obj : relicUIObjects.values()) {
            if (obj.isEnabled)
                list.add(obj.relicID);
        }

        return list;
    }

    public void refreshFilters() {
        selectedRelics = getEnabledRelics();
    }
}
