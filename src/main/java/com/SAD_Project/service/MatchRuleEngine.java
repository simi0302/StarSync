package com.SAD_Project.service;

import com.SAD_Project.util.ZodiacCalculator; // 引入計算星座的工具類別
import org.springframework.stereotype.Service; // Spring 的服務註解
import java.util.HashMap;
import java.util.Map;

// 這個class負責所有配對相關的計算
@Service
public class MatchRuleEngine {

    // 二層 Map，儲存星座配對資料
    // 第一層的 key 是第一個星座，第二層的 key 是第二個星座
    // 例如：matchDataStorage.get("牡羊座").get("獅子座") 可以取得配對結果
    private final Map<String, Map<String, MatchResult>> matchDataStorage;

    public MatchRuleEngine() {
        this.matchDataStorage = allZodiacMatchData(); // 呼叫方法來建立所有星座的配對資料
    }

    // 這個內部類別用來儲存配對的結果資料
    public static class MatchResult {
        private int score;       // 配對分數
        private String level;    // 等級（大吉、小吉、普通、小凶、大凶）
        private int stars;       // 星級
        private String pros; // 優點
        private String cons; // 缺點
        private String comment;  // 評語

        // 建構子，建立時只需要分數和等級
        public MatchResult(int score, String level) {
            this.score = score;
            this.level = level;
        }

        //getter 和 setter 方法
        public int getScore() { return score; }
        public String getLevel() { return level; }
        public int getStars() { return stars; }
        public String getPros() { return pros; }
        public String getCons() { return cons; }
        public String getComment() { return comment; }

        public void setStars(int stars) { this.stars = stars; }
        public void setPros(String pros) { this.pros = pros; }
        public void setCons(String cons) { this.cons = cons; }
        public void setComment(String comment) { this.comment = comment; }
    }

    // 根據日期算星座 (從 AnalysisController 移入)
    public String calculateZodiac(String birthDate) {
        // 如果生日是 null 或空字串，就回傳 null
        if (birthDate == null || birthDate.trim().isEmpty()) {
            return null;
        }

        return ZodiacCalculator.calculateZodiacFromString(birthDate); // 使用工具類別來計算星座
    }

    // 算配對結果
    public MatchResult computeMatch(String zodiac1, String zodiac2) {
        if (zodiac1 == null || zodiac2 == null) {
            return null;
        }

        // 檢查這兩個星座是否在資料庫裡面
        if (!matchDataStorage.containsKey(zodiac1) || !matchDataStorage.containsKey(zodiac2)) {
            return null;
        }

        // 從資料庫取得配對結果
        // 因為配對是雙向的，所以 zodiac1 和 zodiac2 的順序可以互換
        return matchDataStorage.get(zodiac1).get(zodiac2);
    }

    // 初始化配對矩陣
    // 這個方法會建立所有星座之間的配對資料
    private Map<String, Map<String, MatchResult>> allZodiacMatchData() {

        Map<String, Map<String, MatchResult>> individualMatchDataMap = new HashMap<>();  // 建立二層 Map 來存配對資料

        String[] zodiacs = {"牡羊座", "金牛座", "雙子座", "巨蟹座", "獅子座",
                "處女座", "天秤座", "天蠍座", "射手座", "魔羯座",
                "水瓶座", "雙魚座"};

        // 先為每個星座建立一個空的 Map, 把配對資料放進去
        for (String zodiac : zodiacs) {
            individualMatchDataMap.put(zodiac, new HashMap<>());
        }

        // 開始加配對資料
        addMatch(individualMatchDataMap, "牡羊座", "牡羊座", 75, "小吉");
        addMatch(individualMatchDataMap, "牡羊座", "金牛座", 63, "普通");
        addMatch(individualMatchDataMap, "牡羊座", "雙子座", 74, "小吉");
        addMatch(individualMatchDataMap, "牡羊座", "巨蟹座", 47, "普通");
        addMatch(individualMatchDataMap, "牡羊座", "獅子座", 90, "大吉");
        addMatch(individualMatchDataMap, "牡羊座", "處女座", 42, "普通");
        addMatch(individualMatchDataMap, "牡羊座", "天秤座", 62, "普通");
        addMatch(individualMatchDataMap, "牡羊座", "天蠍座", 48, "普通");
        addMatch(individualMatchDataMap, "牡羊座", "射手座", 87, "大吉");
        addMatch(individualMatchDataMap, "牡羊座", "魔羯座", 38, "小凶");
        addMatch(individualMatchDataMap, "牡羊座", "水瓶座", 68, "普通");
        addMatch(individualMatchDataMap, "牡羊座", "雙魚座", 29, "小凶");

        addMatch(individualMatchDataMap, "金牛座", "金牛座", 86, "大吉");
        addMatch(individualMatchDataMap, "金牛座", "雙子座", 23, "小凶");
        addMatch(individualMatchDataMap, "金牛座", "巨蟹座", 91, "大吉");
        addMatch(individualMatchDataMap, "金牛座", "獅子座", 29, "小凶");
        addMatch(individualMatchDataMap, "金牛座", "處女座", 73, "小吉");
        addMatch(individualMatchDataMap, "金牛座", "天秤座", 33, "小凶");
        addMatch(individualMatchDataMap, "金牛座", "天蠍座", 89, "大吉");
        addMatch(individualMatchDataMap, "金牛座", "射手座", 31, "小凶");
        addMatch(individualMatchDataMap, "金牛座", "魔羯座", 89, "大吉");
        addMatch(individualMatchDataMap, "金牛座", "水瓶座", 11, "大凶");
        addMatch(individualMatchDataMap, "金牛座", "雙魚座", 88, "大吉");

        addMatch(individualMatchDataMap, "雙子座", "雙子座", 83, "小吉");
        addMatch(individualMatchDataMap, "雙子座", "巨蟹座", 21, "小凶");
        addMatch(individualMatchDataMap, "雙子座", "獅子座", 82, "小吉");
        addMatch(individualMatchDataMap, "雙子座", "處女座", 40, "小凶");
        addMatch(individualMatchDataMap, "雙子座", "天秤座", 78, "小吉");
        addMatch(individualMatchDataMap, "雙子座", "天蠍座", 15, "大凶");
        addMatch(individualMatchDataMap, "雙子座", "射手座", 92, "大吉");
        addMatch(individualMatchDataMap, "雙子座", "魔羯座", 15, "大凶");
        addMatch(individualMatchDataMap, "雙子座", "水瓶座", 85, "小吉");
        addMatch(individualMatchDataMap, "雙子座", "雙魚座", 10, "大凶");

        addMatch(individualMatchDataMap, "巨蟹座", "巨蟹座", 85, "小吉");
        addMatch(individualMatchDataMap, "巨蟹座", "獅子座", 29, "小凶");
        addMatch(individualMatchDataMap, "巨蟹座", "處女座", 77, "小吉");
        addMatch(individualMatchDataMap, "巨蟹座", "天秤座", 28, "小凶");
        addMatch(individualMatchDataMap, "巨蟹座", "天蠍座", 79, "小吉");
        addMatch(individualMatchDataMap, "巨蟹座", "射手座", 27, "小凶");
        addMatch(individualMatchDataMap, "巨蟹座", "魔羯座", 84, "小吉");
        addMatch(individualMatchDataMap, "巨蟹座", "水瓶座", 31, "小凶");
        addMatch(individualMatchDataMap, "巨蟹座", "雙魚座", 72, "小吉");

        addMatch(individualMatchDataMap, "獅子座", "獅子座", 78, "小吉");
        addMatch(individualMatchDataMap, "獅子座", "處女座", 35, "小凶");
        addMatch(individualMatchDataMap, "獅子座", "天秤座", 75, "小吉");
        addMatch(individualMatchDataMap, "獅子座", "天蠍座", 29, "小凶");
        addMatch(individualMatchDataMap, "獅子座", "射手座", 75, "小吉");
        addMatch(individualMatchDataMap, "獅子座", "魔羯座", 27, "小凶");
        addMatch(individualMatchDataMap, "獅子座", "水瓶座", 89, "大吉");
        addMatch(individualMatchDataMap, "獅子座", "雙魚座", 14, "大凶");

        addMatch(individualMatchDataMap, "處女座", "處女座", 81, "小吉");
        addMatch(individualMatchDataMap, "處女座", "天秤座", 65, "普通");
        addMatch(individualMatchDataMap, "處女座", "天蠍座", 76, "小吉");
        addMatch(individualMatchDataMap, "處女座", "射手座", 32, "小凶");
        addMatch(individualMatchDataMap, "處女座", "魔羯座", 77, "小吉");
        addMatch(individualMatchDataMap, "處女座", "水瓶座", 30, "小凶");
        addMatch(individualMatchDataMap, "處女座", "雙魚座", 86, "大吉");

        addMatch(individualMatchDataMap, "天秤座", "天秤座", 80, "小吉");
        addMatch(individualMatchDataMap, "天秤座", "天蠍座", 29, "小凶");
        addMatch(individualMatchDataMap, "天秤座", "射手座", 71, "小吉");
        addMatch(individualMatchDataMap, "天秤座", "魔羯座", 34, "小凶");
        addMatch(individualMatchDataMap, "天秤座", "水瓶座", 68, "普通");
        addMatch(individualMatchDataMap, "天秤座", "雙魚座", 50, "普通");

        addMatch(individualMatchDataMap, "天蠍座", "天蠍座", 66, "普通");
        addMatch(individualMatchDataMap, "天蠍座", "射手座", 30, "小凶");
        addMatch(individualMatchDataMap, "天蠍座", "魔羯座", 64, "普通");
        addMatch(individualMatchDataMap, "天蠍座", "水瓶座", 30, "小凶");
        addMatch(individualMatchDataMap, "天蠍座", "雙魚座", 81, "小吉");

        addMatch(individualMatchDataMap, "射手座", "射手座", 74, "小吉");
        addMatch(individualMatchDataMap, "射手座", "魔羯座", 38, "小凶");
        addMatch(individualMatchDataMap, "射手座", "水瓶座", 83, "小吉");
        addMatch(individualMatchDataMap, "射手座", "雙魚座", 50, "普通");

        addMatch(individualMatchDataMap, "魔羯座", "魔羯座", 62, "普通");
        addMatch(individualMatchDataMap, "魔羯座", "水瓶座", 37, "小凶");
        addMatch(individualMatchDataMap, "魔羯座", "雙魚座", 76, "小吉");

        addMatch(individualMatchDataMap, "水瓶座", "水瓶座", 74, "小吉");
        addMatch(individualMatchDataMap, "水瓶座", "雙魚座", 38, "小凶");

        addMatch(individualMatchDataMap, "雙魚座", "雙魚座", 73, "小吉");

        return individualMatchDataMap;
    }

    // 加配對資料，雙向都要加，並直接計算評價
    private void addMatch(Map<String, Map<String, MatchResult>> matrix,
                          String zodiac1, String zodiac2, int score, String level) {

        MatchResult result = new MatchResult(score, level); // 建立配對結果物件

        Map<String, String> evaluation = getEvaluation(level); // 根據等級取得評價文字（優點、缺點、評語）
        result.setStars(calculateStars(score)); // 根據分數計算星級
        result.setPros(evaluation.get("positive"));
        result.setCons(evaluation.get("negative"));
        result.setComment(evaluation.get("comment"));

        // 因為配對是雙向的，所以兩個方向都要存
        // 例如：牡羊座配獅子座 和 獅子座配牡羊座 結果一樣
        matrix.get(zodiac1).put(zodiac2, result);
        matrix.get(zodiac2).put(zodiac1, result);
    }

    // 根據配對分數score來計算星級（1-5 顆星）
    private int calculateStars(int score) {
        if (score >= 85) return 5;
        if (score >= 70) return 4;
        if (score >= 50) return 3;
        if (score >= 30) return 2;
        return 1;
    }

    // 根據配對等級level來取得對應的優點、缺點和評語
    private Map<String, String> getEvaluation(String level) {

        Map<String, String> evaluation = new HashMap<>(); // 建立 Map 來存評價文字

        switch (level) {
            case "大吉":
                evaluation.put("positive", "性格相配，價值觀相近\n能夠互相理解和支持\n關係穩定和諧");
                evaluation.put("negative", "可能缺乏新鮮感\n需要保持熱情");
                evaluation.put("comment", "你們的星座組合非常相配，在性格和價值觀上有很多共通點，能夠互相理解和支持，是一對很好的組合。建議好好珍惜這段緣分！");
                break;

            case "小吉":
                evaluation.put("positive", "組合相當不錯\n可以互補不足\n關係穩定");
                evaluation.put("negative", "有些差異需要磨合\n需要更多溝通");
                evaluation.put("comment", "你們的星座組合相當不錯，雖然有些差異，但可以互補，只要多溝通和理解，關係會很穩定。建議多了解對方的想法和需求。");
                break;

            case "普通":
                evaluation.put("positive", "有發展潛力\n可以互相學習");
                evaluation.put("negative", "需要更多努力\n需要增進理解");
                evaluation.put("comment", "你們的星座組合需要更多的努力來維持關係，建議多了解對方的想法和需求，增進彼此的理解。雖然配對分數普通，但真愛可以克服一切困難。");
                break;

            case "小凶":
                evaluation.put("positive", "仍有發展空間\n可以建立穩定關係");
                evaluation.put("negative", "需要更多耐心和包容\n可能遇到挑戰");
                evaluation.put("comment", "你們的星座組合需要更多的耐心和包容，在相處過程中可能會遇到一些挑戰，但透過互相理解和尊重，仍然可以建立穩定的關係。建議多溝通，避免誤會。");
                break;

            case "大凶":
                evaluation.put("positive", "真愛可以克服困難\n可以互相學習成長");
                evaluation.put("negative", "性格差異較大\n需要更多努力和包容");
                evaluation.put("comment", "你們的星座組合在性格上可能有較大的差異，需要更多的努力和包容來維持關係。但請記住，星座只是參考，真愛和互相理解才是關係的關鍵。如果真心相愛，任何困難都可以克服。");
                break;

            default:
                evaluation.put("positive", "需要進一步了解");
                evaluation.put("negative", "需要更多溝通");
                evaluation.put("comment", "建議多溝通，增進彼此的理解。");
        }

        return evaluation;
    }
}

