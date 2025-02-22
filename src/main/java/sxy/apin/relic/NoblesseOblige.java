package sxy.apin.relic;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import sxy.apin.helper.FurinaHelper;

// 昔日宗室之仪 每回合开始时,生命减少到50%，获得减少值2倍的格挡。
public class NoblesseOblige extends CustomRelic {
    public static final String ID = FurinaHelper.makeRelicID(NoblesseOblige.class.getSimpleName());
    public static final String IMG_PATH = "sxy/apin/img/relic/large/relic_7.png";
//    public static final String OUTLINE_PATH = "sxy/apin/img/relic/outline/relic_1.png";

    public NoblesseOblige() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RelicTier.COMMON, LandingSound.SOLID);
    }

    // 获取遗物描述，但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new NoblesseOblige();
    }

    @Override
    public void atBattleStart() {
        AbstractPlayer player = FurinaHelper.getPlayer();
        if (player.currentHealth > (int) (player.maxHealth * 0.8)) {
            this.flash();
            int extraHealth = player.currentHealth - (int) (player.maxHealth * 0.8);
            FurinaHelper.damage(player, player, extraHealth, DamageInfo.DamageType.HP_LOSS);
            FurinaHelper.addToBottom(new GainBlockAction(player, extraHealth * 2));
        }
    }

}
