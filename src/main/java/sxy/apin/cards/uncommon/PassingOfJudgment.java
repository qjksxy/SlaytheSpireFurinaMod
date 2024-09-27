package sxy.apin.cards.uncommon;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sxy.apin.helper.FurinaHelper;
import sxy.apin.power.CriticalBoost;
import sxy.apin.power.Dewdrop;
import sxy.apin.power.Grit;

import static sxy.apin.character.Furina.Enums.FURINA_BLUE;

/**
 * 裁定之时 立即结算一次 会心/战意/珠露；（本次结算不消耗珠露层数）。
 */
public class PassingOfJudgment extends CustomCard {
    public static final String ID = FurinaHelper.makeCardID(PassingOfJudgment.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = "sxy/apin/img/cards/skill/card_raw_115.png";
    private static final int COST = 2;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = FURINA_BLUE;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public PassingOfJudgment() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        // CardType：有 ATTACK, SKILL, POWER, STATUS, CURSE 五种，分别代表攻击，技能，能力，状态，诅咒五种卡牌类型。
        // CardRarity：有 BASIC, SPECIAL, COMMON, UNCOMMON, RARE, CURSE 六种，分别代表不同的卡牌稀有度
        // CardTarget：有 ENEMY, ALL_ENEMY, SELF, NONE, SELF_AND_ENEMY, ALL，分别代表单个敌人，所有敌人，自身，无，自身和敌人，所有，六种卡牌目标。
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
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
        FurinaHelper.applyPower(abstractPlayer, abstractPlayer, new Grit(abstractPlayer, 1), 1);
        FurinaHelper.applyPower(abstractPlayer, abstractPlayer, new CriticalBoost(abstractPlayer, 1), 1);
        FurinaHelper.applyPower(abstractPlayer, abstractPlayer, new Dewdrop(abstractPlayer, 1), 1);
        AbstractPower grit = FurinaHelper.getPower(Grit.POWER_ID);
        if (grit != null) {
            FurinaHelper.addToBottom(new GainBlockAction(abstractPlayer, grit.amount));
        }
        CriticalBoost criticalBoost = (CriticalBoost) FurinaHelper.getPower(CriticalBoost.POWER_ID);
        if (criticalBoost != null) {
            criticalBoost.effect();
        }
        Dewdrop dewdrop = (Dewdrop) FurinaHelper.getPower(Dewdrop.POWER_ID);
        if (dewdrop != null) {
            dewdrop.effect(abstractPlayer);
            if (!this.upgraded) {
                FurinaHelper.removePlayerPower(dewdrop.ID);
            }
        }
    }
}
