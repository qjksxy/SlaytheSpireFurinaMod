package sxy.apin.cards.uncommon;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sxy.apin.character.Furina;
import sxy.apin.helper.FurinaHelper;

import static sxy.apin.character.Furina.Enums.FURINA_BLUE;

/**
 * 愈疗之水 恢复 !M! 生命，获得 2 气氛值，获得 2 元素能量。
 */
public class SoothingWater extends CustomCard {
    public static final String ID = FurinaHelper.makeCardID(SoothingWater.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = "sxy/apin/img/cards/skill/card_raw_94.png";
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = FURINA_BLUE;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public SoothingWater() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        // CardType：有 ATTACK, SKILL, POWER, STATUS, CURSE 五种，分别代表攻击，技能，能力，状态，诅咒五种卡牌类型。
        // CardRarity：有 BASIC, SPECIAL, COMMON, UNCOMMON, RARE, CURSE 六种，分别代表不同的卡牌稀有度
        // CardTarget：有 ENEMY, ALL_ENEMY, SELF, NONE, SELF_AND_ENEMY, ALL，分别代表单个敌人，所有敌人，自身，无，自身和敌人，所有，六种卡牌目标。
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 4;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(6);
        }
        this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        FurinaHelper.addToBottom(new HealAction(abstractPlayer, abstractPlayer, this.magicNumber));
        Furina.gainRevelry(4);
        Furina.gainElementEnergy(4);
    }
}
