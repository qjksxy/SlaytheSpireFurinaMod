package sxy.apin.cards.rare;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import org.apache.logging.log4j.Level;
import sxy.apin.helper.FurinaHelper;
import sxy.apin.modcore.FurinaCore;

import static sxy.apin.character.Furina.Enums.FURINA_BLUE;

/**
 * 戏中人  获得1层无实体和 !M! 层人工制品。此牌每打出1次，耗能增加1。
 */
public class MemberOfTheCast extends CustomCard {
    public static final String ID = FurinaHelper.makeCardID(MemberOfTheCast.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = "sxy/apin/img/cards/skill/card_raw_47.png";
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = FURINA_BLUE;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public MemberOfTheCast() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = 1;
        this.baseMagicNumber = 1;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
        this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        FurinaHelper.applyPower(abstractPlayer, abstractPlayer, new IntangiblePlayerPower(abstractPlayer, 1), 1);
        FurinaHelper.applyPower(abstractPlayer, abstractPlayer, new ArtifactPower(abstractPlayer, this.magicNumber), this.magicNumber);
//        this.setCostForTurn(this.costForTurn + 1);

//        this.upgradedCost = true;
//        this.modifyCostForCombat(1);
    }

    @Override
    public void triggerOnCardPlayed(AbstractCard c) {
        super.triggerOnCardPlayed(c);
        if (c == this) {
            this.modifyCostForCombat(1);
            FurinaCore.logger.log(Level.WARN, "costForTurn:" + this.costForTurn);
        }

    }


}
