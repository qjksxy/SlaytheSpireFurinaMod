package sxy.apin.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sxy.apin.helper.FurinaHelper;
import sxy.apin.power.TestPower;

/**
 * 正义  0费  4伤  将1张牌放入抽牌堆。
 */
public class TestAction extends AbstractGameAction {
    private final AbstractPlayer p;

    public TestAction() {
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        AbstractMonster monster = FurinaHelper.getRandomMonster();
        int damage = 10;
        monster.damage(new DamageInfo(this.p, damage, DamageInfo.DamageType.NORMAL));
        if (monster.lastDamageTaken > 0) {
            TestPower power = (TestPower) FurinaHelper.getPower(TestPower.POWER_ID);
            if (power != null) {
                power.count += monster.lastDamageTaken;
            }
        }
        this.isDone = true;
    }
}
