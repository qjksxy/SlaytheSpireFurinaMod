package sxy.apin.cards.uncommon;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sxy.apin.helper.FurinaHelper;

import static sxy.apin.character.Furina.Enums.FURINA_BLUE;

/**
 * 执律庭 场上每有1名敌人，你摸 !M! 张牌 。
 */
public class MaisonGardiennage extends CustomCard {
    public static final String ID = FurinaHelper.makeCardID(MaisonGardiennage.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = "sxy/apin/img/cards/skill/card_raw_33.png";
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = FURINA_BLUE;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public MaisonGardiennage() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
        this.tags.add(CardTags.STRIKE);
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
        int monNum = 0;
        for (AbstractMonster mon : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mon.isDeadOrEscaped()) {
                monNum++;
            }
        }
        AbstractDungeon.actionManager.addToBottom(
                new DrawCardAction(abstractPlayer, monNum * this.magicNumber)
        );
    }
}
