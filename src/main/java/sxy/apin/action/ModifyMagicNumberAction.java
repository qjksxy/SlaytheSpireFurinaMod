package sxy.apin.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.UUID;

/**
 * 修改本场战斗内卡牌的魔法值
 */
public class ModifyMagicNumberAction extends AbstractGameAction {
    private final UUID uuid;

    public ModifyMagicNumberAction(UUID targetUUID, int amount) {
        this.setValues(this.target, this.source, amount);
        this.actionType = ActionType.DEBUFF;
        this.uuid = targetUUID;
    }

    @Override
    public void update() {
        for (AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
            c.baseMagicNumber += this.amount;
            c.magicNumber = c.baseMagicNumber;
        }
        this.isDone = true;
    }
}
