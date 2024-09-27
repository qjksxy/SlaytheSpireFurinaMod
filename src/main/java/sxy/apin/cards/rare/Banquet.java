package sxy.apin.cards.rare;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sxy.apin.character.Furina;
import sxy.apin.helper.FurinaHelper;

import static sxy.apin.character.Furina.Enums.FURINA_BLUE;

/**
 * 欢宴  气氛值提升至最大值。消耗。
 */
public class Banquet extends CustomCard {
    public static final String ID = FurinaHelper.makeCardID(Banquet.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = "sxy/apin/img/cards/skill/card_raw_67.png";
    private static final int COST = 3;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = FURINA_BLUE;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Banquet() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.isInnate = true;
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
        Furina.gainRevelry(50);
    }
}
