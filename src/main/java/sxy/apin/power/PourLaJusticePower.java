package sxy.apin.power;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sxy.apin.helper.FurinaHelper;

public class PourLaJusticePower extends AbstractPower {
    public static final String POWER_ID = FurinaHelper.makePowerID(PourLaJusticePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int healCount = 0;
    private int hurtCount = 0;
    public PourLaJusticePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.healCount = amount;
        this.hurtCount = amount;
        // 添加一大一小两张能力图
        String path128 = "sxy/apin/img/powers/Example84.png";
        String path48 = "sxy/apin/img/powers/Example32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        // 首次添加能力更新描述
        this.updateDescription();
    }

    // 能力在更新时如何修改描述
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

        this.healCount += stackAmount;
        this.hurtCount += stackAmount;
        if (this.amount >= 999) {
            this.amount = 999;
        }
        if (this.hurtCount > 999) {
            this.hurtCount = 999;
        }
        if (this.healCount > 999) {
            this.healCount = 999;
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (!isPlayer) {
            return;
        }
        for (int i = 0; i < hurtCount * 2; i++) {
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(AbstractDungeon.player,
                            new DamageInfo(AbstractDungeon.player, 1, DamageInfo.DamageType.NORMAL))
            );
        }
        this.hurtCount = 0;
    }

    @Override
    public void atStartOfTurn() {
        for (int i = 0; i < healCount * 3; i++) {
            AbstractDungeon.actionManager.addToBottom(
                    new HealAction(AbstractDungeon.player, AbstractDungeon.player, 2)
            );
        }
        this.healCount = 0;
        if (this.hurtCount == 0) {
            AbstractDungeon.actionManager.addToBottom(
                    new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, PourLaJusticePower.POWER_ID)
            );
        }
    }
}
