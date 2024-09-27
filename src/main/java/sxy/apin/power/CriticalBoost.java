package sxy.apin.power;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sxy.apin.character.Furina;
import sxy.apin.helper.FurinaHelper;

/**
 * 会心 每打出一张攻击牌获得1层会心，受到一次伤害消除2层会心。回合结束时对最近敌人造成会心层数的伤害。
 */
public class CriticalBoost extends AbstractPower {
    // 能力的ID
    public static final String POWER_ID = FurinaHelper.makePowerID(CriticalBoost.class.getSimpleName());
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public CriticalBoost(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = Math.min(amount, getLimit());
        // 添加一大一小两张能力图
        String path128 = "sxy/apin/img/powers/power_128/power_raw_89.png";
        String path48 = "sxy/apin/img/powers/power_48/power_raw_89.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 22, 22, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 8, 8, 32, 32);
        // 首次添加能力更新描述
        this.updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public int getLimit() {
        return 30;
    }

    @Override
    public void stackPower(int stackAmount) {
        if (this.amount == -1) {
            return;
        }
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        this.amount = Math.min(this.amount, getLimit());
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        this.amount += 2;
        this.amount = Math.min(this.amount, getLimit());
        this.flash();
    }

    @Override
    public void wasHPLost(DamageInfo info, int damageAmount) {
        this.amount -= 3;
        this.flash();
        if (this.amount <= 0) {
            FurinaHelper.removePlayerPower(this.ID);
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            effect();
        }
    }

    public void effect() {
        AbstractMonster mon = FurinaHelper.getNearestMonster();
        if (mon == null) {
            return;
        }
        this.flash();
        // 永世领唱 每持有 10 点气氛值，会心造成的伤害提升50%。
        double fac = 1.0;
        if (FurinaHelper.getPower(PerpetualMuseOfChansonsPower.POWER_ID) != null) {
            int revelry = Furina.getRevelry();
            //noinspection RedundantCast
            fac += (int) (revelry / 10) * 0.5;
        }
        FurinaHelper.damage(mon, FurinaHelper.getPlayer(), (int) (this.amount * fac), DamageInfo.DamageType.NORMAL);
    }
}
