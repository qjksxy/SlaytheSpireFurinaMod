package sxy.apin.power;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sxy.apin.character.Furina;
import sxy.apin.helper.FurinaHelper;

/**
 * 沙龙成员 回合结束时，对最近敌人造成 10 点伤害。若生命值高于50%，则额外造成 furina:气氛值 / 2 的伤害，并对自身造成 1 点伤害。
 */
public class SalonMembers extends AbstractPower {
    // 能力的ID
    public static final String POWER_ID = FurinaHelper.makePowerID(SalonMembers.class.getSimpleName());
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public SalonMembers(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = amount;
        // 添加一大一小两张能力图
        String path128 = "sxy/apin/img/powers/power_128/power_raw_73.png";
        String path48 = "sxy/apin/img/powers/power_48/power_raw_73.png";
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
        double factor = INowKnowItIsPower.getINowKnowItIsPowerFactor();
        AbstractMonster mon = FurinaHelper.getNearestMonster();
        if (mon != null) {
            AbstractDungeon.actionManager.addToBottom(
                    new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, SalonMembers.POWER_ID, 1)
            );
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(mon,
                            new DamageInfo(AbstractDungeon.player, (int) (10 * factor), DamageInfo.DamageType.NORMAL))
            );
        }
        if (AbstractDungeon.player.currentHealth <= AbstractDungeon.player.maxHealth / 2) {
            return;
        }
        if (revelry < 1) {
            return;
        }
        mon = FurinaHelper.getRandomMonster();
        if (mon == null) {
            return;
        }
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(mon,
                        new DamageInfo(AbstractDungeon.player,
                                (int) (revelry * factor),
                                DamageInfo.DamageType.NORMAL))
        );
    }
}
