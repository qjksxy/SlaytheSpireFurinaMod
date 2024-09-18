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
        this.amount = amount;
        // 添加一大一小两张能力图
        String path128 = "sxy/apin/img/powers/Example84.png";
        String path48 = "sxy/apin/img/powers/Example32.png";
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
        if (this.amount >= 999) {
            this.amount = 999;
        }
    }

    @Override
    public int onHeal(int healAmount) {
        this.amount += healAmount;
        return healAmount;
    }

    @Override
    public void atStartOfTurn() {
        AbstractPlayer player = FurinaHelper.getPlayer();
        if (this.amount > player.maxHealth / 10) {
            this.flash();
            FurinaHelper.addToBottom(new DamageAllEnemiesAction(player, this.amount,
                    DamageInfo.DamageType.NORMAL,
                    AbstractGameAction.AttackEffect.NONE));
            FurinaHelper.removePlayerPower(this.ID);
        }
    }
}
