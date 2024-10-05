package sxy.apin.cards.common;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sxy.apin.character.Furina;
import sxy.apin.helper.FurinaHelper;
import sxy.apin.power.*;

import static sxy.apin.character.Furina.Enums.FURINA_BLUE;

public class ElementalSkill extends CustomCard {
    public static final String ID = FurinaHelper.makeCardID(ElementalSkill.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID); // 从游戏系统读取本地化资源
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = "sxy/apin/img/cards/skill/card_raw_27.png";
    private static final int COST = 2;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = FURINA_BLUE;
    private static final CardRarity RARITY = CardRarity.COMMON;
    // 是否指向敌人
    private static final CardTarget TARGET = CardTarget.SELF;

    public ElementalSkill() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = 2;
        this.magicNumber = 2;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
        // 加上以下两行就能使用 UPGRADE_DESCRIPTION 了（如果你写了的话）
        this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        int arkhe = Furina.getArkhe();
        if (arkhe == Furina.ERROR) {
            return;
        }
        if (arkhe == Furina.OUSIA) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(abstractPlayer, abstractPlayer,
                            new SalonMembers(abstractPlayer, magicNumber), magicNumber)
            );
        } else {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(abstractPlayer, abstractPlayer,
                            new SingerOfManyWatersPower(abstractPlayer, magicNumber), magicNumber)
            );
        }
        Furina.gainElementEnergy(magicNumber + 1);

        // 6命效果  打出此牌时获得 CenterOfAttentionPower
        HearMePower power = (HearMePower) FurinaHelper.getPower(HearMePower.POWER_ID);
        if (power != null) {
            CenterOfAttentionPower centerOfAttentionPower =
                    (CenterOfAttentionPower) FurinaHelper.getPower(CenterOfAttentionPower.POWER_ID);
            if (centerOfAttentionPower != null) {
                if (!centerOfAttentionPower.isUpgraded() && power.isUpgraded()) {
                    centerOfAttentionPower.upgrade();
                }
            } else {
                FurinaHelper.applyPower(abstractPlayer, abstractPlayer,
                        new CenterOfAttentionPower(abstractPlayer, power.isUpgraded()), 0);
            }

        }
    }
}
