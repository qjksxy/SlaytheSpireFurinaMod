package sxy.apin.cards.rare;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sxy.apin.character.Furina;
import sxy.apin.helper.FurinaHelper;

import static sxy.apin.character.Furina.Enums.FURINA_BLUE;

/**
 * 静水流涌之辉 对敌人造成当前气氛值的伤害。
 */
public class SplendorOfTranquilWaters extends CustomCard {
    public static final String ID = FurinaHelper.makeCardID(SplendorOfTranquilWaters.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = "sxy/apin/img/cards/attack/card_raw_71.png";
    private static final int COST = -1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = FURINA_BLUE;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public SplendorOfTranquilWaters() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(CardTags.STRIKE);
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
        FurinaHelper.addToBottom(new LoseEnergyAction(this.energyOnUse));
        int damage = Furina.getRevelry();
        int energy = this.energyOnUse;
        // TODO 如果玩家拥有遗物 化学物X 则此处+2
        if (abstractPlayer.hasRelic("Chemical X")) {
            energy += 2;
        }
        int extraDamage = HearMe.getExtraDamage();
        if (this.upgraded) {
            energy += 1;
        }
        for (int i = 0; i < energy; i++) {
            FurinaHelper.damage(abstractMonster, abstractPlayer, damage, DamageInfo.DamageType.NORMAL);
        }
        if (extraDamage > 0) {
            FurinaHelper.damage(abstractMonster, abstractPlayer, extraDamage, DamageInfo.DamageType.NORMAL);
        }
        if (Furina.getArkhe() == Furina.OUSIA) {
            FurinaHelper.addToBottom(new HealAction(abstractPlayer, abstractPlayer, damage));
        } else {
            FurinaHelper.damage(abstractMonster, abstractPlayer, damage, DamageInfo.DamageType.NORMAL);
        }
    }
}
