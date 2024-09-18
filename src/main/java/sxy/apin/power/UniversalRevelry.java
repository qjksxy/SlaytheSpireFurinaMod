package sxy.apin.power;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
 * 万众狂欢：每受到一次伤害或者治疗时获得一层气氛值
 */
public class UniversalRevelry extends AbstractPower {
    // 能力的ID
    public static final String POWER_ID = FurinaHelper.makePowerID(UniversalRevelry.class.getSimpleName());
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public UniversalRevelry(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;

        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行
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

        if (this.amount <= -999) {
            this.amount = -999;
        }

    }

    // 能力在更新时如何修改描述
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    // 被攻击时
    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        // 非生命流失伤害，伤害来源不为空，伤害大于 0
        if (this.owner.isPlayer &&
                info.type != DamageInfo.DamageType.HP_LOSS &&
                info.owner != null &&
                damageAmount > 0) {
            // 能力闪烁一下
            this.flash();
            AbstractPlayer player = AbstractDungeon.player;
            // 给玩家添加一层气氛值
            this.addToTop(new ApplyPowerAction(player, player,
                    new Revelry(player, 1), 1));
        }
        // 如果该能力不会修改受到伤害的数值，按原样返回即可
        return damageAmount;
    }

    @Override
    public int onHeal(int healAmount) {
        if (this.owner.isPlayer && this.owner.currentHealth < this.owner.maxHealth && healAmount > 0) {
            // 能力闪烁一下
            this.flash();
            AbstractPlayer player = AbstractDungeon.player;
            // 给玩家添加一层气氛值
            this.addToTop(new ApplyPowerAction(player, player,
                    new Revelry(player, 1), 1));
        }

        return healAmount;
    }

    @Override
    public void atEndOfRound() {
        if (this.amount == 0) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        } else {
            this.addToBot(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }
    }
}
