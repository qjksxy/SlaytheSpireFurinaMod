package sxy.apin.cards.rare;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sxy.apin.helper.FurinaHelper;
import sxy.apin.power.AWomanAdaptsPower;

import static sxy.apin.character.Furina.Enums.FURINA_BLUE;

/**
 * 女人皆善变  命之座2  打出万众狂欢时，获得 10 点气氛值。获得气氛值时 40% 概率额外获得 1 点。
 */
public class AWomanAdapts extends CustomCard {
    public static final String ID = FurinaHelper.makeCardID(AWomanAdapts.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = "sxy/apin/img/cards/power/card_raw_32.png";
    private static final int COST = 1;
    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = FURINA_BLUE;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public AWomanAdapts() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.updateCost(-1);
        }
        this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(abstractPlayer, abstractPlayer,
                        new AWomanAdaptsPower(abstractPlayer, 1), 0)
        );
    }
}
