package sxy.apin.cards.basic;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sxy.apin.helper.FurinaHelper;
import sxy.apin.power.PneumaEmergy;

import static sxy.apin.character.Furina.Enums.FURINA_BLUE;

public class SpiritbreathThorn extends CustomCard {
    public static final String ID = FurinaHelper.makeCardID(SpiritbreathThorn.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = "sxy/apin/img/cards/Strike.png";
    // -2费不显示能量图标（如诅咒卡状态卡等），-1费为X费（旋风斩等）。
    private static final int COST = 0;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = FURINA_BLUE;
    private static final CardRarity RARITY = CardRarity.BASIC;
    // 是否指向敌人
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public SpiritbreathThorn() {
        // 为了命名规范修改了变量名。这些参数具体的作用见下方
        // CardType：有 ATTACK, SKILL, POWER, STATUS, CURSE 五种，分别代表攻击，技能，能力，状态，诅咒五种卡牌类型。
        // CardRarity：有 BASIC, SPECIAL, COMMON, UNCOMMON, RARE, CURSE 六种，分别代表不同的卡牌稀有度
        // CardTarget：有 ENEMY, ALL_ENEMY, SELF, NONE, SELF_AND_ENEMY, ALL，分别代表单个敌人，所有敌人，自身，无，自身和敌人，所有，六种卡牌目标。
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 1;
        this.tags.add(CardTags.STARTER_STRIKE);
        this.tags.add(CardTags.STRIKE);
        this.exhaust = true;
        this.isEthereal = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
            this.upgradeDamage(3); // 将该卡牌的伤害提高3点。
        }
        // 加上以下两行就能使用UPGRADE_DESCRIPTION了（如果你写了的话）
        this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(
                        abstractMonster,
                        new DamageInfo(
                                abstractPlayer,
                                damage,
                                DamageInfo.DamageType.NORMAL
                        )
                )
        );
        // 芒_Pneuma
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(abstractMonster, abstractPlayer,
                        new PneumaEmergy(abstractMonster), 1));
    }

}
