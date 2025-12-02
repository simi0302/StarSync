// 這個類別放在 service 套件裡面
package com.SAD_Project.service;

// 引入計算星座的工具類別
import com.SAD_Project.util.ZodiacCalculator;
// Spring 的服務註解
import org.springframework.stereotype.Service;
// HashMap 用來存鍵值對
import java.util.HashMap;
// Map 介面
import java.util.Map;

// 重構：整合配對規則、評語生成與星級計算邏輯
// 這個類別負責所有配對相關的計算

// 用來計算配對結果
// 這個引擎會根據兩個星座來計算配對分數和評價
@Service
public class MatchRuleEngine {

    // 二層 Map，儲存星座配對資料
    // 第一層的 key 是第一個星座，第二層的 key 是第二個星座
    // 例如：matchDataStorage.get("牡羊座").get("獅子座") 可以取得配對結果
    private final Map<String, Map<String, MatchResult>> matchDataStorage;

    // 建構子，建立物件時會初始化配對資料
    public MatchRuleEngine() {
        // 呼叫方法來建立所有星座的配對資料
        this.matchDataStorage = allZodiacMatchData();
    }

    // 配對結果 (已擴充包含詳細評價)
    // 這個內部類別用來儲存配對的結果資料
    public static class MatchResult {
        // 配對分數，0-100 分
        private int score;       // 分數
        // 配對等級，可能是 "大吉"、"小吉"、"普通"、"小凶"、"大凶"
        private String level;    // 等級（大吉、小吉、普通、小凶、大凶）
        // 星級，1-5 顆星
        private int stars;       // 星級
        // 優點描述
        private String pros; // 優點
        // 缺點描述
        private String cons; // 缺點
        // 整體評語
        private String comment;  // 評語

        // 建構子，建立時只需要分數和等級
        public MatchResult(int score, String level) {
            this.score = score;
            this.level = level;
        }

        // 取得分數的 getter 方法
        public int getScore() { return score; }
        // 取得等級的 getter 方法
        public String getLevel() { return level; }
        // 取得星級的 getter 方法
        public int getStars() { return stars; }
        // 取得優點的 getter 方法
        public String getPros() { return pros; }
        // 取得缺點的 getter 方法
        public String getCons() { return cons; }
        // 取得評語的 getter 方法
        public String getComment() { return comment; }

        // 設定星級的 setter 方法
        public void setStars(int stars) { this.stars = stars; }
        // 設定優點的 setter 方法
        public void setPros(String pros) { this.pros = pros; }
        // 設定缺點的 setter 方法
        public void setCons(String cons) { this.cons = cons; }
        // 設定評語的 setter 方法
        public void setComment(String comment) { this.comment = comment; }
    }

    // 根據日期算星座 (從 AnalysisController 移入)
    // birthDate 是生日字串
    public String calculateZodiac(String birthDate) {
        // 如果生日是 null 或空字串，就回傳 null
        if (birthDate == null || birthDate.trim().isEmpty()) {
            return null;
        }
        // 使用工具類別來計算星座
        return ZodiacCalculator.calculateZodiacFromString(birthDate);
    }

    // 算配對結果
    // zodiac1 是第一個人的星座，zodiac2 是第二個人的星座
    public MatchResult computeMatch(String zodiac1, String zodiac2) {
        // 如果任一個星座是 null，就回傳 null
        if (zodiac1 == null || zodiac2 == null) {
            return null;
        }

        // 檢查這兩個星座是否在資料庫裡面
        if (!matchDataStorage.containsKey(zodiac1) || !matchDataStorage.containsKey(zodiac2)) {
            // 如果找不到就回傳 null
            return null;
        }

        // 從資料庫取得配對結果
        // 因為配對是雙向的，所以 zodiac1 和 zodiac2 的順序可以互換
        return matchDataStorage.get(zodiac1).get(zodiac2);
    }

    // 初始化配對矩陣
    // 這個方法會建立所有星座之間的配對資料
    private Map<String, Map<String, MatchResult>> allZodiacMatchData() {
        // 建立二層 Map 來存配對資料
        Map<String, Map<String, MatchResult>> individualMatchDataMap = new HashMap<>();

        // 所有十二個星座的名稱陣列
        String[] zodiacs = {"牡羊座", "金牛座", "雙子座", "巨蟹座", "獅子座",
                "處女座", "天秤座", "天蠍座", "射手座", "魔羯座",
                "水瓶座", "雙魚座"};

        // 先為每個星座建立一個空的 Map
        // 這樣之後就可以把配對資料放進去
        for (String zodiac : zodiacs) {
            individualMatchDataMap.put(zodiac, new HashMap<>());
        }

        // 開始加配對資料
        // 下面這些是牡羊座和其他所有星座的配對分數和等級
        // 分數範圍是 0-100，等級有：大吉、小吉、普通、小凶、大凶
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

        // 金牛座和其他所有星座的配對資料
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

        // 雙子座和其他所有星座的配對資料
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

        // 巨蟹座和其他所有星座的配對資料
        addMatch(individualMatchDataMap, "巨蟹座", "巨蟹座", 85, "小吉");
        addMatch(individualMatchDataMap, "巨蟹座", "獅子座", 29, "小凶");
        addMatch(individualMatchDataMap, "巨蟹座", "處女座", 77, "小吉");
        addMatch(individualMatchDataMap, "巨蟹座", "天秤座", 28, "小凶");
        addMatch(individualMatchDataMap, "巨蟹座", "天蠍座", 79, "小吉");
        addMatch(individualMatchDataMap, "巨蟹座", "射手座", 27, "小凶");
        addMatch(individualMatchDataMap, "巨蟹座", "魔羯座", 84, "小吉");
        addMatch(individualMatchDataMap, "巨蟹座", "水瓶座", 31, "小凶");
        addMatch(individualMatchDataMap, "巨蟹座", "雙魚座", 72, "小吉");

        // 獅子座和其他所有星座的配對資料
        addMatch(individualMatchDataMap, "獅子座", "獅子座", 78, "小吉");
        addMatch(individualMatchDataMap, "獅子座", "處女座", 35, "小凶");
        addMatch(individualMatchDataMap, "獅子座", "天秤座", 75, "小吉");
        addMatch(individualMatchDataMap, "獅子座", "天蠍座", 29, "小凶");
        addMatch(individualMatchDataMap, "獅子座", "射手座", 75, "小吉");
        addMatch(individualMatchDataMap, "獅子座", "魔羯座", 27, "小凶");
        addMatch(individualMatchDataMap, "獅子座", "水瓶座", 89, "大吉");
        addMatch(individualMatchDataMap, "獅子座", "雙魚座", 14, "大凶");

        // 處女座和其他所有星座的配對資料
        addMatch(individualMatchDataMap, "處女座", "處女座", 81, "小吉");
        addMatch(individualMatchDataMap, "處女座", "天秤座", 65, "普通");
        addMatch(individualMatchDataMap, "處女座", "天蠍座", 76, "小吉");
        addMatch(individualMatchDataMap, "處女座", "射手座", 32, "小凶");
        addMatch(individualMatchDataMap, "處女座", "魔羯座", 77, "小吉");
        addMatch(individualMatchDataMap, "處女座", "水瓶座", 30, "小凶");
        addMatch(individualMatchDataMap, "處女座", "雙魚座", 86, "大吉");

        // 天秤座和其他所有星座的配對資料
        addMatch(individualMatchDataMap, "天秤座", "天秤座", 80, "小吉");
        addMatch(individualMatchDataMap, "天秤座", "天蠍座", 29, "小凶");
        addMatch(individualMatchDataMap, "天秤座", "射手座", 71, "小吉");
        addMatch(individualMatchDataMap, "天秤座", "魔羯座", 34, "小凶");
        addMatch(individualMatchDataMap, "天秤座", "水瓶座", 68, "普通");
        addMatch(individualMatchDataMap, "天秤座", "雙魚座", 50, "普通");

        // 天蠍座和其他所有星座的配對資料
        addMatch(individualMatchDataMap, "天蠍座", "天蠍座", 66, "普通");
        addMatch(individualMatchDataMap, "天蠍座", "射手座", 30, "小凶");
        addMatch(individualMatchDataMap, "天蠍座", "魔羯座", 64, "普通");
        addMatch(individualMatchDataMap, "天蠍座", "水瓶座", 30, "小凶");
        addMatch(individualMatchDataMap, "天蠍座", "雙魚座", 81, "小吉");

        // 射手座和其他所有星座的配對資料
        addMatch(individualMatchDataMap, "射手座", "射手座", 74, "小吉");
        addMatch(individualMatchDataMap, "射手座", "魔羯座", 38, "小凶");
        addMatch(individualMatchDataMap, "射手座", "水瓶座", 83, "小吉");
        addMatch(individualMatchDataMap, "射手座", "雙魚座", 50, "普通");

        // 魔羯座和其他所有星座的配對資料
        addMatch(individualMatchDataMap, "魔羯座", "魔羯座", 62, "普通");
        addMatch(individualMatchDataMap, "魔羯座", "水瓶座", 37, "小凶");
        addMatch(individualMatchDataMap, "魔羯座", "雙魚座", 76, "小吉");

        // 水瓶座和其他所有星座的配對資料
        addMatch(individualMatchDataMap, "水瓶座", "水瓶座", 74, "小吉");
        addMatch(individualMatchDataMap, "水瓶座", "雙魚座", 38, "小凶");

        // 雙魚座和其他所有星座的配對資料
        addMatch(individualMatchDataMap, "雙魚座", "雙魚座", 73, "小吉");

        // 回傳完整的配對資料 Map
        return individualMatchDataMap;
    }

    // 加配對資料，雙向都要加，並直接計算評價
    // matrix 是配對資料的 Map
    // zodiac1 和 zodiac2 是兩個要配對的星座
    // score 是配對分數，level 是配對等級
    private void addMatch(Map<String, Map<String, MatchResult>> matrix,
                          String zodiac1, String zodiac2, int score, String level) {
        // 建立配對結果物件
        MatchResult result = new MatchResult(score, level);

        // 預先計算評價資料 (從 AnalysisController 移入的邏輯)
        // 根據分數計算星級
        result.setStars(calculateStars(score));
        // 根據等級取得評價文字（優點、缺點、評語）
        Map<String, String> evaluation = getEvaluation(level);
        // 設定優點
        result.setPros(evaluation.get("positive"));
        // 設定缺點
        result.setCons(evaluation.get("negative"));
        // 設定評語
        result.setComment(evaluation.get("comment"));

        // 因為配對是雙向的，所以兩個方向都要存
        // 例如：牡羊座配獅子座 和 獅子座配牡羊座 結果一樣
        matrix.get(zodiac1).put(zodiac2, result);
        matrix.get(zodiac2).put(zodiac1, result);
    }

    // 算星級 (從 AnalysisController 移入)
    // 根據配對分數來計算星級（1-5 顆星）
    // score 是配對分數
    private int calculateStars(int score) {
        // 85 分以上是 5 顆星
        if (score >= 85) return 5;
        // 70-84 分是 4 顆星
        if (score >= 70) return 4;
        // 50-69 分是 3 顆星
        if (score >= 50) return 3;
        // 30-49 分是 2 顆星
        if (score >= 30) return 2;
        // 30 分以下是 1 顆星
        return 1;
    }

    // 取得評價文字 (從 AnalysisController 移入)
    // 根據配對等級來取得對應的優點、缺點和評語
    // level 是配對等級（大吉、小吉、普通、小凶、大凶）
    private Map<String, String> getEvaluation(String level) {
        // 建立 Map 來存評價文字
        Map<String, String> evaluation = new HashMap<>();

        // 根據等級來設定不同的評價
        switch (level) {
            // 大吉等級的評價
            case "大吉":
                // 設定優點
                evaluation.put("positive", "性格相配，價值觀相近\n能夠互相理解和支持\n關係穩定和諧");
                // 設定缺點
                evaluation.put("negative", "可能缺乏新鮮感\n需要保持熱情");
                // 設定整體評語
                evaluation.put("comment", "你們的星座組合非常相配，在性格和價值觀上有很多共通點，能夠互相理解和支持，是一對很好的組合。建議好好珍惜這段緣分！");
                break;

            // 小吉等級的評價
            case "小吉":
                evaluation.put("positive", "組合相當不錯\n可以互補不足\n關係穩定");
                evaluation.put("negative", "有些差異需要磨合\n需要更多溝通");
                evaluation.put("comment", "你們的星座組合相當不錯，雖然有些差異，但可以互補，只要多溝通和理解，關係會很穩定。建議多了解對方的想法和需求。");
                break;

            // 普通等級的評價
            case "普通":
                evaluation.put("positive", "有發展潛力\n可以互相學習");
                evaluation.put("negative", "需要更多努力\n需要增進理解");
                evaluation.put("comment", "你們的星座組合需要更多的努力來維持關係，建議多了解對方的想法和需求，增進彼此的理解。雖然配對分數普通，但真愛可以克服一切困難。");
                break;

            // 小凶等級的評價
            case "小凶":
                evaluation.put("positive", "仍有發展空間\n可以建立穩定關係");
                evaluation.put("negative", "需要更多耐心和包容\n可能遇到挑戰");
                evaluation.put("comment", "你們的星座組合需要更多的耐心和包容，在相處過程中可能會遇到一些挑戰，但透過互相理解和尊重，仍然可以建立穩定的關係。建議多溝通，避免誤會。");
                break;

            // 大凶等級的評價
            case "大凶":
                evaluation.put("positive", "真愛可以克服困難\n可以互相學習成長");
                evaluation.put("negative", "性格差異較大\n需要更多努力和包容");
                evaluation.put("comment", "你們的星座組合在性格上可能有較大的差異，需要更多的努力和包容來維持關係。但請記住，星座只是參考，真愛和互相理解才是關係的關鍵。如果真心相愛，任何困難都可以克服。");
                break;

            // 預設情況（如果等級不符合上面任何一個）
            default:
                evaluation.put("positive", "需要進一步了解");
                evaluation.put("negative", "需要更多溝通");
                evaluation.put("comment", "建議多溝通，增進彼此的理解。");
        }

        // 回傳評價 Map
        return evaluation;
    }
}

