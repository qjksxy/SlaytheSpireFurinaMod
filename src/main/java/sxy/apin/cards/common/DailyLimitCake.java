package sxy.apin.cards.common;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sxy.apin.character.Furina;
import sxy.apin.helper.FurinaHelper;
import sxy.apin.power.Revelry;

import static sxy.apin.character.Furina.Enums.FURINA_BLUE;

public class DailyLimitCake extends CustomCard {
    public static final String ID = FurinaHelper.makeCardID(DailyLimitCake.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = "sxy/apin/img/cards/Strike.png";
    // -2费不显示能量图标（如诅咒卡状态卡等），-1费为X费（旋风斩等）。
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = FURINA_BLUE;
    private static final CardRarity RARITY = CardRarity.COMMON;
    // 是否指向敌人
    private static final CardTarget TARGET = CardTarget.SELF;
    private int count = 2;

    public DailyLimitCake() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseHeal = 1;
        this.tags.add(CardTags.HEALING);
        this.exhaust = true;
        this.isEthereal = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.count = 3;
        }
        this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        Furina.gainElementEnergy(1);
        int extraHealth = 0;
        if (abstractPlayer.hasPower(Revelry.POWER_ID)) {
            AbstractPower revelry = abstractPlayer.getPower(Revelry.POWER_ID);
            if (revelry.amount > 0) {
                extraHealth += revelry.amount;
                revelry.flash();
            }
        }
        extraHealth = extraHealth / 2;
        for (int i = 0; i < this.count; i++) {
            AbstractDungeon.actionManager.addToBottom(
                    new HealAction(abstractPlayer, abstractPlayer, extraHealth)
            );
        }
        if (this.upgraded) {
            return;
        }
        Furina.consumeRevelry(1);
    }
}
