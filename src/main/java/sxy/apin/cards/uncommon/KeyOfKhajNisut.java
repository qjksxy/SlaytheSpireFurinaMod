package sxy.apin.cards.uncommon;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sxy.apin.helper.FurinaHelper;

import static sxy.apin.character.Furina.Enums.FURINA_BLUE;

public class KeyOfKhajNisut extends CustomCard {
    public static final String ID = FurinaHelper.makeCardID(KeyOfKhajNisut.class.getSimpleName());
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final String IMG_PATH = "sxy/apin/img/cards/Strike.png";
    private static final int COST = 2;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = FURINA_BLUE;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public KeyOfKhajNisut() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 10;
        this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.updateCost(-1);
        }
        this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(abstractMonster,
                        new DamageInfo(abstractPlayer, damage, DamageInfo.DamageType.NORMAL)
                )
        );
        // TODO 新增一个 Action
//        private int increaseHpAmount;
//        private DamageInfo info;
//        private static final float DURATION = 0.1F;
//
//    public FeedAction(AbstractCreature target, DamageInfo info, int maxHPAmount) {
//            this.info = info;
//            this.setValues(target, info);
//            this.increaseHpAmount = maxHPAmount;
//            this.actionType = AbstractGameAction.ActionType.DAMAGE;
//            this.duration = 0.1F;
//        }
//
//        public void update() {
//            if (this.duration == 0.1F && this.target != null) {
//                AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AbstractGameAction.AttackEffect.NONE));
//                this.target.damage(this.info);
//                if ((((AbstractMonster)this.target).isDying || this.target.currentHealth <= 0) && !this.target.halfDead && !this.target.hasPower("Minion")) {
//                    AbstractDungeon.player.increaseMaxHp(this.increaseHpAmount, false);
//                    if (this.target instanceof Donu) {
//                        UnlockTracker.unlockAchievement("DONUT");
//                    }
//                }
//
//                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
//                    AbstractDungeon.actionManager.clearPostCombatActions();
//                }
//            }
//
//            this.tickDuration();
//        }

    }
}
