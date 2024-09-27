package sxy.apin.cards.uncommon;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sxy.apin.character.Furina;
import sxy.apin.helper.FurinaHelper;
import sxy.apin.power.CriticalBoost;
import sxy.apin.power.Dewdrop;
import sxy.apin.power.Grit;

import static sxy.apin.character.Furina.Enums.FURINA_BLUE;

/**
 * 交织之水 获得 !M! 层 furina_mod:战意 。依据当前的始基力形态，额外获得 !M! 层 furina_mod:会心 或 furina_mod:珠露 。
 */
public class WovenWaters extends CustomCard {
    public static final String ID = FurinaHelper.makeCardID(WovenWaters.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = "sxy/apin/img/cards/skill/card_raw_19.png";
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = FURINA_BLUE;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public WovenWaters() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = 6;
        this.baseMagicNumber = 6;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
        }
        this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(abstractPlayer, abstractPlayer,
                        new Grit(abstractPlayer, this.magicNumber), this.magicNumber)
        );
        int arkhe = Furina.getArkhe();
        if (arkhe == Furina.OUSIA) {
            FurinaHelper.applyPower(abstractPlayer, abstractPlayer, new CriticalBoost(abstractPlayer, this.magicNumber), this.magicNumber);
        } else {
            FurinaHelper.applyPower(abstractPlayer, abstractPlayer, new Dewdrop(abstractPlayer, this.magicNumber), this.magicNumber);
        }
    }
}
