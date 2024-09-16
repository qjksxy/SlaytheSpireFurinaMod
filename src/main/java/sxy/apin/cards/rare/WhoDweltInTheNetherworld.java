package sxy.apin.cards.rare;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sxy.apin.helper.FurinaHelper;
import sxy.apin.power.WhoDweltInTheNetherworldPower;

import static sxy.apin.character.Furina.Enums.FURINA_BLUE;

/**
 * 若非处幽冥  命之座4  孤心沙龙提供的元素能量+1/2。
 */
public class WhoDweltInTheNetherworld extends CustomCard {
    public static final String ID = FurinaHelper.makeCardID(WhoDweltInTheNetherworld.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = "sxy/apin/img/cards/Strike.png";
    private static final int COST = 0;
    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = FURINA_BLUE;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public WhoDweltInTheNetherworld() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
        }
        this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if (FurinaHelper.hasPower(WhoDweltInTheNetherworldPower.POWER_ID)) {
            if (!this.upgraded) {
                return;
            } else {
                WhoDweltInTheNetherworldPower power = (WhoDweltInTheNetherworldPower) FurinaHelper.getPower(WhoDweltInTheNetherworldPower.POWER_ID);
                assert power != null;
                power.upgrade();
            }
        }
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(abstractPlayer, abstractPlayer,
                        new WhoDweltInTheNetherworldPower(abstractPlayer, 1, upgraded), 0)
        );
    }
}
