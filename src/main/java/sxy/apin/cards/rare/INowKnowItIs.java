package sxy.apin.cards.rare;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sxy.apin.helper.FurinaHelper;
import sxy.apin.power.INowKnowItIsPower;

import static sxy.apin.character.Furina.Enums.FURINA_BLUE;

/**
 * 我已有察觉  命之座5  万众狂欢生效期间，气氛值层数视为 140%/180%。
 */
public class INowKnowItIs extends CustomCard {
    public static final String ID = FurinaHelper.makeCardID(INowKnowItIs.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = "sxy/apin/img/cards/power/card_raw_25.png";
    private static final int COST = 1;
    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = FURINA_BLUE;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public INowKnowItIs() {
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
        if (FurinaHelper.hasPower(INowKnowItIsPower.POWER_ID)) {
            if (!this.upgraded) {
                return;
            } else {
                INowKnowItIsPower power = (INowKnowItIsPower) FurinaHelper.getPower(INowKnowItIsPower.POWER_ID);
                assert power != null;
                power.upgrade();
            }
        }
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(abstractPlayer, abstractPlayer,
                        new INowKnowItIsPower(abstractPlayer, upgraded), 0)
        );
    }
}
