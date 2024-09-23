package sxy.apin.cards.common;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sxy.apin.helper.FurinaHelper;
import sxy.apin.power.BloomPower;

import static sxy.apin.character.Furina.Enums.FURINA_BLUE;

/**
 * 冻结 对敌人造成伤害。本回合不消耗气氛值。
 */
public class Bloom extends CustomCard {
    public static final String ID = FurinaHelper.makeCardID(Bloom.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = "sxy/apin/img/cards/attack/card_raw_44.png";
    private static final int COST = 2;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = FURINA_BLUE;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Bloom() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 6;
        this.magicNumber = 6;
        this.baseMagicNumber = 6;
        this.tags.add(CardTags.STRIKE);
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }

    @Override
    public void upgrade() {
        this.upgraded = true;
        ++this.timesUpgraded;
        this.name = NAME + "+" + this.timesUpgraded;
        this.initializeTitle();
        this.upgradeMagicNumber(3);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        FurinaHelper.damage(abstractMonster, abstractPlayer, this.damage, DamageInfo.DamageType.NORMAL);
        BloomPower power = (BloomPower) FurinaHelper.getPower(BloomPower.POWER_ID);
        if (power == null ) {
            FurinaHelper.applyPower(abstractPlayer, abstractPlayer,
                    new BloomPower(abstractPlayer, 1, this.magicNumber), 1);
        } else {
            if (power.damageValue > this.magicNumber) {
                FurinaHelper.applyPower(abstractPlayer, abstractPlayer,
                        new BloomPower(abstractPlayer, 1, this.magicNumber), 1);
            } else {
                FurinaHelper.removePlayerPower(BloomPower.POWER_ID);
                FurinaHelper.applyPower(abstractPlayer, abstractPlayer,
                        new BloomPower(abstractPlayer, 1, this.magicNumber), 1);
            }
        }

    }
}
