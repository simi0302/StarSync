// 這個類別放在 controller 套件裡面
package com.SAD_Project.controller;

// 引入 Couple 模型類別
import com.SAD_Project.model.Couple;
// 引入配對規則引擎服務
import com.SAD_Project.service.MatchRuleEngine;
// Spring 的自動注入註解
import org.springframework.beans.factory.annotation.Autowired;
// 標記這是服務類別
import org.springframework.stereotype.Service;

// HashMap 用來存鍵值對
import java.util.HashMap;
// Map 介面
import java.util.Map;

// 用來處理配對分析
// 重構：已移除 Repository 依賴，專注於流程控制
// 這個類別負責處理星座配對的分析邏輯
@Service
public class AnalysisController {

    // 移除 CoupleDataRepo 和 MatchResultDB 的注入
    // 現在不需要資料庫了，直接處理資料

    // 自動注入配對規則引擎
    // 這個引擎會負責計算配對分數和結果
    @Autowired
    private MatchRuleEngine matchRuleEngine;

    // 移除 coupleId 參數，因為不存資料庫就不需要 ID
    // 這個方法負責執行配對分析
    // couple 參數包含兩個人的生日和性別資料
    public AnalysisResult doAnalysis(Couple couple) {
        // 用 try-catch 來處理可能的錯誤
        try {
            // 算出兩個人的星座 (呼叫 MatchRuleEngine 的方法)
            // 根據第一個人的生日計算星座
            String zodiac1 = matchRuleEngine.calculateZodiac(couple.getBirthDate1());
            // 根據第二個人的生日計算星座
            String zodiac2 = matchRuleEngine.calculateZodiac(couple.getBirthDate2());

            // 檢查星座計算是否成功
            if (zodiac1 == null || zodiac2 == null) {
                // 如果計算失敗就回傳錯誤
                return AnalysisResult.error("無法根據生日推算星座");
            }

            // 算配對結果
            // 根據兩個星座來計算配對分數和評價
            MatchRuleEngine.MatchResult matchResult = matchRuleEngine.computeMatch(zodiac1, zodiac2);

            // 檢查配對結果是否存在
            if (matchResult == null) {
                // 如果找不到配對資料就回傳錯誤
                return AnalysisResult.error("無法找到配對資料");
            }


            // 如果都成功就回傳成功結果
            return AnalysisResult.success(couple, zodiac1, zodiac2, matchResult);

        } catch (Exception e) {
            // 如果發生任何例外就回傳錯誤訊息
            return AnalysisResult.error("分析失敗，請稍後再試: " + e.getMessage());
        }
    }

    // AnalysisResult 內部類別保持不變 (或配合上一輪重構微調)
    // 這個內部類別用來包裝分析結果
    public static class AnalysisResult {
        // 標記分析是否成功
        private boolean success;
        // 如果失敗的話，這裡會存錯誤訊息
        private String error;
        // 兩個人的資料
        private Couple couple;
        // 第一個人的星座
        private String zodiac1;
        // 第二個人的星座
        private String zodiac2;
        // 配對結果，包含分數、等級、評價等
        private MatchRuleEngine.MatchResult matchResult;

        // 私有建構子，只能透過靜態方法建立
        private AnalysisResult(boolean success, String error, Couple couple,
                               String zodiac1, String zodiac2, MatchRuleEngine.MatchResult matchResult) {
            // 設定所有欄位
            this.success = success;
            this.error = error;
            this.couple = couple;
            this.zodiac1 = zodiac1;
            this.zodiac2 = zodiac2;
            this.matchResult = matchResult;
        }

        // 建立成功結果的靜態方法
        public static AnalysisResult success(Couple couple, String zodiac1, String zodiac2,
                                             MatchRuleEngine.MatchResult matchResult) {
            // success 設為 true，error 設為 null
            return new AnalysisResult(true, null, couple, zodiac1, zodiac2, matchResult);
        }

        // 建立錯誤結果的靜態方法
        public static AnalysisResult error(String error) {
            // success 設為 false，其他都設為 null
            return new AnalysisResult(false, error, null, null, null, null);
        }

        // 取得是否成功的 getter 方法
        public boolean isSuccess() { return success; }
        // 取得錯誤訊息的 getter 方法
        public String getError() { return error; }

        // 把結果轉成結構化的 Map 格式
        // 這樣方便轉成 JSON 回傳給前端
        public Map<String, Object> toStructuredResult() {
            // 建立一個新的 Map 來存結果
            Map<String, Object> result = new HashMap<>();

            // 如果分析失敗，就只回傳錯誤訊息
            if (!success) {
                result.put("error", error);
                return result;
            }

            // 直接從 MatchResult 取得資料 (假設您已套用上一輪的 MatchResult 重構)
            // 把配對分數放進去
            result.put("score", matchResult.getScore());
            // 把配對等級放進去（大吉、小吉等）
            result.put("level", matchResult.getLevel());
            // 如果您有套用上一輪重構，這裡可以直接 getStars, getPositive 等
            // 如果沒有，則這裡需要保留原本的計算邏輯

            // 假設已套用 MatchRuleEngine 重構：
            // 把星級放進去（1-5 顆星）
            result.put("stars", matchResult.getStars());
            // 把優點放進去
            result.put("positive", matchResult.getPros());
            // 把缺點放進去
            result.put("negative", matchResult.getCons());
            // 把評語放進去
            result.put("comment", matchResult.getComment());

            // 把兩個人的星座也放進去
            result.put("zodiac1", zodiac1);
            result.put("zodiac2", zodiac2);

            // 回傳結果
            return result;
        }
    }
}

