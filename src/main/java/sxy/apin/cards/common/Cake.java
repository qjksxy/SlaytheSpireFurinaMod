package sxy.apin.cards.common;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sxy.apin.helper.FurinaHelper;
import sxy.apin.power.Revelry;

import static sxy.apin.character.Furina.Enums.FURINA_BLUE;

public class Cake extends CustomCard {
    public static final String ID = FurinaHelper.makeCardID(Cake.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "sxy/apin/img/cards/Strike.png";
    // -2费不显示能量图标（如诅咒卡状态卡等），-1费为X费（旋风斩等）。
    private static final int COST = 1;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = FURINA_BLUE;
    private static final CardRarity RARITY = CardRarity.COMMON;
    // 是否指向敌人
    private static final CardTarget TARGET = CardTarget.SELF;

    public Cake() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseHeal = 1;
        this.tags.add(CardTags.HEALING);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName(); // 卡牌名字变为绿色并添加“+”，且标为升级过的卡牌，之后不能再升级。
        }
        // 加上以下两行就能使用UPGRADE_DESCRIPTION了（如果你写了的话）
        this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        int extraHealth = 0;
        if (abstractPlayer.hasPower(Revelry.POWER_ID)) {
            AbstractPower revelry = abstractPlayer.getPower(Revelry.POWER_ID);
            if (revelry.amount > 0) {
                extraHealth += revelry.amount;
                revelry.flash();
            }
        }
//        this.addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer,
//                new UniversalRevelry(abstractPlayer, 1), 1));
        AbstractDungeon.actionManager.addToBottom(
                new HealAction(abstractPlayer, abstractPlayer, this.baseHeal + extraHealth)
        );
        if (abstractPlayer.hasPower(Revelry.POWER_ID)) {
            Revelry.consumeRevelry(abstractPlayer, 1);
        }

    }
}
