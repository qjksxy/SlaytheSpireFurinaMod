package sxy.apin.relic;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import sxy.apin.cards.basic.Bloom;
import sxy.apin.cards.basic.ElectroCharged;
import sxy.apin.cards.basic.Frozen;
import sxy.apin.cards.basic.Swirl;
import sxy.apin.cards.common.*;
import sxy.apin.helper.FurinaHelper;

public class Nighttime extends CustomRelic {
    public static final String ID = FurinaHelper.makeRelicID(Nighttime.class.getSimpleName());
    public static final String IMG_PATH = "sxy/apin/img/relic/large/relic_4.png";
    public double proba = 1.0;
//    public static final String OUTLINE_PATH = "sxy/apin/img/relic/outline/relic_1.png";

    public Nighttime() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RelicTier.COMMON, LandingSound.SOLID);
    }

    // 获取遗物描述，但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Nighttime();
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.flash();
        this.proba = 1.0;
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractCard c;
        int random = AbstractDungeon.cardRng.random(6);
        switch (random) {
            case 0:
                c = new Swirl();
                break;
            case 1:
                c = new Bloom();
                break;
            case 2:
                c = new Frozen();
                break;
            case 3:
                c = new Crystalize();
                break;
            case 4:
                c = new ElectroCharged();
                break;
            default:
                c = new Vaporize();
        }
        FurinaHelper.addToBottom(new MakeTempCardInDrawPileAction(c, 1, true, true));
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (card instanceof Swirl || card instanceof Bloom || card instanceof Frozen ||
        card instanceof Crystalize || card instanceof  ElectroCharged || card instanceof Vaporize) {
            if (AbstractDungeon.cardRng.random() < this.proba) {
                this.flash();
                card.upgrade();
                this.proba *= 0.85;
            }
        }
    }
}
