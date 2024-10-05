package sxy.apin.cards.basic;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sxy.apin.helper.FurinaHelper;
import sxy.apin.power.ElectroChargedPower;

import java.util.ArrayList;

import static sxy.apin.character.Furina.Enums.FURINA_BLUE;

/**
 * 感电  对敌人造成伤害，附属感电
 */
public class ElectroCharged extends CustomCard {
    public static final String ID = FurinaHelper.makeCardID(ElectroCharged.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = "sxy/apin/img/cards/attack/card_raw_60.png";
    private static final int COST = 2;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = FURINA_BLUE;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public ElectroCharged() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 12;
        this.tags.add(CardTags.STRIKE);
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }

    @Override
    public void upgrade() {
        this.upgraded = true;
        ++this.timesUpgraded;
        this.name = NAME + "+" + this.timesUpgraded;
        this.initializeTitle();
        this.upgradeDamage(4);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        ArrayList<AbstractMonster> monsters = FurinaHelper.getMonsters();
        for (AbstractMonster monster : monsters) {
            if (!monster.isDeadOrEscaped() && monster.hasPower(ElectroChargedPower.POWER_ID)) {
                FurinaHelper.damage(monster, abstractPlayer, this.damage, DamageInfo.DamageType.NORMAL);
            }
        }
        FurinaHelper.damage(abstractMonster, abstractPlayer, this.damage, DamageInfo.DamageType.NORMAL);
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(abstractMonster, abstractPlayer,
                        new ElectroChargedPower(abstractMonster, 1), 1)
        );
    }
}
