package FilterTheSpireHistory.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;

import java.util.ArrayList;

public class CardFilterScreen {
    public boolean isShowing = false;
    public ArrayList<AbstractCard> cardList;
    public ArrayList<AbstractCard> selectedCards = new ArrayList<>();
    public ArrayList<AbstractCard> initialCards = new ArrayList<>();
    public GridCardSelectScreen gridSelectScreen = new GridCardSelectScreen();
    CardGroup group;

    public CardFilterScreen(){
        cardList = CardLibrary.getCardList(CardLibrary.LibraryType.RED);
        cardList.addAll(CardLibrary.getCardList(CardLibrary.LibraryType.GREEN));
        cardList.addAll(CardLibrary.getCardList(CardLibrary.LibraryType.BLUE));
        cardList.addAll(CardLibrary.getCardList(CardLibrary.LibraryType.PURPLE));
        cardList.addAll(CardLibrary.getCardList(CardLibrary.LibraryType.COLORLESS));
        cardList.addAll(CardLibrary.getCardList(CardLibrary.LibraryType.CURSE));

        group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c: cardList) {
            group.addToTop(c);
        }
    }

    public void open(){
        gridSelectScreen.open(group, 0, true, "Choose cards to filter on");
    }

    public void render(SpriteBatch sb){
        gridSelectScreen.render(sb);
    }

    public void update(){
        gridSelectScreen.update();
    }
}
