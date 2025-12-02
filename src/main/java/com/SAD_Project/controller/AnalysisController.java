package com.SAD_Project.controller;

import com.SAD_Project.model.Couple;
import com.SAD_Project.service.MatchRuleEngine;

import org.springframework.beans.factory.annotation.Autowired; // Spring 的自動注入註解
import org.springframework.stereotype.Service; // 標記這是服務類別

import java.util.HashMap;
import java.util.Map;

// 這個class負責處理星座配對的分析邏輯
@Service
public class AnalysisController {

    // 這個引擎會負責計算配對分數和結果
    @Autowired
    private MatchRuleEngine matchRuleEngine;

    // 這個方法負責執行配對分析, couple 參數包含兩個人的生日和性別資料
    public AnalysisResult doAnalysis(Couple couple) {
        try {
            // 呼叫 MatchRuleEngine 的方法來計算兩個人的星座
            String zodiac1 = matchRuleEngine.calculateZodiac(couple.getBirthDate1());
            String zodiac2 = matchRuleEngine.calculateZodiac(couple.getBirthDate2());

            if (zodiac1 == null || zodiac2 == null) {
                return AnalysisResult.error("無法根據生日推算星座");
            }

            MatchRuleEngine.MatchResult matchResult = matchRuleEngine.computeMatch(zodiac1, zodiac2); // 根據兩個星座來計算配對分數和評價

            if (matchResult == null) {
                return AnalysisResult.error("無法找到配對資料");
            }

            return AnalysisResult.success(couple, zodiac1, zodiac2, matchResult); // 如果都成功就回傳成功結果

        } catch (Exception e) {
            return AnalysisResult.error("分析失敗，請稍後再試: " + e.getMessage()); // 如果發生任何例外就回傳錯誤訊息
        }
    }

    // AnalysisResult 內部類用來包裝分析結果
    public static class AnalysisResult {

        private boolean success; // 標記分析是否成功
        private String error; //失敗的話存錯誤訊息
        private Couple couple;
        private String zodiac1;
        private String zodiac2;
        private MatchRuleEngine.MatchResult matchResult;

        // 私有建構子，只能透過靜態方法建立
        private AnalysisResult(boolean success, String error, Couple couple,
                               String zodiac1, String zodiac2, MatchRuleEngine.MatchResult matchResult) {
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
            return new AnalysisResult(true, null, couple, zodiac1, zodiac2, matchResult);
        }

        // 建立錯誤結果的靜態方法
        public static AnalysisResult error(String error) {
            return new AnalysisResult(false, error, null, null, null, null);
        }

        // success跟error的getter方法
        public boolean isSuccess() { return success; }
        public String getError() { return error; }

        // 把結果轉成結構化的 Map 格式
        // 這樣方便轉成 JSON 回傳給前端
        public Map<String, Object> toStructuredResult() {

            Map<String, Object> result = new HashMap<>(); //存結果

            if (!success) {
                result.put("error", error);
                return result;
            }

            // 直接從 MatchResult 取得資料並存進result
            result.put("score", matchResult.getScore());
            result.put("level", matchResult.getLevel());
            result.put("stars", matchResult.getStars());
            result.put("positive", matchResult.getPros());
            result.put("negative", matchResult.getCons());
            result.put("comment", matchResult.getComment());
            result.put("zodiac1", zodiac1);
            result.put("zodiac2", zodiac2);

            return result;
        }
    }
}

