package FilterTheSpireHistory;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;

import java.util.ArrayList;

public class FilterTheSpireHistory {
    private static FilterTheSpireHistory singleton = null;

    public static FilterTheSpireHistory getInstance(){
        if (singleton == null){
            singleton = new FilterTheSpireHistory();
        }
        return singleton;
    }

    private FilterTheSpireHistory(){
    }
}
