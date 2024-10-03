package sxy.apin.relic;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import sxy.apin.helper.FurinaHelper;

// 追忆之注连 打出牌时，若持有层数大于15的正面效果：则减少3层，获得1力量。
public class ShimenawasReminiscence extends CustomRelic {
    public static final String ID = FurinaHelper.makeRelicID(ShimenawasReminiscence.class.getSimpleName());
    public static final String IMG_PATH = "sxy/apin/img/relic/large/relic_10.png";
//    public static final String OUTLINE_PATH = "sxy/apin/img/relic/outline/relic_1.png";

    public ShimenawasReminiscence() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RelicTier.COMMON, LandingSound.SOLID);
    }

    // 获取遗物描述，但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ShimenawasReminiscence();
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        AbstractPlayer player = FurinaHelper.getPlayer();
        AbstractPower maxPower = null;
        int maxAmount = 15;
        for (AbstractPower power : player.powers) {
            if (power.amount > maxAmount && power.type == AbstractPower.PowerType.BUFF) {
                maxPower = power;
                maxAmount = power.amount;
            }
        }
        if (maxPower != null) {
            this.flash();
            FurinaHelper.reducePlayerPower(maxPower.ID, 3);
            FurinaHelper.applyPower(player, player, new StrengthPower(player, 1), 1);
        }
    }
}
