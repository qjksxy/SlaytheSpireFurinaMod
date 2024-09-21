package sxy.apin.cards.basic;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sxy.apin.character.Furina;
import sxy.apin.helper.FurinaHelper;
import sxy.apin.power.CenterOfAttentionPower;

import static sxy.apin.character.Furina.Enums.FURINA_BLUE;

public class ChargedAttack extends CustomCard {
    public static final String ID = FurinaHelper.makeCardID(ChargedAttack.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = "sxy/apin/img/cards/attack/card_raw_121.png";
    private static final int COST = 2;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = FURINA_BLUE;
    private static final CardRarity RARITY = CardRarity.BASIC;
    // 是否指向敌人
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public ChargedAttack() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 3;
        this.tags.add(CardTags.STARTER_STRIKE);
        this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.updateCost(-1);
            this.upgradeDamage(3);
        }
        this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        // int revelry = Furina.getRevelry();
        // 6命额外伤害
        int extraDamage = 0;
        CenterOfAttentionPower power = (CenterOfAttentionPower) FurinaHelper.getPower(CenterOfAttentionPower.POWER_ID);
        if (power != null) {
            if (power.isUpgraded()) {
                extraDamage = (int) (abstractPlayer.maxHealth * 0.15);
                Furina.gainRevelry(10);
            } else {
                extraDamage = (int) (abstractPlayer.maxHealth * 0.1);
                Furina.gainRevelry(4);
            }
        }

        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(abstractMonster,
                        new DamageInfo(abstractPlayer, damage + extraDamage, DamageInfo.DamageType.NORMAL)
                )
        );
        Furina.gainElementEnergy(1);
        AbstractDungeon.actionManager.addToBottom(
                new MakeTempCardInHandAction(new SeatsSacredAndSecular(), 1)
        );
    }


}
