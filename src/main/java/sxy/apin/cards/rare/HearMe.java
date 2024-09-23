package sxy.apin.cards.rare;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sxy.apin.helper.FurinaHelper;
import sxy.apin.power.CenterOfAttentionPower;
import sxy.apin.power.HearMePower;

import static sxy.apin.character.Furina.Enums.FURINA_BLUE;

/**
 * 诸君听我颂  命之座6  释放孤心沙龙的回合内，普通攻击（含重击）造成的伤害提升。提升值为生命上限的30%，并额外获得4层气氛值。
 */
public class HearMe extends CustomCard {
    public static final String ID = FurinaHelper.makeCardID(HearMe.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = "sxy/apin/img/cards/power/card_raw_35.png";
    private static final int COST = 2;
    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = FURINA_BLUE;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public HearMe() {
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
        if (FurinaHelper.hasPower(HearMePower.POWER_ID)) {
            if (!this.upgraded) {
                return;
            } else {
                HearMePower power = (HearMePower) FurinaHelper.getPower(HearMePower.POWER_ID);
                assert power != null;
                power.upgrade();
            }
        }
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(abstractPlayer, abstractPlayer,
                        new HearMePower(abstractPlayer, 1, upgraded), 0)
        );
    }

    public static int getExtraDamage() {
        int extraDamage = 0;
        int hp = FurinaHelper.getPlayer().maxHealth;
        CenterOfAttentionPower power = (CenterOfAttentionPower) FurinaHelper.getPower(CenterOfAttentionPower.POWER_ID);
        if (power == null) {
            return extraDamage;
        }
        if (power.isUpgraded()) {
            extraDamage = (int) (hp * 0.3);
        } else {
            extraDamage = (int) (hp * 0.25);
        }
        return extraDamage;
    }
}
