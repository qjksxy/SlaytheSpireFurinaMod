package sxy.apin.power;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sxy.apin.helper.FurinaHelper;

/**
 * 气氛值  提升指定类型伤害和治疗效果
 */
public class Revelry extends AbstractPower {
    // 能力的ID
    public static final String POWER_ID = FurinaHelper.makePowerID(Revelry.class.getSimpleName());
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public Revelry(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = Amount;
        // 添加一大一小两张能力图
        String path128 = "sxy/apin/img/powers/Example84.png";
        String path48 = "sxy/apin/img/powers/Example32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        // 首次添加能力更新描述
        this.updateDescription();
    }

    public static void consumeRevelry(AbstractPlayer abstractPlayer, int amount) {
        if (!abstractPlayer.hasPower(Revelry.POWER_ID)) {
            return;
        }
        AbstractPower revelry = abstractPlayer.getPower(Revelry.POWER_ID);
        revelry.flash();
        AbstractDungeon.actionManager.addToBottom(
                new ReducePowerAction(abstractPlayer, abstractPlayer, Revelry.POWER_ID, amount)
        );
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        AbstractPlayer player = FurinaHelper.getPlayer();
        if (this.amount == 0) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
        if (player.hasPower(LoveIsARebelliousBirdPower.POWER_ID)) {
            if (this.amount > 50) {
                this.amount = 50;
            }
        } else if (this.amount >= 30) {
            this.amount = 30;
        }

        if (this.amount <= 0) {
            this.amount = 0;
        }

        // 众水：获得1层
        AllWatersPower watersPower = (AllWatersPower) FurinaHelper.getPower(AllWatersPower.POWER_ID);
        if (watersPower != null) {
            watersPower.stackPower(1);
        }
        // 众方：获得1层，累计 3 层抽牌
        AllKindredsPower kindredsPower = (AllKindredsPower) FurinaHelper.getPower(AllKindredsPower.POWER_ID);
        if (kindredsPower != null) {
            kindredsPower.stackPower(1);
            if (kindredsPower.getCount() >= 3) {
                kindredsPower.stackPower(-3);
            }
            kindredsPower.flash();
            FurinaHelper.addToBottom(new DrawCardAction(1));
        }
        // 众民：获得1层
        AbstractPower peoplePower = FurinaHelper.getPower(AllPeoplePower.POWER_ID);
        if (peoplePower != null) {
            FurinaHelper.addToBottom(new DamageRandomEnemyAction(
                    new DamageInfo(player, 5, DamageInfo.DamageType.NORMAL),
                    AbstractGameAction.AttackEffect.NONE));
        }
        // 众律法：获得1层
        AbstractPower lawsPower = FurinaHelper.getPower(AllLawsPower.POWER_ID);
        if (lawsPower != null) {
            double random = FurinaHelper.getRandomFloat();
            if (random < 0.34) {
                FurinaHelper.applyPower(player, player, new Grit(player, 1), 1);
            } else if (random < 0.67) {
                FurinaHelper.applyPower(player, player, new CriticalBoost(player, 1), 1);
            } else {
                FurinaHelper.applyPower(player, player, new Dewdrop(player, 1), 1);
            }
        }
    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
