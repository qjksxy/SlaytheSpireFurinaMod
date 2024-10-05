package sxy.apin.power;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sxy.apin.helper.FurinaHelper;

/**
 * 我已有觉察 孤心沙龙造成的伤害和治疗量提升50%。
 */
public class INowKnowItIsPower extends AbstractPower {
    // 能力的ID
    public static final String POWER_ID = FurinaHelper.makePowerID(INowKnowItIsPower.class.getSimpleName());
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private boolean upgraded;

    public INowKnowItIsPower(AbstractCreature owner, boolean upgraded) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = -1;
        this.upgraded = upgraded;
        // 添加一大一小两张能力图
        String path128 = "sxy/apin/img/powers/power_128/power_raw_80.png";
        String path48 = "sxy/apin/img/powers/power_48/power_raw_80.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 22, 22, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 8, 8, 32, 32);
        // 首次添加能力更新描述
        this.updateDescription();
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public boolean isUpgraded() {
        return upgraded;
    }

    public void upgrade() {
        this.upgraded = true;
    }

    public static double getINowKnowItIsPowerFactor() {
        INowKnowItIsPower power = (INowKnowItIsPower) FurinaHelper.getPower(INowKnowItIsPower.POWER_ID);
        double factor = 1.0;
        if (power != null) {
            if (power.isUpgraded()) {
                factor = 1.3;
            } else {
                factor = 1.4;
            }
        }
        return factor;
    }
}
