package sxy.apin.modcore;

import basemod.BaseMod;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sxy.apin.cards.basic.*;
import sxy.apin.cards.common.*;
import sxy.apin.cards.rare.PassingOfJudgment;
import sxy.apin.cards.uncommon.FountainOfLucine;
import sxy.apin.cards.uncommon.MaisonGardiennage;
import sxy.apin.cards.uncommon.OperaEpiclese;
import sxy.apin.character.Furina;
import sxy.apin.helper.FurinaHelper;
import sxy.apin.relic.LittleCake;
import sxy.apin.relic.TravelingDoctor;

import static com.megacrit.cardcrawl.core.Settings.language;
import static sxy.apin.character.Furina.Enums.FURINA_BLUE;
import static sxy.apin.character.Furina.Enums.FURINA_CLASS;

@SpireInitializer
public class FurinaCore implements EditCardsSubscriber, EditStringsSubscriber, EditCharactersSubscriber,
        EditRelicsSubscriber, EditKeywordsSubscriber {
    public static final Logger logger = LogManager.getLogger(FurinaCore.class.getSimpleName());
    public static final Color MY_COLOR = FurinaHelper.MY_COLOR;
    // 人物选择界面按钮的图片
    private static final String MY_CHARACTER_BUTTON = "sxy/apin/img/char/furina_button.png";
    // 人物选择界面的立绘
    private static final String MY_CHARACTER_PORTRAIT = "sxy/apin/img/char/character_portrait.png";
    // 攻击牌的背景（小尺寸）
    private static final String BG_ATTACK_512 = "sxy/apin/img/512/bg_attack_512.png";
    // 能力牌的背景（小尺寸）
    private static final String BG_POWER_512 = "sxy/apin/img/512/bg_power_512.png";
    // 技能牌的背景（小尺寸）
    private static final String BG_SKILL_512 = "sxy/apin/img/512/bg_skill_512.png";
    // 在卡牌和遗物描述中的能量图标
    private static final String SMALL_ORB = "sxy/apin/img/char/small_orb.png";
    // 攻击牌的背景（大尺寸）
    private static final String BG_ATTACK_1024 = "sxy/apin/img/1024/bg_attack.png";
    // 能力牌的背景（大尺寸）
    private static final String BG_POWER_1024 = "sxy/apin/img/1024/bg_power.png";
    // 技能牌的背景（大尺寸）
    private static final String BG_SKILL_1024 = "sxy/apin/img/1024/bg_skill.png";
    // 在卡牌预览界面的能量图标
    private static final String BIG_ORB = "sxy/apin/img/char/card_orb.png";
    // 小尺寸的能量图标（战斗中，牌堆预览）
    private static final String ENEYGY_ORB = "sxy/apin/img/char/cost_orb.png";

    public FurinaCore() {
        BaseMod.subscribe(this); // 告诉basemod你要订阅事件
        BaseMod.addColor(FURINA_BLUE, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, BG_ATTACK_512, BG_SKILL_512, BG_POWER_512, ENEYGY_ORB, BG_ATTACK_1024, BG_SKILL_1024, BG_POWER_1024, BIG_ORB, SMALL_ORB);
    }

    // 注解需要调用的方法，必须写
    public static void initialize() {
        new FurinaCore();
    }


    @Override
    public void receiveEditStrings() {
        String lang;
        if (language == Settings.GameLanguage.ZHS) {
            lang = "ZHS"; // 如果语言设置为简体中文，则加载ZHS文件夹的资源
        } else {
            lang = "ZHS"; // 如果没有相应语言的版本，也加载中文
        }
        BaseMod.loadCustomStringsFile(CardStrings.class, "sxy/apin/localization/" + lang + "/cards.json"); // 加载相应语言的卡牌本地化内容。
        BaseMod.loadCustomStringsFile(CharacterStrings.class, "sxy/apin/localization/" + lang + "/characters.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, "sxy/apin/localization/" + lang + "/relics.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, "sxy/apin/localization/" + lang + "/powers.json");
        // 如果是中文，加载的就是"ExampleResources/localization/ZHS/cards.json"
    }

    // 当basemod开始注册mod卡牌时，便会调用这个函数
    @Override
    public void receiveEditCards() {
        // BASIC
        BaseMod.addCard(new ChargedAttack());
        BaseMod.addCard(new Dodge());
        BaseMod.addCard(new SeatsSacredAndSecular());
        BaseMod.addCard(new SpiritbreathThorn());
        BaseMod.addCard(new Strike());
        BaseMod.addCard(new SurgingBlade());

        // COMMON
        BaseMod.addCard(new Cake());
        BaseMod.addCard(new DailyLimitCake());
        BaseMod.addCard(new ElementalBurst());
        BaseMod.addCard(new Judge());
        BaseMod.addCard(new LaLettreAFocalors());
        BaseMod.addCard(new Performance());

        // UNCOMMON
        BaseMod.addCard(new FountainOfLucine());
        BaseMod.addCard(new MaisonGardiennage());
        BaseMod.addCard(new OperaEpiclese());

        // RARE
        BaseMod.addCard(new PassingOfJudgment());
    }

    @Override
    public void receiveEditKeywords() {
        BaseMod.addKeyword("furina_mod", "普世欢腾", new String[]{"普世欢腾"}, "拥有 #y普世欢腾 的角色在受到伤害时失去等量生命。");
        BaseMod.addKeyword("furina_mod", "芒荒能量", new String[]{"芒荒能量", "芒性能量", "荒性能量"}, "同时具有芒性能量和荒性能量的敌人会被击晕。");
        BaseMod.addKeyword("furina_mod", "气氛值", new String[]{"气氛值"}, "提高部分伤害或治疗效果。如无特殊说明每生效1次消耗1层。");
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new Furina(CardCrawlGame.playerName), MY_CHARACTER_BUTTON, MY_CHARACTER_PORTRAIT, FURINA_CLASS);
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelic(new LittleCake(), RelicType.SHARED);
        BaseMod.addRelic(new TravelingDoctor(), RelicType.SHARED);
    }
}
