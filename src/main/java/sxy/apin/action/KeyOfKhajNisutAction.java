package sxy.apin.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sxy.apin.helper.FurinaHelper;

/**
 * 圣显之钥 斩杀时，永久获得 2 点生命上限。
 */
public class KeyOfKhajNisutAction extends AbstractGameAction {
    public DamageInfo info;
    private final AbstractCard card;

    public KeyOfKhajNisutAction(AbstractMonster target, DamageInfo info, AbstractCard card) {
        this.target = target;
        this.info = info;
        this.card = card;
    }

    @Override
    public void update() {
        this.target.damage(this.info);
        if ((this.target.isDying || this.target.currentHealth <= 0) && !this.target.halfDead
                && !this.target.hasPower("Minion")) {
            AbstractPlayer player = AbstractDungeon.player;
            player.increaseMaxHp(3, true);
            FurinaHelper.addToBottom(new ExhaustSpecificCardAction(card, AbstractDungeon.player.discardPile));
        }
        this.isDone = true;
    }
}
