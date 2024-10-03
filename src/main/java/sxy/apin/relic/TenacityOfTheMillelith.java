package sxy.apin.relic;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import sxy.apin.helper.FurinaHelper;

// 千岩牢固 获得格挡时：若此次获得的格挡不低于20，则额外获得8点格挡。
public class TenacityOfTheMillelith extends CustomRelic {
    public static final String ID = FurinaHelper.makeRelicID(TenacityOfTheMillelith.class.getSimpleName());
    public static final String IMG_PATH = "sxy/apin/img/relic/large/relic_6.png";
//    public static final String OUTLINE_PATH = "sxy/apin/img/relic/outline/relic_1.png";

    public TenacityOfTheMillelith() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RelicTier.COMMON, LandingSound.SOLID);
    }

    // 获取遗物描述，但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new TenacityOfTheMillelith();
    }

    @Override
    public int onPlayerGainBlock(int blockAmount) {
        if (blockAmount >= 20) {
            this.flash();
            blockAmount += 8;
        }
        return blockAmount;
    }
}
