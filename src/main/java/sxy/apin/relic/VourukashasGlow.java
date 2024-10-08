package sxy.apin.relic;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import sxy.apin.character.Furina;
import sxy.apin.helper.FurinaHelper;

public class VourukashasGlow extends CustomRelic {
    public static final String ID = FurinaHelper.makeRelicID(VourukashasGlow.class.getSimpleName());
    public static final String IMG_PATH = "sxy/apin/img/relic/large/relic_2.png";
//    public static final String OUTLINE_PATH = "sxy/apin/img/relic/outline/relic_1.png";

    public VourukashasGlow() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RelicTier.COMMON, LandingSound.SOLID);
    }

    // 获取遗物描述，但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new VourukashasGlow();
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        Furina.switchArkhe();
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 10));
    }
}
