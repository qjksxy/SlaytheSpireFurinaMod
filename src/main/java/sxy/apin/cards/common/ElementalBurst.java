package sxy.apin.cards.common;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sxy.apin.character.Furina;
import sxy.apin.helper.FurinaHelper;
import sxy.apin.power.AWomanAdaptsPower;
import sxy.apin.power.ElementEnergy;
import sxy.apin.power.UniversalRevelry;

import static sxy.apin.character.Furina.Enums.FURINA_BLUE;

public class ElementalBurst extends CustomCard {
    public static final String ID = FurinaHelper.makeCardID(ElementalBurst.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = "sxy/apin/img/cards/skill/card_raw_1.png";
    private static final int COST = 1;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = FURINA_BLUE;
    private static final CardRarity RARITY = CardRarity.COMMON;
    // 是否指向敌人
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public ElementalBurst() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = 3;
        this.magicNumber = 3;
        this.baseDamage = 12;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(-1);
        }
        // 加上以下两行就能使用 UPGRADE_DESCRIPTION 了（如果你写了的话）
        this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        this.cantUseMessage = "角色元素能量不足，无法打出";
        if (!canUse) {
            return false;
        } else {
            if (!p.hasPower(ElementEnergy.POWER_ID)) {
                return false;
            }
            return p.getPower(ElementEnergy.POWER_ID).amount >= this.magicNumber;
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        int amount = 0;
        int ee_consume = 0;
        amount = Math.min(3, abstractPlayer.getPower(ElementEnergy.POWER_ID).amount / this.magicNumber);
        ee_consume = amount * this.magicNumber;
        AbstractDungeon.actionManager.addToBottom(
                new ReducePowerAction(abstractPlayer, abstractPlayer, ElementEnergy.POWER_ID, ee_consume)
        );
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(abstractPlayer, abstractPlayer,
                        new UniversalRevelry(abstractPlayer, amount), amount)
        );
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(abstractMonster,
                        new DamageInfo(abstractPlayer, damage, DamageInfo.DamageType.NORMAL))
        );
        if (FurinaHelper.hasPower(AWomanAdaptsPower.POWER_ID)) {
            Furina.gainRevelry(15);
        }
    }
}
