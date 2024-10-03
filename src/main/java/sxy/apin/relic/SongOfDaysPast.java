package sxy.apin.relic;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import sxy.apin.helper.FurinaHelper;

// 昔时之歌 战斗开始时，若生命值不足40%，则获得15金币
public class SongOfDaysPast extends CustomRelic {
    public static final String ID = FurinaHelper.makeRelicID(SongOfDaysPast.class.getSimpleName());
    public static final String IMG_PATH = "sxy/apin/img/relic/large/relic_8.png";
//    public static final String OUTLINE_PATH = "sxy/apin/img/relic/outline/relic_1.png";

    public SongOfDaysPast() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RelicTier.COMMON, LandingSound.SOLID);
    }

    // 获取遗物描述，但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SongOfDaysPast();
    }

    @Override
    public void atBattleStart() {
        AbstractPlayer player = FurinaHelper.getPlayer();
        if (player.currentHealth * 10 <= player.maxHealth * 4) {
            this.flash();
            player.gainGold(15);
        }
    }

}
