package sxy.apin.power;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
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
 * 众水的歌者 回合结束时恢复 3 点生命，若生命值低于 50%，则额外恢复 furina:气氛值 / 5 的生命。
 */
public class SingerOfManyWatersPower extends AbstractPower {
    // 能力的ID
    public static final String POWER_ID = FurinaHelper.makePowerID(SingerOfManyWatersPower.class.getSimpleName());
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public SingerOfManyWatersPower(AbstractCreature owner, int amount) {
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
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount == 0) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }

        if (this.amount >= 999) {
            this.amount = 999;
        }

        if (this.amount <= 0) {
            this.amount = 0;
        }

    }
    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        int revelry = Furina.getRevelry();
        INowKnowItIsPower power = (INowKnowItIsPower) FurinaHelper.getPower(INowKnowItIsPower.POWER_ID);
        double factor = 1.0;
        if (power != null) {
            if (power.isUpgraded()) {
                factor = 1.5;
            } else {
                factor = 1.7;
            }
        }
        AbstractPlayer player = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(
                new ReducePowerAction(player, player, SingerOfManyWatersPower.POWER_ID, 1)
        );
        AbstractDungeon.actionManager.addToBottom(
                new HealAction(player, player, (int) (5 * factor))
        );
        if (AbstractDungeon.player.currentHealth > AbstractDungeon.player.maxHealth / 2) {
            return;
        }
        AbstractDungeon.actionManager.addToBottom(
                new HealAction(player, player, (int) (revelry * factor / 2))
        );
        Furina.consumeRevelry(1);
    }
}
