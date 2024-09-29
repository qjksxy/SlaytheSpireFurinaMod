package sxy.apin.cards.rare;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import sxy.apin.character.Furina;
import sxy.apin.helper.FurinaHelper;

import static sxy.apin.character.Furina.Enums.FURINA_BLUE;

/**
 * 瀚海亦舞台 对敌人造成当前气氛值 50% 的伤害，给予 2 层易伤和脆弱。
 */
public class TheSeaIsMyStage extends CustomCard {
    public static final String ID = FurinaHelper.makeCardID(TheSeaIsMyStage.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = "sxy/apin/img/cards/attack/card_raw_73.png";
    private static final int COST = 1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = FURINA_BLUE;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public TheSeaIsMyStage() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = 2;
        this.baseMagicNumber = 2;
        this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeMagicNumber(1);
            this.upgradeName();
        }
        this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        int damage = Furina.getRevelry();
        int extraDamage = HearMe.getExtraDamage();
        FurinaHelper.damage(abstractMonster, abstractPlayer, damage + extraDamage, DamageInfo.DamageType.NORMAL);
        FurinaHelper.addToBottom(new ApplyPowerAction(abstractMonster, abstractPlayer,
                new WeakPower(abstractMonster, this.magicNumber, false)));
        FurinaHelper.addToBottom(new ApplyPowerAction(abstractMonster, abstractPlayer,
                new VulnerablePower(abstractMonster, this.magicNumber, false)));
    }
}
