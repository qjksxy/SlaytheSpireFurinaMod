package sxy.apin.power;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sxy.apin.character.Furina;
import sxy.apin.helper.FurinaHelper;

/**
 * 荒性能量 通过引发芒荒反应击晕敌人。
 */
public class OusiaEmergy extends AbstractPower {
    // 能力的ID
    public static final String POWER_ID = FurinaHelper.makePowerID(OusiaEmergy.class.getSimpleName());
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public OusiaEmergy(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.DEBUFF;
        this.amount = 1;
        // 添加一大一小两张能力图
        String path128 = "sxy/apin/img/powers/power_128/power_raw_65.png";
        String path48 = "sxy/apin/img/powers/power_48/power_raw_65.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 22, 22, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 8, 8, 32, 32);
        // 首次添加能力更新描述
        this.updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void stackPower(int stackAmount) {
        if (this.amount == -1) {
            return;
        }
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount >= 5) {
            this.amount = 5;
        }
    }

    @Override
    public void onInitialApplication() {
        AbstractCreature target = this.owner;
        AbstractPlayer player = FurinaHelper.getPlayer();
        if (target.hasPower(PneumaEmergy.POWER_ID)) {
            int min_amout = target.getPower(PneumaEmergy.POWER_ID).amount;
            if (this.amount < min_amout) {
                min_amout = this.amount;
            }
            AbstractDungeon.actionManager.addToBottom(
                    new ReducePowerAction(target, AbstractDungeon.player, PneumaEmergy.POWER_ID, min_amout)
            );
            AbstractDungeon.actionManager.addToBottom(
                    new ReducePowerAction(target, AbstractDungeon.player, OusiaEmergy.POWER_ID, min_amout)
            );
            if (min_amout <= 0) {
                return;
            }
            Furina.gainElementEnergy(min_amout);
            if (!FurinaHelper.hasPower(Dewdrop.POWER_ID)) {
                FurinaHelper.applyPower(player, player, new Dewdrop(player, 1), 1);
            } else if (!FurinaHelper.hasPower(Grit.POWER_ID)) {
                FurinaHelper.applyPower(player, player, new Grit(player, 1), 1);
            } else if (!FurinaHelper.hasPower(CriticalBoost.POWER_ID)) {
                FurinaHelper.applyPower(player, player, new CriticalBoost(player, 1), 1);
            } else {
                FurinaHelper.applyPower(player, player, new CriticalBoost(player, 1), 1);
            }
        }
    }
}
