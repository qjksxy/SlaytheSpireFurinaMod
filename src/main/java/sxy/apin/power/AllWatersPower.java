package sxy.apin.power;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sxy.apin.helper.FurinaHelper;

/**
 * 众水 每当你获得5次气氛值时，获得1能量。
 */
public class AllWatersPower extends AbstractPower {
    // 能力的ID
    public static final String POWER_ID = FurinaHelper.makePowerID(AllWatersPower.class.getSimpleName());
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    // 标识是否为强化后能力
    public boolean flag;
    private int count = 0;

    public AllWatersPower(AbstractCreature owner, boolean flag) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.flag = flag;
        this.amount = -1;
        // 添加一大一小两张能力图
        String path128 = "sxy/apin/img/powers/power_128/power_raw_29.png";
        String path48 = "sxy/apin/img/powers/power_48/power_raw_29.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 22, 22, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 8, 8, 32, 32);
        // 首次添加能力更新描述
        this.updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurn() {
        int t = 0;
        if (this.flag && this.count >= 4) {
            t = count / 4;
            this.count -= t * 4;
        }
        if (this.count >= 5) {
            t = count / 5;
            this.count -= t * 5;
        }
        FurinaHelper.addToBottom(new GainEnergyAction(t));
        this.flash();
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
