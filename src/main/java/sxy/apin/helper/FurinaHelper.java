package sxy.apin.helper;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FurinaHelper {
    public static final String MOD_ID = "ApinFurina";
    // 89, 138, 194
    public static final Color MY_COLOR = new Color(89.0F / 255.0F, 138.0F / 255.0F, 194.0F / 255.0F, 1.0F);

    public static String makeCardID(String id) {
        return MOD_ID + ":" + id;
    }

    public static String makeRelicID(String id) {
        return MOD_ID + ":" + id;
    }

    public static String makePowerID(String id) {
        return MOD_ID + ":" + id;
    }

    public static void addToBottom(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    public static AbstractPlayer getPlayer() {
        return AbstractDungeon.player;
    }

    public static void applyPower(AbstractCreature target, AbstractCreature source, AbstractPower power, int stackAmount) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(source, target, power, stackAmount)
        );
    }

    public static void damage(AbstractCreature target, AbstractCreature source, int baseDamage, DamageInfo.DamageType type) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(source, baseDamage, type))
        );
    }


}
