package sxy.apin.relic;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import sxy.apin.helper.FurinaHelper;

// 黄金剧团 受到未被格挡的伤害时，若生命值等于上限，则回复20%伤害值的生命。
public class GoldenTroupe extends CustomRelic {
    public static final String ID = FurinaHelper.makeRelicID(GoldenTroupe.class.getSimpleName());
    public static final String IMG_PATH = "sxy/apin/img/relic/large/relic_3.png";
//    public static final String OUTLINE_PATH = "sxy/apin/img/relic/outline/relic_1.png";

    public GoldenTroupe() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RelicTier.COMMON, LandingSound.SOLID);
    }

    // 获取遗物描述，但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new GoldenTroupe();
    }

    @Override
    public void onLoseHp(int damageAmount) {
        AbstractPlayer player = FurinaHelper.getPlayer();
        if (player.currentHealth == player.maxHealth) {
            this.flash();
            damageAmount = damageAmount / 5;
            FurinaHelper.addToBottom(new HealAction(player, player, damageAmount));
        }
    }
}
