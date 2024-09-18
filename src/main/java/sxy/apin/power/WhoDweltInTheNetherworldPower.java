package sxy.apin.power;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sxy.apin.character.Furina;
import sxy.apin.helper.FurinaHelper;

/**
 * 若非处幽冥 消耗 。回合开始阶段获得 !M! 点 furina_mod:元素能量 。
 */
public class WhoDweltInTheNetherworldPower extends AbstractPower {
    // 能力的ID
    public static final String POWER_ID = FurinaHelper.makePowerID(WhoDweltInTheNetherworldPower.class.getSimpleName());
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public WhoDweltInTheNetherworldPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = amount;
        // 添加一大一小两张能力图
        String path128 = "sxy/apin/img/powers/Example84.png";
        String path48 = "sxy/apin/img/powers/Example32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 22, 22, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 8, 8, 32, 32);
        // 首次添加能力更新描述
        this.updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
    }

    @Override
    public void atStartOfTurn() {
        Furina.gainElementEnergy(this.amount);
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

}
