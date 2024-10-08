package sxy.apin.power;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sxy.apin.helper.FurinaHelper;

/**
 * 珠露 每受到1点治疗累计1层珠露。回合开始时，若珠露层数大于生命上限的10%，珠露破碎，对敌方全体造成珠露层数的伤害。
 */
public class Dewdrop extends AbstractPower {
    // 能力的ID
    public static final String POWER_ID = FurinaHelper.makePowerID(Dewdrop.class.getSimpleName());
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public Dewdrop(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = Math.min(amount, getLimit());
        // 添加一大一小两张能力图
        String path128 = "sxy/apin/img/powers/power_128/power_raw_87.png";
        String path48 = "sxy/apin/img/powers/power_48/power_raw_87.png";
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
        this.amount = Math.min(this.amount, getLimit());
    }

    @Override
    public int onHeal(int healAmount) {
        this.amount += healAmount;
        this.amount = Math.min(this.amount, getLimit());
        return healAmount;
    }

    public int getLimit() {
        return (int) (FurinaHelper.getPlayer().maxHealth * 0.25);
    }
    @Override
    public void atStartOfTurn() {
        AbstractPlayer player = FurinaHelper.getPlayer();
        if (this.amount > player.maxHealth / 10) {
            effect(player);
            FurinaHelper.removePlayerPower(this.ID);
        }
    }

    public void effect(AbstractPlayer player) {
        this.flash();
        FurinaHelper.addToBottom(new DamageAllEnemiesAction(player, this.amount,
                DamageInfo.DamageType.NORMAL,
                AbstractGameAction.AttackEffect.NONE));
    }
}
