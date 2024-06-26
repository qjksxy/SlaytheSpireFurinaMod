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

public class SingerOfManyWaters extends AbstractPower {
    // 能力的ID
    public static final String POWER_ID = FurinaHelper.makePowerID(SingerOfManyWaters.class.getSimpleName());
    // 能力的本地化字段
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    // 能力的名称
    private static final String NAME = powerStrings.NAME;
    // 能力的描述
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public SingerOfManyWaters(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = amount;
        // 添加一大一小两张能力图
        String path128 = "sxy/apin/img/powers/Example84.png";
        String path48 = "sxy/apin/img/powers/Example32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
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
        AbstractPlayer player = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(
                new ReducePowerAction(player, player, SingerOfManyWaters.POWER_ID, 1)
        );
        AbstractDungeon.actionManager.addToBottom(
                new HealAction(player, player, 3)
        );
        if (AbstractDungeon.player.currentHealth > AbstractDungeon.player.maxHealth / 2) {
            return;
        }
        AbstractDungeon.actionManager.addToBottom(
                new HealAction(player, player, revelry / 5)
        );
        Furina.consumeRevelry(1);
    }
}
