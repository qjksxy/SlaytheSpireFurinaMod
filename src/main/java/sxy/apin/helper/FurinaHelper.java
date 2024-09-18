package sxy.apin.helper;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.Random;

public class FurinaHelper {
    public static final String MOD_ID = "ApinFurina";
    // 89, 138, 194
    public static final Color MY_COLOR = new Color(89.0F / 255.0F, 138.0F / 255.0F, 194.0F / 255.0F, 1.0F);
    public static Random random = new Random();
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

    public static void removePlayerPower(String powerID) {
        AbstractPlayer player = getPlayer();
        AbstractDungeon.actionManager.addToBottom(
                new RemoveSpecificPowerAction(player, player, powerID)
        );
    }

    public static void reducePlayerPower(String powerID, int amount) {
        AbstractPlayer player = getPlayer();
        AbstractDungeon.actionManager.addToBottom(
                new ReducePowerAction(player, player, powerID, amount)
        );
    }

    public static ArrayList<AbstractMonster> getMonsters() {
        return AbstractDungeon.getCurrRoom().monsters.monsters;
    }

    public static AbstractMonster getRandomMonster() {
        return AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster) null, true, AbstractDungeon.cardRandomRng);
    }

    public static AbstractMonster getNearestMonster() {
        ArrayList<AbstractMonster> monsters = getMonsters();
        AbstractMonster mon = null;
        for (AbstractMonster monster : monsters) {
            if (!monster.isDeadOrEscaped() && monster.currentHealth > 0) {
                mon = monster;
            }
        }
        return mon;
    }

    public static void damage(AbstractCreature target, AbstractCreature source, int baseDamage, DamageInfo.DamageType type) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(source, baseDamage, type))
        );
    }

    public static ArrayList<AbstractCard> getHandCards() {
        return AbstractDungeon.player.hand.group;
    }

    public static ArrayList<AbstractCard> getDrawPile() {
        return AbstractDungeon.player.drawPile.group;
    }

    public static ArrayList<AbstractCard> getDiscardPile() {
        return AbstractDungeon.player.discardPile.group;
    }

    public static ArrayList<AbstractCard> getExhaustPile() {
        return AbstractDungeon.player.exhaustPile.group;
    }

    public static boolean hasPower(String powerID) {
        return FurinaHelper.getPlayer().hasPower(powerID);
    }

    public static AbstractPower getPower(String powerID) {
        if (!hasPower(powerID)) {
            return null;
        }
        return getPlayer().getPower(powerID);
    }

    public static Random getRandom() {
        return random;
    }

    public static double getRandomFloat() {
        return random.nextDouble();
    }
}
