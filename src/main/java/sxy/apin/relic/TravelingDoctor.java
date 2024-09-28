package sxy.apin.relic;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import sxy.apin.helper.FurinaHelper;

public class TravelingDoctor extends CustomRelic {
    public static final String ID = FurinaHelper.makeRelicID("Traveling Doctor");
    public static final String IMG_PATH = "sxy/apin/img/relic/large/relic_1.png";
    public static final int HEALTH_NUM = 5;
    private int healCount = 0;

    public TravelingDoctor() {
        super(ID, ImageMaster.loadImage(IMG_PATH), RelicTier.COMMON, LandingSound.SOLID);
    }

    // 获取遗物描述，但原版游戏只在初始化和获取遗物时调用，故该方法等于初始描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new TravelingDoctor();
    }

    @Override
    public void onEquip() {
        this.healCount = 0;
    }

    @Override
    public void atBattleStart() {
        super.atBattleStart();
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    @Override
    public int onPlayerHeal(int healAmount) {
        this.healCount++;
        if (this.healCount >= 5) {
            this.healCount -= 5;
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, HEALTH_NUM));
        }
        return healAmount;
    }
}
