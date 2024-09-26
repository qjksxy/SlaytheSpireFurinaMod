package sxy.apin.character;

import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import sxy.apin.cards.basic.ChargedAttack;
import sxy.apin.cards.basic.Dodge;
import sxy.apin.cards.basic.Strike;
import sxy.apin.cards.common.Cake;
import sxy.apin.cards.common.ElementalBurst;
import sxy.apin.cards.common.ElementalSkill;
import sxy.apin.helper.FurinaHelper;
import sxy.apin.modcore.FurinaCore;
import sxy.apin.power.*;
import sxy.apin.relic.LittleCake;
import sxy.apin.relic.TravelingDoctor;

import java.util.ArrayList;

import static sxy.apin.character.Furina.Enums.FURINA_BLUE;
import static sxy.apin.character.Furina.Enums.FURINA_CLASS;

public class Furina extends CustomPlayer {
    public static final int ERROR = -1;
    // 荒--输出
    public static final int OUSIA = 0;
    // 芒--治疗
    public static final int PNEUMA = 1;

    // 火堆的人物立绘（行动前）
    private static final String MY_CHARACTER_SHOULDER_1 = "sxy/apin/img/char/shoulder.png";
    // 火堆的人物立绘（行动后）
    private static final String MY_CHARACTER_SHOULDER_2 = "sxy/apin/img/char/shoulder2.png";
    // 人物死亡图像
    private static final String CORPSE_IMAGE = "sxy/apin/img/char/corpse.png";
    // 战斗界面左下角能量图标的每个图层
    private static final String[] ORB_TEXTURES = new String[]{
            "sxy/apin/img/UI/orb/layer5.png",
            "sxy/apin/img/UI/orb/layer4.png",
            "sxy/apin/img/UI/orb/layer3.png",
            "sxy/apin/img/UI/orb/layer2.png",
            "sxy/apin/img/UI/orb/layer1.png",
            "sxy/apin/img/UI/orb/layer6.png",
            "sxy/apin/img/UI/orb/layer5d.png",
            "sxy/apin/img/UI/orb/layer4d.png",
            "sxy/apin/img/UI/orb/layer3d.png",
            "sxy/apin/img/UI/orb/layer2d.png",
            "sxy/apin/img/UI/orb/layer1d.png"
    };
    // 每个图层的旋转速度
    private static final float[] LAYER_SPEED = new float[]{-40.0F, -32.0F, 20.0F, -20.0F, 0.0F, -10.0F, -8.0F, 5.0F, -5.0F, 0.0F};
    // 人物的本地化文本，如卡牌的本地化文本一样，如何书写见下
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString("ApinFurina:Furina");

    public Furina(String name) {
        super(name, FURINA_CLASS, ORB_TEXTURES, "sxy/apin/img/UI/orb/vfx.png", LAYER_SPEED, null, null);


        // 人物对话气泡的大小，如果游戏中尺寸不对在这里修改（libgdx的坐标轴左下为原点）
        this.dialogX = (this.drawX + 0.0F * Settings.scale);
        this.dialogY = (this.drawY + 150.0F * Settings.scale);


        // 初始化你的人物，如果你的人物只有一张图，那么第一个参数填写你人物图片的路径。
        this.initializeClass(
                "sxy/apin/img/char/character.png", // 人物图片
                MY_CHARACTER_SHOULDER_2, MY_CHARACTER_SHOULDER_1,
                CORPSE_IMAGE, // 人物死亡图像
                this.getLoadout(),
                0.0F, 0.0F,
                200.0F, 220.0F, // 人物碰撞箱大小，越大的人物模型这个越大
                new EnergyManager(3) // 初始每回合的能量
        );


        // 如果你的人物没有动画，那么这些不需要写
        // this.loadAnimation("sxy/apin/img/char/character.atlas", "sxy/apin/img/char/character.json", 1.8F);
        // AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        // e.setTime(e.getEndTime() * MathUtils.random());
        // e.setTimeScale(1.2F);


    }

    /**
     * 获取芙宁娜当前的始基力形态
     */
    public static int getArkhe() {
        AbstractPlayer player = AbstractDungeon.player;
        if (player == null) {
            return ERROR;
        }
        int arkhe = Furina.OUSIA;
        if (player.hasPower(Pneuma.POWER_ID)) {
            arkhe = Furina.PNEUMA;
        } else if (!player.hasPower(Ousia.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(player, player,
                            new Ousia(player), 0)
            );
        }
        return arkhe;
    }


    // 芙宁娜特有能力：切换始基力形态
    public static void switchArkhe() {
        AbstractPlayer player = AbstractDungeon.player;
        if (player == null) {
            return;
        }
        if (player.hasPower(Pneuma.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(
                    new RemoveSpecificPowerAction(player, player, Pneuma.POWER_ID)
            );
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(player, player,
                            new Ousia(player), 0)
            );
        } else if (player.hasPower(Ousia.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(
                    new RemoveSpecificPowerAction(player, player, Ousia.POWER_ID)
            );
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(player, player,
                            new Pneuma(player), 0)
            );
        } else {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(player, player,
                            new Ousia(player), 0)
            );
        }
    }

    public static void consumeRevelry(int amount) {
        AbstractPlayer player = AbstractDungeon.player;
        if (player == null || player.hasPower(FrozenPower.POWER_ID)) {
            return;
        }
        if (player.hasPower(Revelry.POWER_ID)) {
            AbstractPower revelry = player.getPower(Revelry.POWER_ID);
            revelry.flash();
            AbstractDungeon.actionManager.addToBottom(
                    new ReducePowerAction(player, player, Revelry.POWER_ID, amount)
            );
        }
    }

    public static void gainRevelry(int amount) {
        AbstractPlayer player = FurinaHelper.getPlayer();
        if (player == null) {
            return;
        }
        if (FurinaHelper.hasPower(AWomanAdaptsPower.POWER_ID)) {
            amount += 1;
        }
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(player, player, new Revelry(player, amount), amount)
        );
    }

    public static int getRevelry() {
        AbstractPlayer player = AbstractDungeon.player;
        if (player == null) {
            return ERROR;
        }
        int revelry = 0;
        if (player.hasPower(Revelry.POWER_ID)) {
            revelry = player.getPower(Revelry.POWER_ID).amount;
        }
        MySecretIsHiddenWithinMePower power = (MySecretIsHiddenWithinMePower) FurinaHelper.getPower(MySecretIsHiddenWithinMePower.POWER_ID);
        if (power != null) {
            if (power.isUpgraded()) {
                revelry = (int) (revelry * 2.2);
            } else {
                revelry = (int) (revelry * 1.8);
            }
        }
        return revelry;
    }

    public static void gainElementEnergy(int amount) {
        AbstractPlayer player = AbstractDungeon.player;
        if (player == null) {
            return;
        }
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(player, player,
                        new ElementEnergy(player, amount))
        );
    }

    // 初始卡组的ID，可直接写或引用变量
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(ChargedAttack.ID);
        retVal.add(Dodge.ID);
        retVal.add(Dodge.ID);
        retVal.add(Cake.ID);
        retVal.add(ElementalSkill.ID);
        retVal.add(ElementalSkill.ID);
        retVal.add(ElementalBurst.ID);
        return retVal;
    }

    // 初始遗物的ID，可以先写个原版遗物凑数
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(LittleCake.ID);
        retVal.add(TravelingDoctor.ID);
        return retVal;
    }

    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(
                characterStrings.NAMES[0], // 人物名字
                characterStrings.TEXT[0], // 人物介绍
                120, // 当前血量
                120, // 最大血量
                0, // 初始充能球栏位
                200, // 初始携带金币
                5, // 每回合抽牌数量
                this, // 别动
                this.getStartingRelics(), // 初始遗物
                this.getStartingDeck(), // 初始卡组
                false // 别动
        );
    }

    // 人物名字（出现在游戏左上角）
    @Override
    public String getTitle(PlayerClass playerClass) {
        return characterStrings.NAMES[0];
    }

    // 你的卡牌颜色（这个枚举在最下方创建）
    @Override
    public AbstractCard.CardColor getCardColor() {
        return FURINA_BLUE;
    }

    // 翻牌事件出现的你的职业牌（一般设为打击）
    @Override
    public AbstractCard getStartCardForEvent() {
        return new Strike();
    }

    // 卡牌轨迹颜色
    @Override
    public Color getCardTrailColor() {
        return FurinaCore.MY_COLOR;
    }

    // 高进阶带来的生命值损失
    @Override
    public int getAscensionMaxHPLoss() {
        return 5;
    }

    // 卡牌的能量字体，没必要修改
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontBlue;
    }

    // 人物选择界面点击你的人物按钮时触发的方法，这里为屏幕轻微震动
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }

    // 碎心图片
    @Override
    public ArrayList<CutscenePanel> getCutscenePanels() {
        ArrayList<CutscenePanel> panels = new ArrayList<>();
        // 有两个参数的，第二个参数表示出现图片时播放的音效
        panels.add(new CutscenePanel("sxy/apin/img/char/Victory1.png", "ATTACK_MAGIC_FAST_1"));
        panels.add(new CutscenePanel("sxy/apin/img/char/Victory2.png"));
        panels.add(new CutscenePanel("sxy/apin/img/char/Victory3.png"));
        return panels;
    }

    // 自定义模式选择你的人物时播放的音效
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_HEAVY";
    }

    // 游戏中左上角显示在你的名字之后的人物名称
    @Override
    public String getLocalizedCharacterName() {
        return characterStrings.NAMES[0];
    }

    // 创建人物实例，照抄
    @Override
    public AbstractPlayer newInstance() {
        return new Furina(this.name);
    }

    // 第三章面对心脏说的话（例如战士是“你握紧了你的长刀……”之类的）
    @Override
    public String getSpireHeartText() {
        return characterStrings.TEXT[1];
    }

    // 打心脏的颜色，不是很明显
    @Override
    public Color getSlashAttackColor() {
        return FurinaCore.MY_COLOR;
    }

    // 吸血鬼事件文本，主要是他（索引为0）和她（索引为1）的区别（机器人另外）
    @Override
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[1];
    }

    // 卡牌选择界面选择该牌的颜色
    @Override
    public Color getCardRenderColor() {
        return FurinaCore.MY_COLOR;
    }

    // 第三章面对心脏造成伤害时的特效
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL};
    }

    // 为原版人物枚举、卡牌颜色枚举扩展的枚举，需要写，接下来要用
    // ***填在SpireEnum中的name需要一致***
    public static class Enums {
        // 注意此处是在 MyCharacter 类内部的静态嵌套类 Enums 中定义的新 PlayerClass 枚举值
        // 不可将该定义放在外部的 MyCharacter 类中，具体原因见《高级技巧 / 01 - Patch / SpireEnum》
        @SpireEnum
        public static PlayerClass FURINA_CLASS;

        @SpireEnum(name = "FURINA_BLUE")
        public static AbstractCard.CardColor FURINA_BLUE;

        @SpireEnum(name = "FURINA_BLUE")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType FURINA_LIBRARY;
    }
}
