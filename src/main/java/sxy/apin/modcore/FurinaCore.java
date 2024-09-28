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
import sxy.apin.cards.rare.*;
import sxy.apin.cards.uncommon.*;
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
    @SuppressWarnings("unused")
    public static final Logger logger = LogManager.getLogger(FurinaCore.class.getSimpleName());
    public static final Color MY_COLOR = FurinaHelper.MY_COLOR;
    // 人物选择界面按钮的图片
    private static final String MY_CHARACTER_BUTTON = "sxy/apin/img/char/furina_button.png";
    // 人物选择界面的立绘
    private static final String MY_CHARACTER_PORTRAIT = "sxy/apin/img/char/furina_1920_1200.png";
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
    @SuppressWarnings("unused")
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
        BaseMod.addCard(new TestCard());
        // BASIC--6
        BaseMod.addCard(new ChargedAttack());
        BaseMod.addCard(new Dodge());
        BaseMod.addCard(new SeatsSacredAndSecular());
        BaseMod.addCard(new SpiritbreathThorn());
        BaseMod.addCard(new Strike());
        BaseMod.addCard(new SurgingBlade());

        // COMMON--18
        BaseMod.addCard(new ArmoredCrab());
        BaseMod.addCard(new BallOctopus());
        BaseMod.addCard(new Bloom());
        BaseMod.addCard(new Blubberbeast());
        BaseMod.addCard(new BubblySeahorse());
        BaseMod.addCard(new Cake());
        BaseMod.addCard(new Crystalize());
        BaseMod.addCard(new ElectroCharged());
        BaseMod.addCard(new ElementalBurst());
        BaseMod.addCard(new ElementalSkill());
        BaseMod.addCard(new FleuveCendreFerryman());
        BaseMod.addCard(new FontinaliaMousse());
        BaseMod.addCard(new Frozen());
        BaseMod.addCard(new OratriceMecaniquedAnalyseCardinale());
        BaseMod.addCard(new Swirl());
        BaseMod.addCard(new TheDockhandsAssistant());
        BaseMod.addCard(new Vaporize());
        BaseMod.addCard(new WolfFang());

        // UNCOMMON--36
        BaseMod.addCard(new AllKindreds());
        BaseMod.addCard(new AllLaws());
        BaseMod.addCard(new AllPeople());
        BaseMod.addCard(new AllWaters());
        BaseMod.addCard(new CourtOfFontaine());
        BaseMod.addCard(new EndlessSoloOfSolitude());
        BaseMod.addCard(new Equity());
        BaseMod.addCard(new Erinnyes());
        BaseMod.addCard(new Fontatine());
        BaseMod.addCard(new FortressOfMeropide());
        BaseMod.addCard(new FountainOfLucine());
        BaseMod.addCard(new GentilhommeUsher());
        BaseMod.addCard(new Justice());
        BaseMod.addCard(new KeyOfKhajNisut());
        BaseMod.addCard(new LaLettreAFocalors());
        BaseMod.addCard(new LightOfFoliarIncision());
        BaseMod.addCard(new MademoiselleCrabaletta());
        BaseMod.addCard(new MaisonGardiennage());
        BaseMod.addCard(new MelusineSupport());
        BaseMod.addCard(new MistsplitterReforged());
        BaseMod.addCard(new OperaEpiclese());
        BaseMod.addCard(new Order());
        BaseMod.addCard(new PalaisMermonia());
        BaseMod.addCard(new PassingOfJudgment());
        BaseMod.addCard(new PerpetualMuseOfChansons());
        BaseMod.addCard(new PerpetualMuseOfRondeaux());
        BaseMod.addCard(new PrimordialJadeCutter());
        BaseMod.addCard(new SingerOfManyWaters());
        BaseMod.addCard(new SlowingWater());
        BaseMod.addCard(new SoothingWater());
        BaseMod.addCard(new SurintendanteChevalmarin());
        BaseMod.addCard(new UrakuMisugiri());
        BaseMod.addCard(new VarunadaLazuriteGemstone());
        BaseMod.addCard(new WaterAndJustice());
        BaseMod.addCard(new WeepingWillowOfTheLake());
        BaseMod.addCard(new WovenWaters());

        // RARE--20
        BaseMod.addCard(new AWomanAdapts());
        BaseMod.addCard(new Banquet());
        BaseMod.addCard(new CoronatedPrimaDonna());
        BaseMod.addCard(new DewsFirstGlimmerAtDaybreak());
        BaseMod.addCard(new EndlessWaltz());
        BaseMod.addCard(new GymnopediesOfLune());
        BaseMod.addCard(new HearMe());
        BaseMod.addCard(new INowKnowItIs());
        BaseMod.addCard(new LaVaguelette());
        BaseMod.addCard(new LeSouvenirAvecLeCrepuscule());
        BaseMod.addCard(new LoveIsARebelliousBird());
        BaseMod.addCard(new MemberOfTheCast());
        BaseMod.addCard(new MySecretIsHiddenWithinMe());
        BaseMod.addCard(new PeripateticPeregrination());
        BaseMod.addCard(new PourLaJustice());
        BaseMod.addCard(new SplendorOfTranquilWaters());
        BaseMod.addCard(new TheLittleOceanid());
        BaseMod.addCard(new TheSeaIsMyStage());
        BaseMod.addCard(new UnheardConfession());
        BaseMod.addCard(new WhoDweltInTheNetherworld());
    }

    @Override
    public void receiveEditKeywords() {
        BaseMod.addKeyword("furina_mod", "普世欢腾", new String[]{"普世欢腾"}, "受到伤害或治疗时获得3层 #y气氛值 。");
        BaseMod.addKeyword("furina_mod", "芒荒能量", new String[]{"芒荒能量", "芒性能量", "荒性能量"}, "同时具有芒性能量和荒性能量时会引发芒荒过载：获得1点 #y元素能量 。并顺次获得战意/珠露/会心。");
        BaseMod.addKeyword("furina_mod", "气氛值", new String[]{"气氛值"}, "提高部分伤害或治疗效果。每回合结束时消耗10%。");
        BaseMod.addKeyword("furina_mod", "元素能量", new String[]{"元素能量"}, "特定牌打出需要消耗一定的元素能量。");
        BaseMod.addKeyword("furina_mod", "始基力", new String[]{"始基力", "始基力形态", "芒荒形态"}, "芙宁娜拥有荒芒两种始基力形态，可通过特定牌切换自身的始基力。部分牌在不同形态下有不同效果。");
        BaseMod.addKeyword("furina_mod", "会心", new String[]{"会心"}, "每打出一张攻击牌获得2层 #y会心 ，受到一次伤害消除3层 #y会心 。回合结束时对最近敌人造成 #y会心 层数的伤害。");
        BaseMod.addKeyword("furina_mod", "珠露", new String[]{"珠露"}, "每受到1点治疗累计1层 #y珠露 。回合开始时，若 #y珠露 层数大于生命上限的10%， #y珠露 破碎，对敌方全体造成 #y珠露 层数的伤害。");
        BaseMod.addKeyword("furina_mod", "战意", new String[]{"战意"}, "每受到伤害或治疗时，获得1层 #y战意 。回合开始阶段，获得 #y战意 层数的护盾，随后消耗三分之一的层数。");
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
