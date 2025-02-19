package sxy.apin.relic;

import basemod.abstracts.CustomRelic;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import sxy.apin.helper.FurinaHelper;

// 绝缘之旗印 第三回合开始时，你可以从抽牌堆中选择一张技能牌加入手牌。
public class EmblemOfSeveredFate extends CustomRelic {
    public static final String ID = FurinaHelper.makeRelicID(EmblemOfSeveredFate.class.getSimpleName());
    public static final String IMG_PATH = "sxy/apin/img/relic/large/relic_5.png";
//    public static final String OUTLINE_PATH = "sxy/apin/img/relic/outline/relic_1.png";

    public EmblemOfSeveredFate() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RelicTier.COMMON, LandingSound.SOLID);
    }

    // 获取遗物描述，但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new EmblemOfSeveredFate();
    }

    @Override
    public void atTurnStart() {
        this.counter++;
        if (this.counter == 3) {
            this.flash();
            AbstractPlayer p = AbstractDungeon.player;
            FurinaHelper.addToBottom(new SelectCardsAction(p.drawPile.group, 1, "选择一张牌加入手牌", list -> {
                if (!list.isEmpty()) {
                    AbstractCard c = list.get(0);
                    AbstractDungeon.actionManager.addToTop(new MoveCardsAction(p.hand, p.drawPile, card -> card == c, 1 ));
                }
            }));
        }
    }

    @Override
    public void atBattleStart() {
        this.counter = 0;
    }
}
