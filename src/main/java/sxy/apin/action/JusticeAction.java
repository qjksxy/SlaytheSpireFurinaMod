package sxy.apin.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

/**
 * 正义  0费  4伤  将1张牌放入抽牌堆。
 */
public class JusticeAction extends AbstractGameAction {
    private final AbstractPlayer p;
    public static final String UI_TEXT = "选择一张牌放入抽牌堆";

    public JusticeAction() {
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
            } else if (this.p.hand.size() == 1) {
                AbstractCard c = this.p.hand.getTopCard();
                if (c.cost > 0) {
                    c.freeToPlayOnce = true;
                }
                this.p.hand.moveToDeck(c, true);
                AbstractDungeon.player.hand.refreshHandLayout();
                this.isDone = true;
            } else {
                AbstractDungeon.handCardSelectScreen.open(UI_TEXT, 1, false);
                this.tickDuration();
            }
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                AbstractCard c;
                for (Iterator<AbstractCard> var1 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();
                     var1.hasNext();
                     this.p.hand.moveToDeck(c, true)) {
                    c = (AbstractCard) var1.next();
                }
                AbstractDungeon.player.hand.refreshHandLayout();
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }
            this.tickDuration();
        }
    }
}
