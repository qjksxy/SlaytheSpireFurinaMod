package sxy.apin.power;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sxy.apin.helper.FurinaHelper;

/**
 * 伊黎耶岛 每回合开始时，恢复生命值。
 */
public class ErinnyesPower extends AbstractPower {
    public static final String POWER_ID = FurinaHelper.makePowerID(ErinnyesPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int mod = 0;
    public ErinnyesPower(AbstractCreature owner, int mod) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = -1;
        this.mod = mod;
        String path128 = "sxy/apin/img/powers/Example84.png";
        String path48 = "sxy/apin/img/powers/Example32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        // 首次添加能力更新描述
        this.updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
    @Override
    public void stackPower(int stackAmount) {
        return;
    }

    @Override
    public void atStartOfTurn() {
        int heal = 0;
        AbstractPlayer player = AbstractDungeon.player;
        heal = player.maxHealth - player.currentHealth;
        heal = heal / 10;
        if (mod == 1) {
            heal = player.maxHealth / 10;
        }
        AbstractDungeon.actionManager.addToBottom(
                new HealAction(AbstractDungeon.player, AbstractDungeon.player, heal)
        );
    }


}
