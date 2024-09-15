package sxy.apin.cards.common;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sxy.apin.helper.FurinaHelper;

import static sxy.apin.character.Furina.Enums.FURINA_BLUE;

// TODO 检查此牌是否正常生效
public class Swirl extends CustomCard {
    public static final String ID = FurinaHelper.makeCardID(Swirl.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = "sxy/apin/img/cards/Strike.png";
    private static final int COST = 1;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = FURINA_BLUE;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public Swirl() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 5;
        this.isMultiDamage = true;
        this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
        }
        this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        DamageAllEnemiesAction action = new DamageAllEnemiesAction(abstractPlayer, this.multiDamage,
                this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE);
        FurinaHelper.addToBottom(action);
    }
}
